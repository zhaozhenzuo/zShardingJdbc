package com.z.shard.util;

public class StringUtil {

	private static final String ZERO = "0";

	public static final String leftPadZero(String source, int targetLength) {
		if (source == null) {
			return source;
		}

		if (targetLength <= 0 || source.length() >= targetLength) {
			return source;
		}

		int sub = targetLength - source.length();
		if (sub <= 0) {
			return source;
		}

		StringBuilder buffer = new StringBuilder(sub);
		for (int i = 0; i < sub; i++) {
			buffer.append(ZERO);
		}

		return buffer.toString() + source;
	}

	public static void main(String[] args) {
		String source="1";
		
		String res=leftPadZero(source, 4);
		System.out.println(res);
		
		System.out.println(leftPadZero("128", 5));
	}

}
