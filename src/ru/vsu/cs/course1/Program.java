package ru.vsu.cs.course1;

import ru.vsu.cs.course1.cli.InputArgs;
import ru.vsu.cs.course1.gui.FrameMain;
import ru.vsu.cs.util.SwingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ru.vsu.cs.course1.cli.InputArgsUtils.*;


public class Program {

	public static void winMain() {
		SwingUtils.setLookAndFeelByName("Windows");
		Locale.setDefault(Locale.ROOT);
		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtils.setDefaultFont("Microsoft Sans Serif", 18);

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(() -> new FrameMain().setVisible(true));
	}

	public static void main(String[] args){
		InputArgs params = parseCmdArgs(args);
		if (params.window) {
			winMain();
		}
		if (params.runTests){
			runTests(params.dictionaryFiles);
		}
		if (params.runIndividualFilesCheck){
			int numOfInFiles = params.inputFiles.length;
			int numOfOutFiles = params.outputFiles.length;
			int overallCalculations = Math.min(numOfInFiles, numOfOutFiles);
			for (int iFI = 0; iFI < overallCalculations; iFI++) {
				if (!params.checkForEachDictionaryIndividually){
					individualFileCheck(params.inputFiles[iFI], params.outputFiles[iFI], params.dictionaryFiles);
					continue;
				}
				for (String dictionaryFile: params.dictionaryFiles) {
					List<String> dictionariesOfOneDictionary = new ArrayList<>();
					dictionariesOfOneDictionary.add(dictionaryFile);
					individualFileCheck(params.inputFiles[iFI], params.outputFiles[iFI], dictionariesOfOneDictionary);
				}
			}
		}
	}
}
