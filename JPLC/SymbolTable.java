import java.util.*;

public class SymbolTable {
	// As function cant be nested, we can mantain variables here
    private int localStackPtr = 0;	    // Local variable area pointer
    private int opStackPtr = 0;	        // Operation stack area pointer
    private int opStackSize = 0;		// Operation stack area size
    private int localStackSize = 0;		// Operation stack area size

	private Map<String, Integer> symTable;  // Map of identifier -> position in the stack
    private Printer out;                    // Variable for printing

	public SymbolTable() {
		symTable = new HashMap<String, Integer>();
        out = new Printer();
	}

    /**
     * Declares variable if it doesn't exist
     * @param id
     * @return Position of the variable in the stack
     */
	public void variable(String id){
		Integer occurrence = symTable.get(id); // ocurrences of an identifier

		if(occurrence == null) { // we need to add it to the symbol table
            symTable.put(id, localStackPtr);

            if(++localStackPtr > localStackSize) // If maximize the stack, we update the size
                localStackSize = localStackPtr;
        }
	}

    /**
     * Returns the position of the stack of the given variable
     * @param id
     * @return Position of the variable in the stack
     */
    public int lookUp(String id){
        return symTable.get(id).intValue();
    }

    /**
     * Deletes all the content of the SymTable
     */

	public void purge(){
		symTable.clear();
		localStackPtr = 0;
        opStackPtr = 0;
        localStackSize = 0;
        opStackSize = 0;
	}

    /**
     * GETTERS
     */

    public int opStackPtr() {
        return opStackPtr;
    }

    public int localStackPtr() {
        return localStackPtr;
    }

    public int opStackSize() {
        return opStackSize;
    }

    public int localStackSize() {
        return localStackSize;
    }

    /**
     * MUTATORS
     */

    public int incrOpStack(){
        if(++opStackPtr > opStackSize)
            opStackSize = opStackPtr;

        return opStackPtr;
    }

    public int decrOpStack(){
        return --opStackPtr;
    }

}
