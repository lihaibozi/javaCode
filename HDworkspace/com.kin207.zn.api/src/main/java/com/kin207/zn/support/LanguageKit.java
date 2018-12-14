package com.kin207.zn.support;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.utils.StringUtil;


public class LanguageKit {
	
	public static String LANGUAGE_EN = "EN";
	public static String LANGUAGE_ZH_HANS = "CH";
	public static String LANGUAGE_ZH_HANT = "CH-tT";
	public static String LANGUAGE_KOREAN = "KOREAN";
	public static String LANGUAGE_JP = "JAPANESE";
	public static String LANGUAGE_DEFAULT ;
	
	
	private static final String POSTFIX_ZH = "";
	private static final String POSTFIX_EN = "En";
	private static final String POSTFIX_KOREAN = "Kor";
	private static final String POSTFIX_JP = "Jp";
	
	public static final String POSTFIX_DEFAULT = POSTFIX_ZH;
	
	private static final String UNDERLINE_POSTFIX_ZH = "";
	private static final String UNDERLINE_POSTFIX_EN = "_En";
	private static final String UNDERLINE_POSTFIX_KOREAN = "_Kor";
	private static final String UNDERLINE_POSTFIX_JP = "_Jp";
	
	private static final String UNDERLINE_POSTFIX_DEFAULT = UNDERLINE_POSTFIX_ZH;
	
	public static final Set<String> SET_POSTFIX;
	
	static{
		SET_POSTFIX = new HashSet<String>();
		SET_POSTFIX.add(POSTFIX_ZH);
		SET_POSTFIX.add(POSTFIX_EN);
		SET_POSTFIX.add(POSTFIX_KOREAN);
		SET_POSTFIX.add(POSTFIX_JP);
	}
	
	public static boolean isKorean(String lang) 
	{
		return lang != null && LANGUAGE_KOREAN.equals(lang);
	}
	
	public static boolean isEn(String lang) 
	{
		return lang != null && LANGUAGE_EN.equals(lang);
	}
	public static boolean isJp(String lang) 
	{
		return lang != null && LANGUAGE_JP.equals(lang);
	}
	public static boolean isZh(String lang) 
	{
		if (lang == null) 
		{
			return false;
		}
		return lang.equals(LANGUAGE_ZH_HANS) || lang.equals(LANGUAGE_ZH_HANT);
	}
	
	public static void filter(List<Map<String, Object>> list, String keys, String lang, String defaultLang) 
	{
		if (list == null || list.isEmpty()) 
		{
			return;
		}
		String[] keyArr = StringUtil.splitToArray(keys, ",");
		for (Map<String, Object> map : list) 
		{
			for (String key : keyArr) 
			{
				String langKey = key + postfixOf(lang);
				
				if (map.containsKey(langKey) && map.get(langKey) != null) 
				{
					map.put(key, map.get(langKey));
				}else 
				{
					String defaultKey = key + postfixOf(defaultLang);
					if (map.containsKey(defaultKey) && map.get(defaultKey) != null)
					{
						map.put(key, map.get(defaultKey));
					}
				}
				removeLangKey(map, key);
			}
		}
	}
	
	public static void filter(List<Map<String, Object>> list, String keys, String lang) 
	{
		if (list == null || list.isEmpty()) 
		{
			return;
		}
		String[] keyArr = StringUtil.splitToArray(keys, ",");
		for (Map<String, Object> map : list) 
		{
			filter(map, keyArr, lang);
		}
	}
	
	public static void filter(Map<String, Object> resultMap, String keys, String lang) 
	{
		String[] keyArr = StringUtil.splitToArray(keys, ",");
		filter(resultMap, keyArr, lang);
	}
	
	private static void filter(Map<String, Object> resultMap, String[] keyArr, String lang) 
	{
		if (resultMap == null || resultMap.isEmpty()) 
		{
			return;
		}
		
		for (String key : keyArr) 
		{
			String postfix = LanguageKit.postfixOf(lang);
			String langKey = key + postfix;
			Object value = resultMap.get(langKey);
			resultMap.put(key, value);
			
			removeLangKey(resultMap, key);
		}
	}
	
	/**
	 * @param resultMap {key, keyEn, keyKor}
	 * @param key
	 * @return {key}
	 */
	private static void removeLangKey(Map<String, Object> resultMap, String key)
	{
		for (String postfix : LanguageKit.SET_POSTFIX) 
		{
			if (postfix.equals(""))
			{
				continue;
			}
			String langKey = key + postfix;
			resultMap.remove(langKey);
		}
	}
	
	private static String postfixOf(String lang) 
	{
		if (isEn(lang)) 
		{
			return POSTFIX_EN;
		}
		if (isKorean(lang)) 
		{
			return POSTFIX_KOREAN;
		}
		if (isZh(lang)) 
		{
			return POSTFIX_ZH;
		}
		return POSTFIX_DEFAULT;
	}
	
	public static String underlinePostfixOf(String lang) 
	{
		if (isEn(lang)) 
		{
			return UNDERLINE_POSTFIX_EN;
		}
		if (isKorean(lang)) 
		{
			return UNDERLINE_POSTFIX_KOREAN;
		}
		if (isZh(lang)) 
		{
			return UNDERLINE_POSTFIX_ZH;
		}
		if (isJp(lang)) 
		{
			return UNDERLINE_POSTFIX_JP;
		}
		return UNDERLINE_POSTFIX_DEFAULT;
	}
}
