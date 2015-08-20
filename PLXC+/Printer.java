import java.lang.*;
import java.util.*;

public class Printer {

	// Enum for arithmetic operations
	public final static int ADD = 30;
	public final static int SUB = 31;
	public final static int MUL = 32;
	public final static int DIV = 33;

	private static int actualTmp = 0; 	// Temporary variables counter
	private java.io.PrintStream out;	// Print stream
	private SymbolTable symTable;		// Actual SymbolTable

	public Printer(){
		this.out = PLXC.out;
	}

	public Printer(SymbolTable symTable){
		this.out = PLXC.out;
		this.symTable = symTable;
	}

	// Creates temporary variables identifier (INT - tXX or FLOAT - $tXX)
	private String newTmp(int type) {
        return (type == Occurrence.INT) ? "t"+(actualTmp++) : "$t"+(actualTmp++);
	}

    /**
     * ASSIGNMENTS
     */

	// Assignment ARR[i] = EXP
	public String assignment(String id, Object idx, Object exp) {
		// Assign float to int, error
		if(isEntero(id) && isReal(exp))
			error("tried to assign float value to int");

		// Check range
		checkRange(id, idx);

        // If we need to cast, we need one step more with tmp variables
        Object expression = exp;
        if((isReal(id) && isEntero(exp)))
            expression = rawAssignment(newTmp(symTable.typeOf(id)), null, "(float)", exp, null);

		return rawAssignment(id, idx, expression, null);
	}

    // Assignments ID = EXP, ARR = ARR
    public String assignment(String id, Object exp) {
        if(isArray(id) && isArray(exp))
        	return arrayToArrayAssignment(id, exp);

        // Assign float to int, error
        if(isEntero(id) && isReal(exp))
            error("tried to assign float value to int");

        return rawAssignment(id, null, (isReal(id) && isEntero(exp)) ? "(float)" : null, exp, null);
    }

    // Explicit casting
    public String casting(Object exp, int toType) {
        return rawAssignment(newTmp(toType), null, (toType == Occurrence.INT) ? "(int)" : "(float)", exp, null);
    }

    /**
     * RAW ASSIGNMENTS
     */

    // asigned = (casting) exp; asigned[idx] = (casting) exp; asigned = (casting) arr[i];
    private String rawAssignment(String asigned, Object idx1 , String cast, Object expOrArray, Object idx2) {
        String index1 = (idx1 != null) ? "[" + idx1.toString() + "]" : "";
        String casting = (cast != null) ? cast + " " : "";
        String index2 = (idx2 != null) ? "[" + idx2.toString() + "]" : "";

        out.println("   " + asigned + index1 + " = " + casting + expOrArray + index2 + ";");

        return asigned;
    }

    // asigned = exp; asigned[idx] = exp; asigned = arr[i]; (without casting)
    private String rawAssignment(String asigned, Object idx1 , Object expOrArray, Object idx2) {
        String index1 = (idx1 != null) ? "[" + idx1.toString() + "]" : "";
        String index2 = (idx2 != null) ? "[" + idx2.toString() + "]" : "";

        out.println("   " + asigned + index1 + " = " + expOrArray + index2 + ";");

        return asigned;
    }

    /**
     * ARRAYS
     */

    // Explicit array initialization
    public String arrayInit(String id, List<Object> list) {
        if(symTable.sizeOf(id) < list.size())
            error("las matrices no son compatibles");

        String arrTmp = newTmp(symTable.typeOf(id));
        String tmp = newTmp(symTable.typeOf(id));

        int idx = 0;

        for(Object exp: list) {
            if(symTable.typeOf(id) != symTable.typeOf(exp))
                error("error de tipos");
            rawAssignment(arrTmp, idx++, exp, null);
        }

        for(int i = 0; i < list.size(); i++){
            rawAssignment(tmp, null, arrTmp, i);
            rawAssignment(id, i, tmp, null);
        }

        return rawAssignment(id, null, arrTmp, null);
    }

	// Load array to tmp variable
	public String loadArray(String id, Object idx) {
        String tmp = newTmp(symTable.typeOf(id));

        // Check Range
		checkRange(id, idx);

		return rawAssignment(tmp, null, id, idx );
	}

    // assign one array to the other
    public String arrayToArrayAssignment(String arr1, Object arr2){
        if(symTable.sizeOf(arr1) < symTable.sizeOf(arr2))
            error("las matrices no son compatibles");

        if(symTable.typeOf(arr1) != symTable.typeOf(arr2))
            error("error de tipos");

        String tmp = newTmp(symTable.typeOf(arr1));

        for(int i = 0; i < symTable.sizeOf(arr1); i++){
            rawAssignment(tmp, null, arr2, i);
            rawAssignment(arr1, i, tmp, null);
        }

        return rawAssignment(arr1, null, arr2, null);
    }

	// Check if the idx is in range of the array
	public void checkRange(String id, Object idx) {
		if(isTmp(id)) // tmp variables are not checked
			return;
		if(!isArray(id))
			throw new RuntimeException("checking range on non array");

		int size = symTable.sizeOf(id);

		Condition cond = new Condition();
		out.println("# Comprobacion de rango");
		out.println("   if (" + idx + " < 0) goto " + cond.trueTag + ";");
		out.println("   if (" + size + " < " + idx + ") goto " + cond.trueTag + ";");
		out.println("   if (" + size + " == " + idx + ") goto " + cond.trueTag + ";");
		goTo(cond.falseTag);
		label(cond.trueTag);
		out.println("   error;");
		out.println("   halt;");
		label(cond.falseTag);
	}

    /**
     * ARITHMETIC OPERATIONS
     */

    // Tern (arithmetic operations)
    public String tern(Object e1, int operation, Object e2) {
        String tmp = "", op = "";
        String op1 = e1.toString(), op2 = e2.toString();

        switch (operation){
            case Printer.ADD:
                op += "+";
                break;
            case Printer.SUB:
                op += "-";
                break;
            case Printer.MUL:
                op += "*";
                break;
            case Printer.DIV:
                op += "/";
                break;
        }

        if(isReal(e1) || isReal(e2)) { // If any of the operand is Real
            tmp = newTmp(Occurrence.FLOAT);
            op += "r";
            if(isEntero(e2)){ // e1 FLOAT, e2 INTEGER (casting needed)
                op2 = assignment(newTmp(Occurrence.FLOAT), e2);
            } else if (isEntero(e1)) { // e1 INTEGER (casting needed), e2 FLOAT
                op1 = assignment(newTmp(Occurrence.FLOAT), e1);
            }
        } else {
            tmp = newTmp(Occurrence.INT);
        }

        return rawTern(tmp, op1, op, op2);
    }

    private String rawTern(String asigned, Object op1, String operation, Object op2){
        out.println("   " + asigned + " = " + op1 + " " + operation + " " + op2 + ";");

        return asigned;
    }

	// Mod operation
	public String mod(Object e1, Object e2) {
		String tmp1 = newTmp(Occurrence.INT);
		rawTern(tmp1, e1, "/", e2);
		String tmp2 = newTmp(Occurrence.INT);
		rawTern(tmp2, tmp1, "*", e2);
		String tmp3 = newTmp(Occurrence.INT);

		return rawTern(tmp3, e1, "-", tmp2);
	}

    // Pre-Increment/Decrement
    public Object preIncrDecr(Object id, String op){
        return rawTern((String) id, id, op.substring(0, op.length()-1) , new Integer(1));
    }

    // Post-Increment/Decrement
    public String postIncrDecr(Object id, String op){
        String tmp = newTmp(Occurrence.INT);
        rawAssignment(tmp, null, id, null);
        rawTern((String) id, id, op.substring(0, op.length() - 1), new Integer(1));

        return tmp;
    }

    /**
     * CONDITIONS
     */

	// Conditions
	public Condition condition(Object e1, int type, Object e2){
		Condition tags = new Condition();

		switch(type){
			// If a == b goto trueTag, else, goto falseTag
			case Condition.EQ:
				out.println("   if (" + e1 + " == " + e2 +") goto " + tags.trueTag + ";");
				out.println("   goto " + tags.falseTag + ";");
				break;
			// If a == b goto falseTag, else, goto trueTag
			case Condition.NEQ:
				out.println("   if (" + e1 + " == " + e2 +") goto " + tags.falseTag + ";");
				out.println("   goto " + tags.trueTag + ";");
				break;
			// If a < b goto trueTag, else, goto falseTag
			case Condition.LOW:
				out.println("   if (" + e1 + " < " + e2 +") goto " + tags.trueTag + ";");
				out.println("   goto " + tags.falseTag + ";");
				break;
			// If b < a goto falseTag, else, goto trueTag
			case Condition.LOE:
				out.println("   if (" + e2 + " < " + e1 +") goto " + tags.falseTag + ";");
				out.println("   goto " + tags.trueTag + ";");
				break;
			// If b < a goto trueTag, else, goto falseTag
			case Condition.GRE:
				out.println("   if (" + e2 + " < " + e1 +") goto " + tags.trueTag + ";");
				out.println("   goto " + tags.falseTag + ";");
				break;
			// If (a < b) goto falseTag, else, goto trueTag
			case Condition.GOE:
				out.println("   if (" + e1 + " < " + e2 +") goto " + tags.falseTag + ";");
				out.println("   goto " + tags.trueTag + ";");
				break;
		}

		return tags;
	}

    // Goto
    public void goTo(String label) {
        out.println("   goto " + label + ";");
    }

    // Label
    public void label(String tag) {
        out.println(tag + ":");
    }

    /**
     * SIMPLE INSTRUCTIONS
     */

    // Print
    public void print(Object exp) {
        out.println("   print " + exp + ";");
    }

    /**
     * FOR IN
     */

    public Condition forIn(String id, String forTag, Object arr){
        String array = "";
        Integer size = 0;

        if(arr instanceof String){ // array variable
            array = symTable.lookUp((String) arr);
            size = symTable.sizeOf(arr);
            if(size <= 0)
                error("tipo incorrecto");
        } else { // explicit array
            array = newTmp(symTable.typeOf(id));
            size = ((List<Object>) arr).size();

            int i = 0;
            for(Object exp: (List<Object>) arr)
                rawAssignment(array, i++, exp, null);
        }

        if(symTable.typeOf(id) != symTable.typeOf(array))
            error("tipos incompatibles");

        String index = newTmp(symTable.typeOf(id));

        rawAssignment(index, null, new Integer(-1), null);
        label(forTag);
        rawTern(index, index, "+" , new Integer(1));
        Condition cond = condition(index, Condition.LOW, size);
        label(cond.trueTag);
        rawAssignment(id, null, array, index);

        return cond;
    }

    /**
     * ERRORS
     */

	// Error without message
	public void error() {
		out.println("   error;");
        out.println("   halt;");
        throw new RuntimeException(); // RuntimeException is caught and done_parsing() is called (PLXC.java)
	}

	// Error with message
	public void error(String err) {
		out.println("# ERROR: " + err);
        out.println("   error;");
		out.println("   halt;");
        throw new RuntimeException(err); // RuntimeException is caught and done_parsing() is called (PLXC.java)
	}

    /**
     * HELPERS
     */

    // Raw message
    public void raw(String sent) {
        out.println("   " + sent);
    }

	/**
	 * BOOLEANS
	 */

	private boolean isReal(Object o){
		return symTable.isReal(o);
	}

	private boolean isEntero(Object o){
		return symTable.isEntero(o);
	}

	private boolean isArray(Object o){
		return symTable.isArray(o);
	}

	private boolean isTmp(Object o){
		return symTable.isTmp(o);
	}
}
