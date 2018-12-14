package common.utils.wechat;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;


/**
 * 公众平台通用接口工具类
 *
 * @author kinfeng
 * @date 2015-02-27
 */
public class WxConfigUtil {
	private static Logger logger = Logger.getLogger(WxConfigUtil.class);

	// 公众号appId   根据实际修改
	public final static String appId = "wx62a831411800446c";
	// 微信公众号secret  根据实际修改
	public final static String secret = "075e7e3bdeaed2f5947e4328a0af4add";
	// 生成签名的随机串
	public final static String nonce_str = UUID.randomUUID().toString().substring(0,15);
	public final static Long timestamp = System.currentTimeMillis() / 1000;
	// 获取access_token的接口地址（GET） 限2000（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 获取jsapi_ticket的接口地址（GET） 限2000（次/天）
	public final static String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 缓存添加的时间
	public static String cacheAddTime = null;
	// token,ticket缓存
	public static Map<String, AccessToken> TOKEN_TICKET_CACHE = new HashMap<String, AccessToken>();
	// token对应的key
	private static final String TOKEN = "token";
	// ticket对应的key
	private static final String TICKET = "ticket";
	


	/**
	 * 获取access_token
	 *
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	private static AccessToken getAccessToken(String appid, String appsecret, long currentTime) {
//		AccessToken tockenTicketCache = getTokenTicket(TOKEN);
		AccessToken accessToken = null;

/*		if (tockenTicketCache != null && (currentTime - tockenTicketCache.getAddTime() <= tockenTicketCache.getExpiresIn() * 1000)) {// 缓存存在并且没过期
			logger.info("==========缓存中token已获取时长为：" + (currentTime - tockenTicketCache.getAddTime()) + "毫秒，可以重新使用");
			return tockenTicketCache;
		}*/
		logger.info("==========缓存中token不存在或已过期===============");
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			accessToken = new AccessToken();
			accessToken.setToken(jsonObject.getString("access_token"));
			accessToken.setExpiresIn(jsonObject.getInt("expires_in") / 2);// 正常过期时间是7200秒，此处设置3600秒读取一次
			logger.info("==========tocket缓存过期时间为:" + accessToken.getExpiresIn() * 1000 + "毫秒");
			accessToken.setAddTime(currentTime);
			updateAccessToken(TOKEN, accessToken);
		}
		return accessToken;
	}

	/**
	 * 获取ticket
	 *
	 * @param token
	 *
	 * @return
	 */
	private static AccessToken getTicket(String token, long currentTime) {
		AccessToken tockenTicketCache = getTokenTicket(TICKET);
		AccessToken accessToken = null;
		if (tockenTicketCache != null && (currentTime - tockenTicketCache.getAddTime() <= tockenTicketCache.getExpiresIn() * 1000)) {// 缓存中有ticket
			logger.info("==========缓存中ticket已获取时长为：" + (currentTime - tockenTicketCache.getAddTime()) + "毫秒，可以重新使用");
			return tockenTicketCache;
		}
		logger.info("==========缓存中ticket不存在或已过期===============");
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			accessToken = new AccessToken();
			accessToken.setTicket(jsonObject.getString("ticket"));
			accessToken.setExpiresIn(jsonObject.getInt("expires_in") / 2);// 正常过期时间是7200秒，此处设置3600秒读取一次
			logger.info("==========ticket缓存过期时间为:" + accessToken.getExpiresIn() * 1000 + "毫秒");
			accessToken.setAddTime(currentTime);
			updateAccessToken(TICKET, accessToken);
		}
		return accessToken;
	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
			// jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.info("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.info("https request error:{}" + e.getMessage());
		}
		return jsonObject;
	}

	/**
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
    
    public static  Map<String, String> getWXConfigMap(HttpServletRequest request,String url){
        AccessToken token = getAccessToken(appId, secret, timestamp);
        Map<String,String> map = getJsSignMap(getTicket(token.getToken(),timestamp).getTicket(),url);
        return map;
    }
    /**
     * 获取jsapi相关参数map
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public static Map<String, String> getJsSignMap(String jsapi_ticket, String url) {
        Map<String, String> map = new HashMap<String, String>();


        //注意这里参数名必须全部小写，且必须有序
        String s = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        String signature = null;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(s.getBytes("UTF-8"));
            signature = WXSign.byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
        	logger.error("WxConfigUtil getJsSignMap：获取微信公众号jsapi失败！"+e);
        }
        catch (UnsupportedEncodingException e)
        {
        	logger.error("WxConfigUtil getJsSignMap：获取微信公众号jsapi失败！"+e);
        }

        map.put("url", url);
        map.put("appid", appId);
        map.put("jsapi_ticket", jsapi_ticket);
        map.put("nonceStr", nonce_str);
        map.put("timestamp", timestamp.toString());
        map.put("signature", signature);

        return map;
    }
	/**
	 * 从缓存中读取token或者ticket
	 *
	 * @return
	 */
	private static AccessToken getTokenTicket(String key) {
		if (TOKEN_TICKET_CACHE != null && TOKEN_TICKET_CACHE.get(key) != null) {
			logger.info("==========从缓存中获取到了" + key + "成功===============");
			return TOKEN_TICKET_CACHE.get(key);
		}
		return null;
	}

	/**
	 * 更新缓存中token或者ticket
	 *
	 * @return
	 */
	private static void updateAccessToken(String key, AccessToken accessTocken) {
		if (TOKEN_TICKET_CACHE != null && TOKEN_TICKET_CACHE.get(key) != null) {
			TOKEN_TICKET_CACHE.remove(key);
			logger.info("==========从缓存中删除" + key + "成功===============");
		}
		TOKEN_TICKET_CACHE.put(key, accessTocken);
		cacheAddTime = String.valueOf(accessTocken.getAddTime());// 更新缓存修改的时间
		logger.info("==========更新缓存中" + key + "成功===============");
	}
	/**
	 * 获取URL
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
	    if (request == null) {
	        return "";
	    }
	    String url = request.getRequestURL().toString();

	    if (request.getQueryString() != null && !"".equals(request.getQueryString()) && !"null".equals(request.getQueryString()) ) {
	        url = url + "?" + request.getQueryString();
	    }

	    return url;
	}

}