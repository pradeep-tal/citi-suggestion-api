package com.test;

import java.util.Arrays;
import java.util.List;


public class StringJoinerTest {

	public static void main(String args[])
	{
		String ss="a b c";
		
		List<String> l=Arrays.asList(ss.split(" "));
		
		System.out.println(l.size());
		
		System.out.println(System.lineSeparator());

		System.out.println(System.getProperty("line.separator"));
		
		System.out.println(org.apache.commons.lang3.StringUtils.join(l, System.getProperty("line.separator")));
	}

}
