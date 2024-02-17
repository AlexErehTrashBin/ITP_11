package ru.vsu.cs.course1.task;

import java.util.ArrayList;
import java.util.List;

public class TaskUtils {
	public static List<String> convertStringArrayToStringList(String[] array) {
		List<String> list = new ArrayList<>();
		for (String j : array) {
			list.add(j);
		}
		return list;
	}

	public static String extractStringFromCharArray(char[] arr, int startIndex, int endIndex) {
		char[] newArr = new char[endIndex - startIndex + 1];
		for (int i = startIndex; i <= endIndex; i++) {
			newArr[i - startIndex] = arr[i];
		}
		return String.valueOf(newArr);
	}

	public static char[] extractCharArrayFromCharArray(char[] arr, int startIndex, int endIndex) {
		char[] newArr = new char[endIndex - startIndex + 1];
		for (int i = startIndex; i <= endIndex; i++) {
			newArr[i - startIndex] = arr[i];
		}
		return newArr;
	}

	public static void insertCharArrayToCharArray(char[] what, char[] where, int startIndex, int endIndex) {
		for (int i = startIndex; i <= endIndex; i++) {
			where[i] = what[i - startIndex];
		}
	}
}