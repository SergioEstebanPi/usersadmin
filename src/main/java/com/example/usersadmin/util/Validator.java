package com.example.usersadmin.util;

import java.util.regex.Pattern;

public class Validator {

	private final static String EMAIL_PATTERN = "^(.+)@(.+)$";
	private final static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$";

	public static boolean patternMatches(String value, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(value).matches();
	}

	public static boolean validateEmail(String value) {
		return Pattern.compile(EMAIL_PATTERN).matcher(value).matches();
	}

	public static boolean validatePassword(String value) {
		return Pattern.compile(PASSWORD_PATTERN).matcher(value).matches();
	}
}
