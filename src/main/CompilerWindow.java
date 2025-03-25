package main;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class CompilerWindow {
	
	private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	
	private Compiler compiler;
	
	private JFrame frame;
	
	private JPanel mainPanel;
	private JPanel asmPanel;
	private JPanel inputPanel;
	private JPanel hackPanel;
	
	private JTextArea asmTextArea;
	private JScrollPane asmScrollPane;
	
	private JTextArea hackTextArea;
	private JScrollPane hackScrollPane;
	
	private JTextField fileNameTextField;
	private JButton compileButton;
	
	private JLabel asmTitleLabel;
	private JLabel hackTitleLabel;
	private JLabel fileNameTitleLabel;
	
	
	public CompilerWindow(Compiler compiler) {
		this.compiler = compiler;
		initialize();
	}

	private void initialize() {
		setFrame();
		
		setMainPanel();
		
		setAsmPanel();
		
		setAsmTextArea();

		setHackPanel();
		
		setHackTextArea();
		
		setInputPanel();
		
		setInputPanelTextField();

		setInputPanelCompileButton();
		
		setTitles();
	}
	
	private void setFrame() {
		this.frame = new JFrame();
		this.frame.setResizable(false);
		this.frame.setTitle("Hack Compiler");
	    this.frame.setSize(750, 700);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(new FlowLayout());
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
	}
	
	private void setMainPanel() {
		this.mainPanel = new JPanel();
		this.mainPanel.setPreferredSize(new Dimension(700,675));
		this.frame.getContentPane().add(this.mainPanel, BorderLayout.CENTER);
		this.mainPanel.setLayout(null);
	}
	
	private void setAsmPanel() {
		this.asmPanel = new JPanel();
		this.asmPanel.setBounds(25, 100, 250, 500);
		this.asmPanel.setBackground(new Color(201, 201, 201));
		this.asmPanel.setLayout(null);
		this.mainPanel.add(this.asmPanel);
	}
	
	private void setAsmTextArea() {
		this.asmTextArea = new JTextArea();
		this.asmTextArea.setBackground(new Color(201, 201, 201));
		this.asmTextArea.setEditable(false);
		this.asmTextArea.setFont(FONT);
		setAsmScrollPane();
	}
	
	private void setAsmScrollPane() {
		this.asmScrollPane = new JScrollPane(this.asmTextArea);
		this.asmScrollPane.setBounds(10, 0, 240, 500);
		this.asmScrollPane.setBorder(null);
		this.asmPanel.add(this.asmScrollPane);
	}
	
	private void setHackPanel() {
		this.hackPanel = new JPanel();
		this.hackPanel.setBounds(425, 100, 250, 500);
		this.hackPanel.setBackground(new Color(201, 201, 201));
		this.hackPanel.setLayout(null);
		this.mainPanel.add(this.hackPanel);
	}
	
	private void setHackTextArea() {
		this.hackTextArea = new JTextArea();
		this.hackTextArea.setBackground(new Color(201, 201, 201));
		this.hackTextArea.setEditable(false);
		this.hackTextArea.setFont(FONT);
		setHackScrollPane();
	}
	
	private void setHackScrollPane() {
		this.hackScrollPane = new JScrollPane(this.hackTextArea);
		this.hackScrollPane.setBounds(10, 0, 240, 500);
		this.hackScrollPane.setBorder(null);
		this.hackPanel.add(this.hackScrollPane);
	}
	
	private void setInputPanel() {
		this.inputPanel = new JPanel();
		this.inputPanel.setBounds(230, 50, 240, 40);
		this.mainPanel.add(this.inputPanel);
	}
	
	private void setInputPanelTextField() {
		this.fileNameTextField = new JTextField();
		this.inputPanel.add(this.fileNameTextField);
		this.fileNameTextField.setColumns(10);
	}
	
	private void setInputPanelCompileButton() {
		this.compileButton = new JButton("Compilar");
		this.inputPanel.add(this.compileButton);
		this.compileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonPress();
            }
        });
	}
	
	private void buttonPress() {
		String fileName = fileNameTextField.getText().trim();
	    File asmFile = new File(compiler.getInputPath() + fileName + ".asm");
	    
	    if (!asmFile.exists()) {
	        showFileNotFoundError(fileName);
	        return;
	    }else {
	    	compiler.initialize(fileName);
	    	writeTextContent(fileName);
	    	asmTextArea.setCaretPosition(0);
	    	hackTextArea.setCaretPosition(0);
	    }
	}
	
	private void showFileNotFoundError(String fileName) {
	    JOptionPane.showMessageDialog(
	        frame,
	        "File not found: " + fileName + ".asm",
	        "Input Error",
	        JOptionPane.ERROR_MESSAGE
	    );
	    
	    asmTextArea.setText("");
	    hackTextArea.setText("");
	}
	
	private void setTitles() {
		setAsmTitle();
		setHackTitle();
		setFileNameTitle();
	}
	
	private void setAsmTitle() {
		this.asmTitleLabel = DefaultComponentFactory.getInstance().createTitle(".asm file content");
		Dimension asmTitleLabelDimension = this.asmTitleLabel.getPreferredSize();
		this.asmTitleLabel.setBounds(110, 75, 0, 0);
		this.asmTitleLabel.setSize(asmTitleLabelDimension);
		this.mainPanel.add(this.asmTitleLabel);
	}
	
	private void setHackTitle() {
		this.hackTitleLabel = DefaultComponentFactory.getInstance().createTitle(".hack file content");
		Dimension hackTitleLabelDimension = this.hackTitleLabel.getPreferredSize();
		this.hackTitleLabel.setBounds(510, 75, 0, 0);
		this.hackTitleLabel.setSize(hackTitleLabelDimension);
		this.mainPanel.add(this.hackTitleLabel);
	}
	
	private void setFileNameTitle() {
		this.fileNameTitleLabel = DefaultComponentFactory.getInstance().createTitle("Ingrese el nombre del archivo a compilar (sin extension):");
		Dimension fileNameTitleDimension = this.fileNameTitleLabel.getPreferredSize();
		this.fileNameTitleLabel.setBounds(200, 28, 88, 14);
		this.fileNameTitleLabel.setSize(fileNameTitleDimension);
		this.mainPanel.add(this.fileNameTitleLabel);
	}
	
	private void writeTextContent(String fileName) {
		String asmFileLocation = this.compiler.getInputPath() + fileName + ".asm";
	    this.asmTextArea.setText(readFile(asmFileLocation).toString());
		
	    String hackFileLocation = this.compiler.getOutputPath() + fileName + ".hack";
	    this.hackTextArea.setText(readFile(hackFileLocation).toString());
	}
	
	private StringBuilder readFile(String filePath){
		StringBuilder toReturn = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
	        while ((line = reader.readLine()) != null) {
	        	line = line.trim();
	        	
	        	if (toReturn.length() > 0) {
	        		toReturn.append(System.lineSeparator());
        		}
	        	
	        	toReturn.append(line);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
}
