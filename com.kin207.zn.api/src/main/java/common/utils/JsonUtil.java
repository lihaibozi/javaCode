package common.utils;

import java.util.HashMap;
import java.util.Map;

import jodd.json.JsonParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json工具类
 * @author wujun
 */
public class JsonUtil {

	public static String toJson(Object obj){
		if(obj == null){
			return null;
		}
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(obj);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String json) {
		return new JsonParser().parse(json, HashMap.class);
	}
	
}
