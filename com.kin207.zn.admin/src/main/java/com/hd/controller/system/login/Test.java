package com.hd.controller.system.login;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		String str = "2019-10-15";
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		Long time;
		try {
			time =  new Date().getTime()-sdf.parse(str).getTime();
			System.out.println(time);
			System.out.println(time/1000/24/60/60/365);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
}
