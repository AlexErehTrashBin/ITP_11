package ru.vsu.cs.course1.gui;

import ru.vsu.cs.course1.cli.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class GUIUtils {
	public static List<String> getRootsFromMatrix(String[][] matrix){
		List<String> resultingList = new ArrayList<>();
		for (String[] line: matrix) {
			resultingList.add(line[0]);
		}
		return resultingList;
	}

	public static String[][] getStringMatrixFromFile(String path) {
		List<String> roots = FileUtils.getOneDictionary(path);
		String[][] matrix = new String[roots.size()][1];
		for (int i = 0; i < roots.size(); i++) {
			matrix[i][0] = roots.get(i);
		}
		return matrix;
	}
}
