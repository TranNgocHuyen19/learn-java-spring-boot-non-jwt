package com.javaweb.ulti;

public class StringUlti {
	public static boolean checkString(String value) {
		if(value != null && !value.equals("")) {
			return true;
		}
		return false;
	}
}
