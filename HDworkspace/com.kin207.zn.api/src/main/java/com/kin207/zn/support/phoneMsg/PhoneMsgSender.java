package com.kin207.zn.support.phoneMsg;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
public class PhoneMsgSender {
	/**
	 * 
	 * @param url
	 *            应用地址，类似于http://ip:port/msg/
	 * @param account
	 *            账号
	 * @param pswd
	 *            密码
	 * @param mobile
	 *            手机号码，多个号码使用","分割
	 * @param msg
	 *            短信内容
	 * @param needstatus
	 *            是否需要状态报告，需要true，不需要false
	 * @return 返回值定义参见HTTP协议文档
	 * @throws Exception
	 */
	public static String  sendSms(String mobile, String msg) throws Exception{
		String url = "http://222.73.117.158/msg/";// 应用地址
		String account = "Vipwnkj888";// 账号
		String pswd = "Ikin2072016";// 密码
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码
		HttpClient client=new HttpClient();
		GetMethod method=new GetMethod();
		try{
			URI base=new URI(url,false);
			method.setURI(new URI(base,"HttpBatchSendSM",false));
			method.setQueryString(new NameValuePair[]{new NameValuePair("account",account),
					new NameValuePair("pswd",pswd),new NameValuePair("mobile",mobile),
					new NameValuePair("needstatus",String.valueOf(needstatus)),new NameValuePair("msg",msg),
					new NameValuePair("extno",extno),});
			int result=client.executeMethod(method);
			if(result==HttpStatus.SC_OK) {
				InputStream in=method.getResponseBodyAsStream();
				ByteArrayOutputStream baos=new ByteArrayOutputStream();
				byte[] buffer=new byte[1024];
				int len=0;
				while((len=in.read(buffer))!=-1){
					baos.write(buffer,0,len);
				}
				return URLDecoder.decode(baos.toString(),"UTF-8");
			}else{
				throw new Exception("HTTP ERROR Status: "+method.getStatusCode()+":"+method.getStatusText());
			}
		}finally{
			method.releaseConnection();
		}
	}
	public static String batchSend(String phone) throws Exception {
		String url = "http://sms.253.com/msg/";// 应用地址
		String un = "M1183967";// 账号
		String pw = "Aub8dOIRDQ1a99";// 密码
		String msg = "【LA服装会所】三月女神节，淘宝不等双十一，LA特卖有新衣。送您100元抵扣券，凭短信消费立减100元（劲爆商品除外）。退订回T";// 短信内容
		String rd = "1";// 是否需要状态报告，需要1，不需要0
		String ex = null;// 扩展码
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "send", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("un", un),
					new NameValuePair("pw", pw), 
					new NameValuePair("phone", phone),
					new NameValuePair("rd", rd), 
					new NameValuePair("msg", msg),
					new NameValuePair("ex", ex), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				String code = URLDecoder.decode(baos.toString(), "UTF-8");
				return code;
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
			}
		}
}