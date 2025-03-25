package assembly;

import java.util.HashMap;

public class Variables {
	
	private int size;
	private HashMap<String, Integer> variables;
	
	public Variables() {
		this.size = 0;
		this.variables = new HashMap<String, Integer>();
		this.variables.put("R0", 0);
		this.variables.put("R1", 1);
		this.variables.put("R2", 2);
		this.variables.put("R3", 3);
		this.variables.put("R4", 4);
		this.variables.put("R5", 5);
		this.variables.put("R6", 6);
		this.variables.put("R7", 7);
		this.variables.put("R8", 8);
		this.variables.put("R9", 9);
		this.variables.put("R10", 10);
		this.variables.put("R11", 11);
		this.variables.put("R12", 12);
		this.variables.put("R13", 13);
		this.variables.put("R14", 14);
		this.variables.put("R15", 15);
		this.variables.put("SP", 0);
		this.variables.put("LCL", 1);
		this.variables.put("ARG", 2);
		this.variables.put("THIS", 3);
		this.variables.put("THAT", 4);
		this.variables.put("SCREEN", 16384);
		this.variables.put("KBD", 24576);
	}
	
	public void addVariable(String name) {
		if(!variables.containsKey(name)) {
			variables.put(name, 16 + size);
			size++;
		}
	}
	
	public int getVariableLocation(String name) {
		return variables.get(name);
	}
}
