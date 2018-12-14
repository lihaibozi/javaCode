package com.wx.pay.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kin207.zn.AppConst;
import com.wx.pay.tenpay.util.AmountUtils;
import com.wx.pay.tenpay.util.UuidUtil;
import com.wx.pay.util.XMLUtil;
import com.wx.pay.util.CommonUtil;
import com.wx.pay.util.GetWxOrderno;
import com.wx.pay.util.OrderUtils;
import com.wx.pay.util.RequestHandler;
import com.wx.pay.util.Sha1Util;
import com.wx.pay.util.TxtUtil;
import com.wx.pay.util.WeixinPayUtil;
import com.wx.pay.util.http.HttpClientConnectionManager;

/**
 * 微信支付Controller
 * 
 * @author kinfeng
 * @since 2016-08-11
 */
@Controller
@RequestMapping("/wx")
@SuppressWarnings("rawtypes")
public class WeixinPayController {
	private static String baseUrl = AppConst.APP_URL;
	public static Logger log = Logger.getLogger(WeixinPayController.class);

	/**
	 * 微信网页授权获取用户基本信息，先获取 code，跳转 url 通过 code 获取 openId
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userAuth")
	public String userAuth(HttpServletRequest request, HttpServletResponse response) {
		try {
			String orderId = OrderUtils.genOrderNo();
			String totalFee = "0.1";
			//String totalFee = request.getParameter("totalFee");
			System.out.println("in userAuth,orderId:" + orderId);

			// 授权后要跳转的链接
			String backUri = baseUrl + "wx/toPay";
			backUri = backUri + "?orderId=" + orderId + "&totalFee=" + totalFee;
			// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
			backUri = URLEncoder.encode(backUri, "UTF-8");
			// scope 参数视各自需求而定，这里用scope=snsapi_base
			// 不弹出授权页面直接授权目的只获取统一支付接口的openid
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + AppConst.APPID
					+ "&redirect_uri=" + backUri
					+ "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
			System.out.println("url:" + url);
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 微信支付（拿到授权信息执行支付）
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toPay")
	public String toPay(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String orderId = request.getParameter("orderId");
			// 商品描述
			String body = request.getParameter("body");
			System.out.println("in toPay,orderId:" + orderId);
			String totalFeeStr = request.getParameter("totalFee");
			Float totalFee = 0.0f;
			if (StringUtils.isNotBlank(totalFeeStr)) {
				totalFee = new Float(totalFeeStr);
			}
			// 网页授权后获取传递的参数
			String userId = request.getParameter("userId");
			String code = request.getParameter("code");
			System.out.println("code:" + code);
			System.out.println("userId:" + userId);
			// 获取统一下单需要的openid
			String openId = "";
			String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AppConst.APPID + "&secret="
					+ AppConst.SECRET + "&code=" + code + "&grant_type=authorization_code";
			System.out.println("URL:" + URL);
			JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
			 Set set = jsonObject.keySet();
	            for (Object key : set) {
	                System.out.println("wx-value:"+jsonObject.get(key));
	            }
			if (null != jsonObject) {
				openId = jsonObject.getString("openid");
				System.out.println("openId:" + openId);
			}
			// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
			// 随机数
			String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
			// 商户订单号
			String out_trade_no = orderId;
			// 订单生成的机器 IP
			String spbill_create_ip = request.getRemoteAddr();
			// 总金额
			// TODO
			Integer total_fee = Math.round(totalFee * 100);

			// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
			String notify_url = baseUrl + "/wx/notifyUrl";
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", AppConst.APPID);
			packageParams.put("mch_id", AppConst.PARTNER);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", "test");
			packageParams.put("out_trade_no", out_trade_no);
			packageParams.put("total_fee", total_fee + "");
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);
			packageParams.put("trade_type", AppConst.trade_type);
			packageParams.put("openId", openId);
			RequestHandler reqHandler = new RequestHandler(request, response);
			reqHandler.init(AppConst.APPID, AppConst.SECRET, AppConst.PARTNERKEY);
			String sign = reqHandler.createSign(packageParams);
			System.out.println("sign:" + sign);
			packageParams.put("sign", sign);
			String xml = XMLUtil.changePayXML(packageParams);
			System.out.println("xml：" + xml);
			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String prepay_id = "";
			try {
				prepay_id = WeixinPayUtil.getPayNo(createOrderURL, xml);
				System.out.println("prepay_id:" + prepay_id);
				if (prepay_id.equals("")) {
					request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
					response.sendRedirect("error.jsp");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
			String timestamp = Sha1Util.getTimeStamp();
			String packages = "prepay_id=" + prepay_id;
			finalpackage.put("appId", AppConst.APPID);
			finalpackage.put("timeStamp", timestamp);
			finalpackage.put("nonceStr", nonce_str);
			finalpackage.put("package", packages);
			finalpackage.put("signType", AppConst.signType);
			String finalsign = reqHandler.createSign(finalpackage);
			System.out.println("/jsapi?appid=" + AppConst.APPID + "&timeStamp=" + timestamp + "&nonceStr=" + nonce_str
					+ "&package=" + packages + "&sign=" + finalsign);
			model.addAttribute("appid", AppConst.APPID);
			model.addAttribute("timeStamp", timestamp);
			model.addAttribute("nonceStr", nonce_str);
			model.addAttribute("packageValue", packages);
			model.addAttribute("sign", finalsign);
			model.addAttribute("bizOrderId", orderId);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payPrice", total_fee);
			return "/wx/jsapi";
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 微信异步回调，不会跳转页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/notifyUrl")
	public String weixinReceive(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("==开始进入h5支付回调方法==");
		String xml_review_result = WeixinPayUtil.getXmlRequest(request);
		System.out.println("微信支付结果:" + xml_review_result);
		Map resultMap = null;
		try {
			resultMap = WeixinPayUtil.doXMLParse(xml_review_result);
			System.out.println("resultMap:" + resultMap);
			if (resultMap != null && resultMap.size() > 0) {
				String sign_receive = (String) resultMap.get("sign");
				System.out.println("sign_receive:" + sign_receive);
				resultMap.remove("sign");
				String checkSign = WeixinPayUtil.getSign(resultMap, AppConst.PARTNERKEY);
				System.out.println("checkSign:" + checkSign);
				// 签名校验成功
				if (checkSign != null && sign_receive != null && checkSign.equals(sign_receive.trim())) {
					System.out.println("weixin receive check Sign sucess");
					try {
						// 获得返回结果
						String return_code = (String) resultMap.get("return_code");
						if ("SUCCESS".equals(return_code)) {
							String out_trade_no = (String) resultMap.get("out_trade_no");
							System.out.println("weixin pay sucess,out_trade_no:" + out_trade_no);
							// 处理支付成功以后的逻辑，这里是写入相关信息到文本文件里面，如果有订单的处理订单
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mi:ss");
								String content = out_trade_no + "        " + sdf.format(new Date());
								String fileUrl = System.getProperty("user.dir") + File.separator + "WebContent"
										+ File.separator + "data" + File.separator + "order.txt";
								TxtUtil.writeToTxt(content, fileUrl);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							model.addAttribute("payResult", "0");
							model.addAttribute("err_code_des", "通信错误");
						}
					} catch (Exception e) {
						e.printStackTrace();
						}
				} else {
					// 签名校验失败
					System.out.println("weixin receive check Sign fail");
					String checkXml = "<xml><return_code><![CDATA[FAIL]]></return_code>"
							+ "<return_msg><![CDATA[check sign fail]]></return_msg></xml>";
					 WeixinPayUtil.getTradeOrder(AppConst.APP_URL+"/wx/notifyUrl", checkXml);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 页面js返回支付成功后，查询微信后台是否支付成功，然后跳转结果页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/success")
	public String toWXPaySuccess(HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {
		String id = request.getParameter("orderId");
		System.out.println("toWXPaySuccess, orderId: " + id);
		try {
			Map resultMap = WeixinPayUtil.checkWxOrderPay(id);
			System.out.println("resultMap:" + resultMap);
			String return_code = (String) resultMap.get("return_code");
			String result_code = (String) resultMap.get("result_code");
			System.out.println("return_code:" + return_code + ",result_code:" + result_code);
			if ("SUCCESS".equals(return_code)) {
				if ("SUCCESS".equals(result_code)) {
					model.addAttribute("orderId", id);
					model.addAttribute("payResult", "1");
				} else {
					String err_code = (String) resultMap.get("err_code");
					String err_code_des = (String) resultMap.get("err_code_des");
					System.out.println("weixin resultCode:" + result_code + ",err_code:" + err_code + ",err_code_des:"
							+ err_code_des);

					model.addAttribute("err_code", err_code);
					model.addAttribute("err_code_des", err_code_des);
					model.addAttribute("payResult", "0");
				}
			} else {
				model.addAttribute("payResult", "0");
				model.addAttribute("err_code_des", "通信错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/wx/payResult";
	}

	/**
	 * 微信公众号申请退款
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/refundWXPay")
	private String refundwx(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*--------1.初始化数据参数----------*/
		// 需要退款的商户订单号，对应提交订单中的out_trade_no
		String orderId = request.getParameter("orderId").toString();
		String total_fee = AmountUtils.changeY2F(request.getParameter("total_fee")); // 也可以根据业务场景从数据库中获取总金额和退款金额
		String refund_fee = AmountUtils.changeY2F(request.getParameter("refund_fee"));
		Map<String, String> result = (Map<String, String>) wxRefund(request, response, orderId, total_fee, refund_fee);
		/*
		 * 根据result的返回状态，处理你的业务逻辑 .....
		 */
		if (result.get("returncode").equals("ok")) {
			return "";
		}

		return "";
	}

	private Map<String, String> wxRefund(HttpServletRequest request, HttpServletResponse response, String orderId,
			String total_fee, String refund_fee) {
		Map<String, String> result = new HashMap<String, String>();
		String refundid = UuidUtil.get32UUID();
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		/*----------  1.生成预支付订单需要的的package数据-----------*/
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", AppConst.APPID);
		packageParams.put("mch_id", AppConst.PARTNER);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("op_user_id", AppConst.PARTNER);
		packageParams.put("out_trade_no", orderId);
		packageParams.put("out_refund_no", refundid);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		/*------2.根据package生成签名sign------- */
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(AppConst.APPID, AppConst.SECRET, AppConst.PARTNERKEY);
		String sign = reqHandler.createSign(packageParams);
		packageParams.put("sign", sign);
		/*------3.拼装需要提交到微信的数据xml------- */
		String xml = XMLUtil.changeRePayXML(packageParams);
		try {
			/*--------4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改------*/
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(AppConst.SSLFILEPATH));
			try {
				keyStore.load(instream, AppConst.PARTNER.toCharArray());
			} finally {
				instream.close();
			}
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, AppConst.PARTNER.toCharArray())
					.build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

			/*------5.发送数据到微信的退款接口------- */
			String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
			httpost.setEntity(new StringEntity(xml, "UTF-8"));
			HttpResponse weixinResponse = httpClient.execute(httpost);
			String jsonStr = EntityUtils.toString(weixinResponse.getEntity(), "UTF-8");
			log.info(jsonStr);

			Map map = GetWxOrderno.doXMLParse(jsonStr);
			if ("success".equalsIgnoreCase((String) map.get("return_code"))) {
				log.info("退款成功");
				result.put("returncode", "ok");
				result.put("returninfo", "退款成功");
			} else {
				log.info("退款失败");
				result.put("returncode", "error");
				result.put("returninfo", "退款失败");
			}
		} catch (Exception e) {
			log.info("退款失败");
			result.put("returncode", "error");
			result.put("returninfo", "退款失败");
		}
		return result;

	}
}
