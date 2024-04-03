package com.ssafy.kkoma.global.util;

public class AreaCodeUtils {

	public static long getDigitByCode(long code) {
		return getDigitByLevel(getLevel(code));
	}

	public static long getNextDigitByCode(long code) {
		return getDigitByLevel(getLevel(code) + 1);
	}

	public static long getDigitByLevel(int level) {
		return (long)Math.pow(10, 11 - (level * 3));
	}

	public static int getLevel(long code) {
		for (int i = 1; i < 4; i++) {
			if(code % getDigitByLevel(i) == 0) return i;
		}
		return 4;
	}

}
