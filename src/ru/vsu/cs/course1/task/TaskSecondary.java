package ru.vsu.cs.course1.task;

public class TaskSecondary {
	public static boolean isSmallLatin(char c) {
		return (c >= 'a' && c <= 'z');
	}

	public static boolean isCapitalLatin(char c) {
		return (c >= 'A' && c <= 'Z');
	}

	public static boolean isCapitalCyrillic(char c) {
		return (c >= 'А' && c <= 'Я');
	}

	public static boolean isSmallCyrillic(char c) {
		return (c >= 'а' && c <= 'я');
	}

	public static boolean isArbitraryOrSeparator(char c) {
		return !isCapitalCyrillic(c) && !isSmallCyrillic(c) && !isSmallLatin(c) && !isCapitalLatin(c);
	}

	public static char[] charArrToLower(char[] charArr) {
		char[] resultArr = charArr.clone();

		for (int i = 0; i < resultArr.length; i++) {
			char c = resultArr[i];
			if (isCapitalLatin(c)) resultArr[i] += 32;
			if (isCapitalCyrillic(c)) resultArr[i] += 32;
		}
		return resultArr;
	}

	public static char toUpper(char c) {
		if (isSmallLatin(c)) {
			c += 'A' - 'a';
		}
		if (isSmallCyrillic(c)){
			c += 'А' - 'а';
		}
		return c;
	}
	public static char toLower(char c) {
		if (isCapitalLatin(c)) {
			c += 'a' - 'A';
		}
		if (isCapitalCyrillic(c)){
			c += 'а' - 'А';
		}
		return c;
	}

	public static boolean isCapital(char c) {
		return isCapitalLatin(c) || isCapitalCyrillic(c);
	}
}