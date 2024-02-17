package ru.vsu.cs.course1.cli;

import ru.vsu.cs.course1.task.TaskUtils;
import ru.vsu.cs.util.ArrayUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileUtils {
	public static String getTextFromFile(String path){
		List<String> linesList;
		try {
			linesList = TaskUtils.convertStringArrayToStringList(ArrayUtils.readLinesFromFile(path));
		} catch (FileNotFoundException e) {
			//TODO("Файл не найден")
			throw new RuntimeException(e);
		}
		return String.join("\n", linesList);
	}

	public static List<String> getOneDictionary(String dictionaryPath) {
		List<String> dictionary = new ArrayList<>();
		if (Objects.equals(dictionaryPath, "") || dictionaryPath == null) {
			System.err.println("Пустой путь к словарю!");
			return dictionary;
		}
		String[] dictionaryFileLines;
		try {
			dictionaryFileLines = ArrayUtils.readLinesFromFile(dictionaryPath);
		} catch (FileNotFoundException e) {
			System.err.println("Не был найден файл со словарём!");
			return dictionary;
		}
		dictionary.addAll(Arrays.asList(dictionaryFileLines));
		return dictionary;
	}

	public static List<String> getUnitedDictionary(List<String> dictionariesPaths) {
		List<String> dictionary = new ArrayList<>();
		for (String dictionaryPath : dictionariesPaths) {
			dictionary.addAll(getOneDictionary(dictionaryPath));
		}
		return dictionary;
	}
}
