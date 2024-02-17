package ru.vsu.cs.course1.gui;

import ru.vsu.cs.course1.task.Task;
import ru.vsu.cs.util.ArrayUtils;
import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FrameMain extends JFrame {
	private JPanel panelMain;
	private JButton buttonLoadInputFromFile;
	private JButton buttonSaveInputIntoFile;
	/**
	 * Кнопка "Решить таск"
	 */
	private JButton buttonSolve;
	private JButton buttonSaveOutputIntoFile;
	/**
	 * Поле ввода для многострочного текста, который подаётся на вход программе.
	 */
	private JTextPane textInput;
	/**
	 * Выходной текст в многострочном виде.
	 */
	private JTextArea textOutput;
	private JTable rootsTable;
	private JButton loadDictionaryFromFileButton;
	private JButton clearRootsButton;

	private final JFileChooser fileChooserOpen;
	private final JFileChooser fileChooserSave;

	public FrameMain() {
		this.setTitle("FrameMain");
		this.setContentPane(panelMain);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();

		JTableUtils.initJTableForArray(rootsTable, 250, false, false, true, false);
		// Установка стандартного текста и корней к нему (пример из задания).
		textInput.setText("Карова дает малако каторае пъут дэти");
		//textField1.setText("молок коров апельсин котор дети");

		fileChooserOpen = new JFileChooser();
		fileChooserSave = new JFileChooser();
		fileChooserOpen.setCurrentDirectory(new File("."));
		fileChooserSave.setCurrentDirectory(new File("."));
		FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
		fileChooserOpen.addChoosableFileFilter(filter);
		fileChooserSave.addChoosableFileFilter(filter);

		fileChooserSave.setAcceptAllFileFilterUsed(false);
		fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooserSave.setApproveButtonText("Save");

		JMenuBar menuBarMain = new JMenuBar();
		setJMenuBar(menuBarMain);

		JMenu menuLookAndFeel = new JMenu();
		menuLookAndFeel.setText("Вид");
		menuBarMain.add(menuLookAndFeel);
		SwingUtils.initLookAndFeelMenu(menuLookAndFeel);
		rootsTable.setRowHeight(25);
		JTableUtils.writeArrayToJTable(rootsTable, new String[][]{
				{"молок"},
				{"коров"},
				{"апельсин"},
				{"котор"},
				{"дети"}
		});

		this.pack();


		buttonLoadInputFromFile.addActionListener(actionEvent -> {
			try {
				if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
					List<String> lines = new ArrayList<>(List.of(ArrayUtils.readLinesFromFile(fileChooserOpen.getSelectedFile().getPath())));
					StringBuilder text = new StringBuilder();
					for (String line: lines) {
						text.append(line).append("\n");
					}
					textInput.setText(text.toString());
				}
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
		buttonSaveInputIntoFile.addActionListener(actionEvent -> {
			try {
				if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
					String text = textInput.getText();
					String file = fileChooserSave.getSelectedFile().getPath();
					if (!file.toLowerCase().endsWith(".txt")) {
						file += ".txt";
					}
					try (PrintWriter out = new PrintWriter(file)) {
						out.println(text);
					}
				}
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
		loadDictionaryFromFileButton.addActionListener(actionEvent -> {
			try {
				if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION){
					String[][] matrix = GUIUtils.getStringMatrixFromFile(fileChooserOpen.getSelectedFile().getPath());
					JTableUtils.writeArrayToJTable(rootsTable, matrix);
				}
			} catch (Exception e){
				SwingUtils.showErrorMessageBox(e);
			}
		});
		buttonSaveOutputIntoFile.addActionListener(actionEvent -> {
			try {
				if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
					String text = textOutput.getText();
					String file = fileChooserSave.getSelectedFile().getPath();
					if (!file.toLowerCase().endsWith(".txt")) {
						file += ".txt";
					}
					if (!file.toLowerCase().endsWith(".txt")) {
						file += ".txt";
					}
					try (PrintWriter out = new PrintWriter(file)) {
						out.println(text);
					}
				}
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
		buttonSolve.addActionListener(actionEvent -> {
			try {
				List<String> roots = GUIUtils.getRootsFromMatrix(Objects.requireNonNull(JTableUtils.readStringMatrixFromJTable(rootsTable)));
				String tempTxt = Task.processText(textInput.getText(), roots);
				textOutput.setText(tempTxt);
			} catch (Exception e) {
				SwingUtils.showErrorMessageBox(e);
			}
		});
		clearRootsButton.addActionListener(actionEvent -> {
			try {
				String[][] emptyMatrix = new String[][]{
						{""}
				};
				JTableUtils.writeArrayToJTable(rootsTable, emptyMatrix);
				//rootsTable.
			} catch (Exception e){
				SwingUtils.showErrorMessageBox(e);
			}
		});
	}
}
