public class Printer {
	/* Enum for types of arithmetic operators*/
	public final static int ADD = 10;
	public final static int SUB = 11;
	public final static int MUL = 12;
	public final static int DIV = 13;
	public final static int USUB = 14;
	/* Variables */
	private static java.io.PrintStream out = JPLC.out;
	
	public static void method(String name){
		out.println(".method public static " + name + "(I)I");
	}

	public static void endMethod(){
		out.println("    .limit stack " + SymbolTable.opStack);
		out.println("    .limit locals " + SymbolTable.localStack);
		out.println(".end method");
	}

	public static void returnVal(String val){
		push(val);
		out.println("    ireturn");
		out.println("    nop");
	}

	public static void push(String val){
		if(val == null) return;	
		SymbolTable.opStack++;
		out.println("    sipush " + val);
	}
	
	public static void store(String val){
		if(val == null) return;	
		out.println("    istore " + val);
	}

	public static void dup() {
		out.println("    dup");
		SymbolTable.opStack++;
	}

	public static void assignment(String identStackPos, String exp) {
		dup();
		store(identStackPos);
	}
		
	public static void load(String identStackPos) {
		out.println("    iload " + identStackPos);
	}

	public static void callFunction(String ident) {
		out.println("    invokestatic JPL/" + ident + "(I)I");
	}

	public static void operation(String e1, int op, String e2){
		switch(op){
			case Printer.ADD:
				out.println("    iadd");			
				break;
			case Printer.SUB:
				out.println("    isub");			
				break;
			case Printer.MUL:
				out.println("    imul");			
				break;
			case Printer.DIV:
				out.println("    idiv");			
				break;
			case Printer.USUB:
				push("-1");
				out.println("    imul");			
				break;
		}
	}

	public static void goTo(String label) {
		out.println("    goto " + label);
	}

	public static void label(String tag) {
		out.println(tag + ":");
	}

	public static Condition condition(String e1, int type, String e2){
		Condition tags = new Condition();
		
		switch(type){
			/* If a < b goto trueTag, else, goto falseTag */
			case Condition.LOW:
				out.println("    isub");
				out.println("    iflt " + tags.trueTag);
				goTo(tags.falseTag);		
				break;
			/* If b < a goto trueTag, else, goto falseTag */
			case Condition.GRE:
				out.println("    isub");
				out.println("    ifgt " + tags.trueTag);
				goTo(tags.falseTag);
				break;
		}
		
		return tags;
	}

	public static void raw(String s) {
		out.println(s);	
	}


}
