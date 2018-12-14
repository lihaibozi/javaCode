package common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * http请求工具类
 * @author wujun
 */
public class HttpRequestUtil {
	private static Logger log = Logger.getLogger(HttpRequestUtil.class);
	
	/* ajax请求头名称 */
	private static final String AJAX_HEADER_NAME = "x-requested-with";
	
	/**
	 * 判断ajax请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		String val = request.getHeader(AJAX_HEADER_NAME);
		return val != null && !val.equals("");
	}
	
	/**
	 * 判断多功能的表单(例如上传文件)
	 * @param request
	 * @return
	 */
	public static boolean isMutipartForm(HttpServletRequest request){
		return request != null 
				&& request.getContentType() != null 
				&& request.getContentType().contains("multipart/form-data");
	}
	
	/**
	 * 判断请求Url是否指定的前缀开始
	 * @param request
	 * @param urlPrefix url指定的前缀
	 * @return
	 */
	public static boolean isUrlStartWith(HttpServletRequest request, String urlPrefix){
		return request.getServletPath().startsWith(urlPrefix);
	}
	
	/**
	 * 获取当前请求url
	 */
	public static String currrentUrl(HttpServletRequest requst){
		if(requst.getQueryString() != null && !requst.getQueryString().equals("")){
			return requst.getRequestURI() + "?" + requst.getQueryString();
		}
		return requst.getRequestURI();
	}
	
	/**
	 * 指定当前请求url按utf-8编码格式编码
	 * @param currentUrl
	 * @return
	 */
	public static String encodeUtf8(String currentUrl){
		try {
			return URLEncoder.encode(currentUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("HttpRequestUtil encodeUtf8" +e.getMessage());
		}
		return null;
	}
	
	/**
	 * 指定url按utf-8编码格式解码
	 * @param url
	 * @return
	 */
	public static String decodeUtf8(String url) {
		try {
			return URLDecoder.decode(url, "utf8");
		} catch (UnsupportedEncodingException e) {
			log.error("HttpRequestUtil decodeUtf8" +e.getMessage());
		}
		return null;
	}
	
	/**
	 * 清除当前url的contextPath()为空字符串
	 * @param currentUrl
	 * @param request
	 * @return
	 */
	public static String removeServletContexName(String currentUrl, HttpServletRequest request){
		return currentUrl.replaceFirst(request.getContextPath(), "");
	}
	
	/**
	 * 读取请求以字节流的方式返回指定utf-8编号字符串
	 * @param request
	 * @return
	 */
	public static String readStream(HttpServletRequest request){
		byte[] bytes = new byte[1024 * 1024];
		try {
			InputStream is = request.getInputStream();
			int nRead = 1;
			int nTotalRead = 0;
			while (nRead > 0){
				nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
				if(nRead > 0)
					nTotalRead = nTotalRead + nRead;
			}
			return new String(bytes, 0, nTotalRead, "utf-8");	
		} catch (IOException e) {
			log.error("HttpRequestUtil readStream" +e.getMessage());
		}
		return "";
	}
}
