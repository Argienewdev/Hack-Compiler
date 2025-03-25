package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import assembly.CInstructionMaps;
import assembly.Variables;

/**
 * Assembles valid Hack assembly code into machine language.
 * Input assumptions:
 * 
 * - All A-instructions are well-formed (@ followed by label/number)
 * 
 * - All C-instructions have valid dest=comp;jump parts
 * 
 * - No whitespace or comments remain after preprocessing
 */

public class Compiler {
	
	static final String INPUT_BASE_PATH = "programs\\input\\";
	static final String OUTPUT_BASE_PATH = "programs\\output\\";
	
	private static final String C_INSTRUCTION_PREFIX = "111";
	private static final String EMPTY = "";
	
	private CInstructionMaps cInstructionMaps;
	private String inputFileLocation;
	private String outputFileLocation;
	private File inputFile;
	private File outputFile;
	private HashMap<String, Integer> labels;
	private Variables variables;
	
	public Compiler() {
		this.cInstructionMaps = new CInstructionMaps();
		this.labels = new HashMap<String, Integer>();
		this.variables = new Variables();
	}
	
	public void initialize(String fileName) {
		setFileLocations(fileName);
		initializeFiles();
		
		List<String> cleanFileLines = getCleanFileLines();
		
		extractLabels(cleanFileLines);
		extractVariables(cleanFileLines);
		translateToHACK(cleanFileLines);
	}
	
	private void extractLabels(List<String> lines) {
		int pc = 0;
        for (String line : lines){
            if (line.startsWith("(")) {
            	this.labels.put(line.substring(1, line.indexOf(')')), pc);
            }else {
            	pc++; // Increments PC only if there was no tag declaration
            }
        }
	  }
	
	private void extractVariables(List<String> lines) {
		for (String line : lines) {
			if (line.startsWith("@") && 
					!labels.containsKey(line.substring(1)) &&
					!Character.isDigit(line.charAt(1))) {
				this.variables.addVariable(line.substring(1));
			}
		}
	}
	
	private void translateToHACK(List<String> lines) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFile))){
		    StringBuilder output = new StringBuilder();
	        for (String line : lines) {
	        	if(!line.startsWith("(")) {
	        		if (output.length() > 0) {
	        			output.append(System.lineSeparator());
	        		}
	        		output.append(assemble(line));
	        	}
	        }
	        writer.write(output.toString());
	        System.out.println("Compilation complete: " + outputFile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	private List<String> getCleanFileLines() {
		List<String> toReturn = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(this.inputFile))) {
			String line;
	        while ((line = reader.readLine()) != null) {
	        	line = line.trim();
	        	if(!line.startsWith("//") && !line.isEmpty()) {
	        		toReturn.add(line.replaceAll(" ", EMPTY));
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	private void initializeFiles() {
		this.inputFile = new File(this.inputFileLocation);
		this.outputFile = new File(this.outputFileLocation);
	}
	
	private void setFileLocations(String fileName) {
        this.inputFileLocation = INPUT_BASE_PATH + fileName + ".asm";
        this.outputFileLocation = OUTPUT_BASE_PATH + fileName + ".hack";
	}
	
	private boolean isAInstruction(String line) {
		return line.charAt(0) == '@';
	}
	
	private boolean isCInstruction(String line) {
		return !isAInstruction(line);
	}
	
	private String assemble(String line) {
		line = removeInlineComments(line);
		
		String assembledInstruction = EMPTY;
		
		if(isAInstruction(line)) {
			assembledInstruction = assembleAInstruction(line);
		}else if(isCInstruction(line)){
			assembledInstruction = assembleCInstruction(line);
		}
		return assembledInstruction;
	}
	
	private String assembleAInstruction(String line) {
		int constant = 0;
		if(Character.isDigit(line.charAt(1))) {
			constant = Integer.parseInt(line.substring(1));
		}else {
			//Not a constant, but a reference
			if(labels.containsKey(line.substring(1))) {
				constant = labels.get(line.substring(1));
			}else {
				constant = variables.getVariableLocation(line.substring(1));
			}
		}
		String assembledAInstruction = toBinary16(constant);
		return assembledAInstruction;
	}
	
	private String assembleCInstruction(String line) {
		String[] parts = splitParts(line);
		parts = parseToBinary(parts[0], parts[1], parts[2]);
		String assembledCInstruction = C_INSTRUCTION_PREFIX + parts[1] + parts[0] + parts[2];
		return assembledCInstruction;
	}
	
	private String[] splitParts(String line) {
		String destPart = EMPTY;
		String compPart = EMPTY;
		String jmpPart = EMPTY;
		
		int destIndex = line.indexOf('=');
		int jmpIndex = line.indexOf(';');
		
		boolean foundDest = destIndex != -1;
		boolean foundJmp = jmpIndex != -1;
		
		if(foundDest && foundJmp) {
			destPart = line.substring(0, destIndex);
			compPart = line.substring(destIndex + 1, jmpIndex);
			jmpPart = line.substring(jmpIndex + 1);
		}else if(foundDest) {
			destPart = line.substring(0, destIndex);
			compPart = line.substring(destIndex + 1);
		}else if(foundJmp){
			compPart = line.substring(0, jmpIndex);
			jmpPart = line.substring(jmpIndex + 1);
		}else {
			compPart = line;
		}
		
		String[] parts = new String[3];
		parts[0] = destPart;
		parts[1] = compPart;
		parts[2] = jmpPart;
		return parts;
	}
	
	private String[] parseToBinary(String destPart, String compPart, String jmpPart) {
		destPart = cInstructionMaps.getDestMap().get(destPart);
		compPart = cInstructionMaps.getCompMap().get(compPart);
		jmpPart = cInstructionMaps.getJmpMap().get(jmpPart);
		String[] parts = new String[3];
		parts[0] = destPart;
		parts[1] = compPart;
		parts[2] = jmpPart;
		return parts;
	}
	
	private String toBinary16(int value) {
	    return String.format("%16s", Integer.toBinaryString(value)).replace(' ', '0');
	}
	
	private String removeInlineComments(String line) {
		if(line.contains("//")) {
			int commentStart = line.indexOf("/");
			line = line.substring(0,commentStart);
		}
		return line;
	}
	
	public String getInputPath() {
		return INPUT_BASE_PATH;
	}
	
	public String getOutputPath() {
		return OUTPUT_BASE_PATH;
	}
}
