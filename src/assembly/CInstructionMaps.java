package assembly;

import java.util.HashMap;

public class CInstructionMaps {
	//Hashmap to store translation of C instructions comp part
	private HashMap<String, String> cInstructionCompMap;
	
	//Hashmap to store translation of C instructions dest part
	private HashMap<String, String> cInstructionDestMap;
	
	//Hashmap to store translation of C instructions jmp part
	private HashMap<String, String> cInstructionJmpMap;
	
	public CInstructionMaps() {
		initializeCCompMap();
		initializeCDestMap();
		initializeCJmpMap();
	}
	
	private void initializeCCompMap() {
		cInstructionCompMap = new HashMap<String, String>();
		cInstructionCompMap.put("0", "0101010");
		cInstructionCompMap.put("1", "0111111");
		cInstructionCompMap.put("-1", "0111010");
		cInstructionCompMap.put("D", "0001100");
		cInstructionCompMap.put("A", "0110000");
		cInstructionCompMap.put("!D", "0001111");
		cInstructionCompMap.put("!A", "0110001");
		cInstructionCompMap.put("-D", "0001111");
		cInstructionCompMap.put("-A", "0110011");
		cInstructionCompMap.put("D+1", "0011111");
		cInstructionCompMap.put("A+1", "0110111");
		cInstructionCompMap.put("D-1", "0001110");
		cInstructionCompMap.put("A-1", "0110010");
		cInstructionCompMap.put("D+A", "0000010");
		cInstructionCompMap.put("D-A", "0010011");
		cInstructionCompMap.put("A-D", "0000111");
		cInstructionCompMap.put("D&A", "0000000");
		cInstructionCompMap.put("D|A", "0010101");
		cInstructionCompMap.put("M", "1110000");
		cInstructionCompMap.put("!M", "1110001");
		cInstructionCompMap.put("-M", "1110011");
		cInstructionCompMap.put("M+1", "1110111");
		cInstructionCompMap.put("M-1", "1110010");
		cInstructionCompMap.put("D+M", "1000010");
		cInstructionCompMap.put("D-M", "1010011");
		cInstructionCompMap.put("M-D", "1000111");
		cInstructionCompMap.put("D&M", "1000000");
		cInstructionCompMap.put("D|M", "1010101");
	}
	
	private void initializeCDestMap() {
		cInstructionDestMap = new HashMap<String, String>();
		cInstructionDestMap.put("", "000");
		cInstructionDestMap.put("M", "001");
		cInstructionDestMap.put("D", "010");
		cInstructionDestMap.put("MD", "011");
		cInstructionDestMap.put("A", "100");
		cInstructionDestMap.put("AM", "101");
		cInstructionDestMap.put("AD", "110");
		cInstructionDestMap.put("AMD", "111");
	}
	
	private void initializeCJmpMap() {
		cInstructionJmpMap = new HashMap<String, String>();
		cInstructionJmpMap.put("", "000");
		cInstructionJmpMap.put("JGT", "001");
		cInstructionJmpMap.put("JEQ", "010");
		cInstructionJmpMap.put("JGE", "011");
		cInstructionJmpMap.put("JLT", "100");
		cInstructionJmpMap.put("JNE", "101");
		cInstructionJmpMap.put("JLE", "110");
		cInstructionJmpMap.put("JMP", "111");
	}
	
	public HashMap<String, String> getDestMap(){
		return cInstructionDestMap;
	}
	
	public HashMap<String, String> getCompMap(){
		return cInstructionCompMap;
	}
	
	public HashMap<String, String> getJmpMap(){
		return cInstructionJmpMap;
	}
}
