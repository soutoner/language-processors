import java.util.*;

public class SymbolTable {
	// Size of the ocurrence array and indexes [scope, type, size (if array, -1 if not)]
	public final static int ARRAY_SIZE = 3;
	public final static int ARR_SCOPE = 0;
	public final static int ARR_TYPE = 1;
	public final static int ARR_SIZE = 2;
	
	// Map of identifier -> list of ocurrences
	public static Map<String, List<int []>> symTable;

	public SymbolTable() {
		symTable = new HashMap<String, List<int []>>();	
	}

	public void declare(String id, int actualScope, int type, String arrSize){
		int size = -1;
		try{
			size = Integer.valueOf(arrSize);
		} catch (NumberFormatException e) { // delcaration with identifier as index - error
			Printer.error("Identifier inside declaration of an array");
		} 
		List<int []> occurrences = symTable.get(id); // ocurrences of an identifier

		if(occurrences == null){ // we need to add it to the symbol table
			symTable.put(id, new ArrayList<int []>());
			int [] ocurrence = new int[ARRAY_SIZE];
			// SCOPE
			ocurrence[ARR_SCOPE] = actualScope;
			// TYPE
			ocurrence[ARR_TYPE] = type;
			// SIZE (-1 if not array)
			ocurrence[ARR_SIZE] = size;
			symTable.get(id).add(ocurrence);
		} else { // identifier is declared, fetch first occurrence
			int [] closerScope = occurrences.get(0);

			if(closerScope[ARR_SCOPE] == actualScope ){ // multiple declaration of variable, throw error
				Printer.error("variable ya declarada");
			} else if (closerScope[ARR_SCOPE] < actualScope){ // not declared in this scope so add occurrence at beginning
				int [] ocurrence = new int[ARRAY_SIZE];
				// SCOPE
				ocurrence[ARR_SCOPE] = actualScope;
				// TYPE
				ocurrence[ARR_TYPE] = type;
				// SIZE (-1 if not array)
				ocurrence[ARR_SIZE] = size;
				occurrences.add(0, ocurrence);
			} else {
				throw new RuntimeException("Purge is not working correctly");			
			}
		}
		printSymTable(); // DEBUG
	}


	public Variable lookUp(String id, int actualScope){
		List<int []> occurrences = symTable.get(id); // ocurrences of an identifier
		
		if(occurrences == null){ // variable non declared, throw error
			Printer.error("variable no declarada");
			return null;
		} else { // identifier is declared, fetch closer occurrence
			String ident = id;
			if(occurrences.size() > 1 ) // variable declared more than one, append suffix
				ident += ("_" + occurrences.get(0)[ARR_SCOPE]);
			return new Variable(ident, occurrences.get(0)[ARR_TYPE], occurrences.get(0)[ARR_SIZE]);
		}
	}

	public void purge(int actualScope){
		Iterator it = symTable.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, List<int []>> entry = (Map.Entry<String, List<int []>>) it.next();		

			List<int []> occurrences = entry.getValue();
			int [] closerScope = occurrences.get(0); // get occurrences of a identifier

			if(closerScope[ARR_SCOPE] == actualScope){ // occurrence that belong to the actual scope
				occurrences.remove(0); // so purge it
				if(occurrences.size() == 0){ // if there is no more occurrences, remove the entry
					it.remove();
				}
			}
		}
	}

	public static boolean hasTypeOf(String id, int type){
		List<int []> occurrences = symTable.get(id); // ocurrences of an identifier
		
		if(occurrences == null) // variable non declared, no type
			return false;
		else  // identifier is declared, fetch closer occurrence
			return occurrences.get(0)[ARR_TYPE] == type;
	}

	/*
	 *	DEBUGGING
	 */

	public static void printSymTable(){
		System.out.println("# --------------------");
		System.out.println("# ID\tSCOPE\tTYPE");
		System.out.println("# --------------------");

		Iterator it = symTable.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, List<int []>> entry = (Map.Entry<String, List<int []>>) it.next();		

			List<int []> occurrences = entry.getValue();
			
			for(int i = 0; i < occurrences.size(); i++){
				int [] scope = occurrences.get(i); // get occurrences of a identifier
				System.out.println("# " + entry.getKey() + "\t" + scope[ARR_SCOPE] + "\t" + printType(scope[ARR_TYPE], scope[ARR_SIZE]));
			}
		}
	}

	private static String printType(int type, int size){
		String ret = "";
		switch(type){
			case Variable.INT:
				ret += "INT";
				break;
			case Variable.FLOAT:
				ret += "FLOAT";
				break;
		}
		if(size > 0) ret += "[" + size + "]";
		return ret;
	}

}
