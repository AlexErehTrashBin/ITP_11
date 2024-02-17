package ru.vsu.cs.course1.cli;

import org.apache.commons.cli.*;
import ru.vsu.cs.course1.task.Task;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class InputArgsUtils {
	public static final String WINDOWED_HT = "Запустить с оконным интерфейсом.";
	public static final String HELP_HT = "Показать справку.";
	public static final String TEST_HT = "Прогнать файлы из папки testIn и записать результаты в файлы с теми же названиями, " +
			"но в папку testOut.";
	public static final String IF_HT = "Входные файлы.";
	public static final String OF_HT = "Выходные файлы. " +
			"Очерёдность соответствует порядку получения входных файлов.";
	public static final String DICTIONARIES_HT = "Файлы со словарями. " +
			"Действуют к каждому переданному файлу.";

	/*
	 * Объявление возможных аргументов командной строки
	 * */
	public static Options fillOptions() {
		Options options = new Options();
		Option windowed = Option.builder("w")
				.longOpt("window")
				.desc(WINDOWED_HT)
				.build();
		Option help = Option.builder("h")
				.longOpt("help")
				.desc(HELP_HT)
				.build();
		Option tests = Option.builder("t")
				.longOpt("tests")
				.desc(TEST_HT)
				.build();
		Option inputFiles = Option.builder("i")
				.longOpt("input-files")
				.hasArgs()
				.valueSeparator(',')
				.desc(IF_HT)
				.build();
		Option dictionaryFiles = Option.builder("d")
				.longOpt("dictionaries")
				.hasArgs()
				.valueSeparator(',')
				.desc(DICTIONARIES_HT)
				.build();
		Option outputFiles = Option.builder("o")
				.longOpt("output-files")
				.hasArgs()
				.valueSeparator(',')
				.desc(OF_HT)
				.build();
		Option forEachDictionary = Option.builder("e")
				.longOpt("foreach-dictionary")
				.desc(TEST_HT)
				.build();
		options.addOption(windowed);
		options.addOption(help);
		options.addOption(inputFiles);
		options.addOption(dictionaryFiles);
		options.addOption(outputFiles);
		options.addOption(tests);
		options.addOption(forEachDictionary);

		return options;
	}
	public static void individualFileCheck(String inFile, String outFile, List<String> dictionaries) {
		System.out.printf("------------------------------%n");
		System.out.printf("Обрабатываю файл: %s %n", inFile);

		/// Получение текста
		String allText = FileUtils.getTextFromFile(inFile);
		/// Получение словарей
		List<String> roots = FileUtils.getUnitedDictionary(dictionaries);
		if (roots.isEmpty()) return;
		System.out.println("Корни: ");
		System.out.println(roots);


		/// Заключительные действия
		System.out.println("Входной текст:");
		System.out.println(allText);

		String result = Task.processText(allText, roots);

		try {
			PrintStream psArr = new PrintStream(outFile);
			System.out.println("Выходной текст: ");
			System.out.println(result);
			psArr.print(result);
			psArr.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(2);
		}

		System.out.printf("------------------------------%n");
	}

	public static void multipleFilesCheck(String[] inFiles, String[] outFiles, List<String> dictionaries) {
		int overallCalculations = Math.min(inFiles.length, outFiles.length);
		for (int iFI = 0; iFI < overallCalculations; iFI++) {
			individualFileCheck(inFiles[iFI], outFiles[iFI], dictionaries);
		}
	}

	public static void runTests(List<String> dictionaryFiles) {
		String workingDir = System.getProperty("user.dir");
		File inputFilesDir = new File(workingDir + "\\testIn\\");
		File outputFilesDir = new File(workingDir + "\\testOut\\");
		if (!inputFilesDir.exists()) {
			try {
				if (!inputFilesDir.mkdir()){
					throw new RuntimeException("Не удалось создать папку с входными файлами для тестов!");
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(10);
			}

		}
		if (!outputFilesDir.exists()) {
			try {
				if (!outputFilesDir.mkdir()){
					throw new RuntimeException("Не удалось создать папку с выходными файлами для тестов!");
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.exit(11);
			}
		}
		File[] inputFiles = inputFilesDir.listFiles();
		if (inputFiles == null) return;
		String[] inputFilesPaths = new String[inputFiles.length];
		String[] outputFilesPaths = new String[inputFiles.length];
		for (int i = 0; i < inputFiles.length; i++) {
			inputFilesPaths[i] = "testIn\\" + inputFiles[i].getName();
			outputFilesPaths[i] = "testOut\\" + inputFiles[i].getName();
			//System.out.println(inputFiles[i].getName());
		}

		multipleFilesCheck(inputFilesPaths, outputFilesPaths, dictionaryFiles);

	}

	public static void showHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("<cmd> [OPTIONS]", options);
	}

	public static InputArgs parseCmdArgs(String[] args) {
		InputArgs inputArgs = new InputArgs();
		if (args == null) return inputArgs;
		Options options = fillOptions();
		/*
		 * Непосредственный парсинг.
		 * */
		CommandLine line;
		try {
			line = new DefaultParser().parse(options, args);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return inputArgs;
		}
		if (line.hasOption("h")) {
			showHelp(options);
			return inputArgs;
		}
		if (line.hasOption("w")) {
			System.out.println("Будет запущен оконный интерфейс");
			inputArgs.window = true;
		}
		if (!line.hasOption("d")){
			System.err.println("Не был(-и) найден словарь(-и). Будет выведена справка.");
			showHelp(options);
			return inputArgs;
		}
		inputArgs.dictionaryFiles = Arrays.asList(line.getOptionValues("d"));
		if (line.hasOption("e")){
			inputArgs.checkForEachDictionaryIndividually = true;
		}
		if (line.hasOption("t")) {
			System.out.println("Тесты:");
			inputArgs.runTests = true;
		}
		if (line.hasOption("i") && line.hasOption("o")) {
			inputArgs.runIndividualFilesCheck = true;
			String[] inputFiles = line.getOptionValues("i");
			String[] outputFiles = line.getOptionValues("o");
			System.out.println("Будут обработаны следующие файлы: ");
			System.out.printf("%s - как входные. %n", Arrays.toString(inputFiles));
			System.out.printf("%s - как выходные. %n", Arrays.toString(outputFiles));
			inputArgs.inputFiles = inputFiles;
			inputArgs.outputFiles = outputFiles;
		}
		return inputArgs;
	}
}