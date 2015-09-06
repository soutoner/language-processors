import java.util.*;

public class SymbolTable {

    private List<String> variables;     // Local variable
    private Stack<Integer> stack;       // Operation stack
    private int maxStackSize;           // Operation stack max size

    private Printer printer;            // Variable for printing

	public SymbolTable() {
        this.variables = new ArrayList<String>();
		this.stack = new Stack<Integer>();
        this.maxStackSize = 0;
        this.printer = new Printer();
	}

    /**
     * Declares variable if it doesn't exist
     * @param id
     */
	public int declare(String id){
        variables.add(id);

        return variables.indexOf(id);
    }

    /**
     * Returns the position of the stack of the given variable
     * @param id
     * @return Position of the variable in the list
     */
    public int lookUp(String id){
        return variables.indexOf(id);
    }

    /**
     * MUTATORS
     */

    /**
     * Push n onto the stack
     * @param n Integer
     */
    public void push(){
        stack.push(0);

        if(stack.size() > maxStackSize) // If maximize the stack, we update the size
            maxStackSize++;
    }

    /**
     * Pop top element from the stack
     * @return Integer
     */
    public Integer pop(){
        return stack.pop();
    }

    /**
     * Deletes all the content of the Symbol Table
     */
    public void purge(){
        variables.clear();
        stack = new Stack<Integer>();
        maxStackSize = 0;
    }

    /**
     * GETTERS
     */

    public int getStackSize() {
        return maxStackSize;
    }

    public int getLocalsSize() {
        return variables.size();
    }
}
