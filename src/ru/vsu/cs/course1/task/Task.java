package ru.vsu.cs.course1.task;

import java.util.List;

import static ru.vsu.cs.course1.task.TaskSecondary.*;
import static ru.vsu.cs.course1.task.TaskUtils.*;

public class Task {
	// TODO(Поэкспериментировать и сохранять исходные регистры для всех символов)
	public static class CheckResult {
		// -2  -> в слове есть изначальный корень.
		// -1  -> в слове нет ошибки.
		public int mistakeIndex;
		public int offsetFromStart; // Смещение распознанного корня относительно начала
		public boolean wasCapitalLetter; // Была ли буква по индексу ошибки заглавной
		public static CheckResult getNoErrorResult(){
			return new CheckResult(-1, 0, false);
		}
		public CheckResult(int mistakeIndex, int offsetFromStart, boolean wasCapitalLetter) {
			this.mistakeIndex = mistakeIndex;
			this.offsetFromStart = offsetFromStart;
			this.wasCapitalLetter = wasCapitalLetter;
		}
	}

	public static CheckResult checkWord(char[] word, char[] root) {
		char[] wordCopy = charArrToLower(word);
		int offsetFromStart = 0;
		int mistake = 0;
		int mistakeIndex = -1;
		int rootLength = root.length;
		int j = 0;
		while (offsetFromStart + rootLength <= wordCopy.length) {
			while (j < rootLength) {
				if (wordCopy[j + offsetFromStart] != root[j]) {
					mistake++;
					mistakeIndex = j + offsetFromStart;
				}
				if (mistake > 1) {
					break;
				}
				j++;
			}
			if (mistake == 0){
				return new CheckResult(-2, offsetFromStart, false);
			}
			if (mistake == 1) {
				return new CheckResult(mistakeIndex, offsetFromStart, isCapital(word[mistakeIndex]));
			}
			j = 0;
			mistake = 0;
			offsetFromStart++;
		}
		return CheckResult.getNoErrorResult();
	}

	public static char[] checkWordForAllRoots(char[] word, List<String> roots) {
		for (String root : roots) {
			char[] rootArr = root.toCharArray();
			CheckResult checkRes = checkWord(word, rootArr);
			if (checkRes.mistakeIndex == -2) break;
			if (checkRes.mistakeIndex >= 0) {
				boolean wasCapital = checkRes.wasCapitalLetter;
				char correctLetter = rootArr[checkRes.mistakeIndex - checkRes.offsetFromStart];
				if (!wasCapital && isCapital(correctLetter)){
					correctLetter = toLower(correctLetter);
				}
				if (wasCapital && !isCapital(correctLetter)){
					correctLetter = toUpper(correctLetter);
				}
				word[checkRes.mistakeIndex] = (correctLetter);
				break;
			}
		}
		return word;
	}

	public static void sortRootsByLengthDescending(List<String> roots) {
		for (int i = roots.size() - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				if (roots.get(j).length() < roots.get(j + 1).length()) {
					String temp = roots.get(j);
					roots.set(j, roots.get(j + 1));
					roots.set(j + 1, temp);
				}
			}
		}
	}

	public static String processText(String text, List<String> roots) {
		if (roots == null || roots.isEmpty() || text == null) return text;
		sortRootsByLengthDescending(roots);
		char[] textArr = text.toCharArray();
		int wordCurrentRelativeIndex = 0;
		int i;
		for (i = 0; i < textArr.length; i++) {
			if (isArbitraryOrSeparator(textArr[i]) && wordCurrentRelativeIndex != 0) {
				processWord(roots, textArr, wordCurrentRelativeIndex, i);
				wordCurrentRelativeIndex = 0;
			}
			if (isArbitraryOrSeparator(textArr[i])) {
				continue;
			}
			wordCurrentRelativeIndex++;
		}
		if (wordCurrentRelativeIndex != 0) {
			processWord(roots, textArr, wordCurrentRelativeIndex, i);
		}
		return extractStringFromCharArray(textArr, 0, textArr.length - 1);
	}

	private static void processWord(List<String> roots, char[] textArr, int wordCurrentRelativeIndex, int i) {
		int wordAbsStartIndex = i - wordCurrentRelativeIndex;
		int wordAbsEndIndex = i - 1;
		char[] word = extractCharArrayFromCharArray(textArr, wordAbsStartIndex, wordAbsEndIndex);
		char[] checkedWord = checkWordForAllRoots(word, roots);
		insertCharArrayToCharArray(checkedWord, textArr, wordAbsStartIndex, wordAbsEndIndex);
	}
}