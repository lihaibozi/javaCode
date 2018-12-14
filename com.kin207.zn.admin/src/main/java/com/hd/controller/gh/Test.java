package com.hd.controller.gh;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<String> listString = new ArrayList<String>();
		listString.add("aaa");
		listString.add("bbb");
		listString.add("ccc");
		System.out.println(listString.get(0));
		System.out.println(listString.get(listString.size()-1));
	}
}
