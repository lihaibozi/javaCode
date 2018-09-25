package com.kin207.zn;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jodd.io.FileUtil;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

public class SpringKit implements ApplicationContextAware{
	private static Logger log = Logger.getLogger(SpringKit.class);
	private static ApplicationContext context;
	
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext contex)throws BeansException 
	{
		this.context = contex;
	}

	public static ApplicationContext getContext()
	{
		return context;
	}
	
	public static SqlSession getSqlSession()
	{
		return context.getBean(SqlSessionTemplate.class);
	}

	
	/**
	 * @param propertyPath redis.properties（从classpath读取）
	 */
	public static Properties loadClasspathProperties(String propertyPath) 
	{
		
		Properties properties = new Properties();
		InputStream input = null;
		try {
			Resource resource = new ClassPathResource(propertyPath);
			input = resource.getInputStream();
			properties.load(input);
		} catch (IOException e) {
			log.error("SpringKit loadClasspathProperties " +e.getMessage());
		}finally{
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error("SpringKit loadClasspathProperties" +e.getMessage());
				}
			}
		}
		return properties;
	}
	
	public static String loadContentFrom(String configLocation) 
	{
		try {
			File file = ResourceUtils.getFile(configLocation);
			String content = FileUtil.readString(file);
			return content;
		} catch (Exception e) {
			log.error("SpringKit loadContentFrom" +e.getMessage());
			
		}
		throw new IllegalArgumentException("读取文件内容失败，file path = " + configLocation);
	}

	public static void testReadProperties(Properties pros, String key) 
	{
		System.out.println("---------------------------");
		System.out.println(pros.getProperty(key));
		System.out.println("---------------------------");
	}
	
}
