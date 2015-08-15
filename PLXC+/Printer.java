import java.lang.Double;
import java.lang.RuntimeException;

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

	// Creates temporary variables (String tag)
	private String newIntTmp() {
		return "t"+(actualTmp++);
	}

	// Creates temporary variables (String tag)
	private String newFloatTmp() {
		return "$t"+(actualTmp++);
	}

	// Assignment
	public String assignment(String id, Object exp) {
		String casting = "";

		if(isReal(id) && isEntero(exp)) // Asssign int to foat variable, implicit casting needed
			casting += "(float) ";
		else if(isEntero(id) && isReal(exp))
			error("tried to assign float value to int");

		out.println("   " + id + " = " + casting + exp + ";");

		return id;
	}

	// Explicit casting
	public String casting(Object exp, int toType) {
		String tmp = "", casting = "";

		switch(toType){
			case SymbolTable.INT:
				tmp = newIntTmp();
				casting += "(int)";
				break;
			case SymbolTable.FLOAT:
				tmp = newFloatTmp();
				casting += "(float)";
				break;
		}

		out.println("   " + tmp + " = " + casting + " " + exp + ";");

		return tmp;
	}

	// Print
	public void print(Object exp) {
		out.println("   print " + exp + ";");
	}

	// Goto
	public void goTo(String label) {
		out.println("   goto " + label + ";");
	}

	// Label
	public void label(String tag) {
		out.println(tag + ":");
	}

    // Pre-Increment/Decrement
    public Object preIncrDecr(Object id, String op){
        if(op.equals("++")) out.println("   " + id + " = " + id + " + 1;");
        else out.println("   " + id + " = " + id + " - 1;");
        return id;
    }

    // Post-Increment/Decrement
    public String postIncrDecr(Object id, String op){
        String tmp = newIntTmp();
        out.println("   " + tmp + " = " + id + ";");

        if(op.equals("++"))
			out.println("   " + id + " = " + id + " + 1;");
		else
			out.println("   " + id + " = " + id + " - 1;");

        return tmp;
    }

	// Mod operation
	public String mod(Object e1, Object e2) {
		String tmp1 = newIntTmp();
		out.println("   " + tmp1 + " = " + e1 + " / " + e2 + ";");
		String tmp2 = newIntTmp();
		out.println("   " + tmp2 + " = " + tmp1 + " * " + e2 + ";");
		String tmp3 = newIntTmp();
		out.println("   " + tmp3 + " = " + e1 + " - " + tmp2 + ";");

		return tmp3;
	}

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
			tmp = newFloatTmp();
			op += "r";
			if(isEntero(e2)){ // e1 FLOAT, e2 INTEGER (casting needed)
				op2 = assignment(newFloatTmp(), e2);
			} else if (isEntero(e1)) { // e1 INTEGER (casting needed), e2 FLOAT
				op1 = assignment(newFloatTmp(), e1);
			}
		} else {
			tmp = newIntTmp();
		}

		out.println("   " + tmp + " = " + op1 + " " + op + " " + op2 + ";");
		return tmp;
	}

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

	// Raw message
	public void raw(String sent) {
		out.println("   " + sent);
	}

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

	// BOOLEANS

	private boolean isReal(Object o){
		return symTable.typeOf(o) == SymbolTable.FLOAT;
	}

	private boolean isEntero(Object o){
		return symTable.typeOf(o) == SymbolTable.INT;
	}

}
