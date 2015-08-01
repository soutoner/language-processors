import java.util.*;

public class SymbolTable {
	// As function cant be nested, we can mantain ths vvariables here
	public static int opStack = 0;		// Operation stack area size
	public static int localStack = 0;	// Local variable area size

	// Map of identifier -> position in the stack
	private Map<String, Integer> symTable;

	public SymbolTable() {
		symTable = new HashMap<String, Integer>();	
	}

	public void declareMainArgument(String id){
		symTable.put(id, localStack++);
	}

	public void declare(String id, String val){
		Integer occurrence = symTable.get(id); // ocurrences of an identifier

		if(occurrence == null){ // we need to add it to the symbol table
			String pos = String.valueOf(localStack);
			symTable.put(id, localStack++);
			Printer.push(val);
			Printer.store(pos);
		}
	}

	public String lookUp(String id){
		return String.valueOf(symTable.get(id));
	}

	public void purge(){
		symTable = new HashMap<String, Integer>();
		localStack = 0;
	}

}
