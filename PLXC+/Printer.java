public class Printer {
	// ENUM for ops
	public final static int ADD = 30;
	public final static int SUB = 31;
	public final static int MUL = 32;
	public final static int DIV = 33;
	// Temporary variables 
	private static int actualTmp = 0;
	private static java.io.PrintStream out = PLXC.out;
	
	private static String newTmp() {
		return "$t"+(actualTmp++);
	}

	public static Variable assignment(Variable ident, String idx, Variable exp) {
		String cast = "";
		String left = ident.getValue();

		// Set type and casting if necessary
		if(ident.isFloat()){
			if(exp.isInt()) // add casting
				cast = "(float) ";
		} else {
			if(exp.isFloat()) // ERROR: unsupported casting
				error("tried to assign float value to int");
		}
		if(!idx.equals("-1")) left += "[" + idx + "]"; // array index provided
		out.println("   " + left + " = " + cast + exp + ";");
		return ident;
	}

	public static Variable arrayToTmp(Variable ident, String idx) {
		String tmp = newTmp();
		out.println("   " + tmp + " = " + ident + "[" + idx + "];");
		return new Variable(tmp, ident.getType(), ident.getSize());
	}

	public static void rangeCheck(Variable ident, Variable idx){
		Condition tags = new Condition();
		out.println("# Comprobacion de rango");
		out.println("   if (" + idx + " < 0) goto " + tags.trueTag + ";");
		out.println("   if (" + ident.getSize() + " < " + idx + ") goto " + tags.trueTag + ";");
		out.println("   if (" + ident.getSize() + " == " + idx + ") goto " + tags.trueTag + ";");
		goTo(tags.falseTag);
		label(tags.trueTag);
		error();
		label(tags.falseTag);
	}

	public static Variable arrInit(Variable tmp, int idx, Variable exp){
		if (tmp == null) tmp = new Variable(newTmp(), exp.getType(), 0);
		assignment(tmp, String.valueOf(idx), exp);
		return new Variable(tmp.getValue(), tmp.getType(), tmp.getSize() + 1);
	}

	public static void arrAssign(String ident, Variable tmpArr){
		String tmp = newTmp();
		for(int i=0; i < tmpArr.getSize(); i++){
			out.println("   " + tmp + " = " + tmpArr + "[" + i + "];");	
			out.println("   " + ident + "[" + i + "] = " + tmp + ";");	
		}
		out.println("   " + ident + " = " + tmpArr + ";");
	}

	public static Variable tern(Variable e1, int op, Variable e2) {
		String tmp = newTmp();
		String operation = "";
		String op1  = e1.getValue(), op2 = e2.getValue();
		int type = Variable.INT;
		switch(op){
			case ADD:
				operation = "+";
				break;
			case SUB:
				operation = "-";
				break;
			case MUL:
				operation = "*";
				break;
			case DIV:
				operation = "/";
				break;		
				
		}
		if(e1.isFloat() || e2.isFloat()){
				operation += "r";
				type = Variable.FLOAT;				
			if(e1.isFloat() && !e2.isFloat()){ // e1 float, casting to e2
				op2 = newTmp();
				out.println("   " + op2 + " = (float) " + e2 + ";");
			} else if (!e1.isFloat() && e2.isFloat()){ // casting to e1, e2 float
				op1 = newTmp();
				out.println("   " + op1 + " = (float) " + e1 + ";");
			} 
		}		
		out.println("   " + tmp + " = " + op1 + " " + operation + " " + op2 + ";");
		return new Variable(tmp, type);
	}

	public static Variable mod(Variable e1, Variable e2) {
		String tmp1 = newTmp();
		out.println("   " + tmp1 + " = " + e1 + " / " + e2 + ";");
		String tmp2 = newTmp();
		out.println("   " + tmp2 + " = " + tmp1 + " * " + e2 + ";");
		String tmp3 = newTmp();
		out.println("   " + tmp3 + " = " + e1 + " - " + tmp2 + ";");
		return new Variable(tmp3, Variable.INT);
	}

	public static Variable casting(Variable exp, int toType) {
		String tmp = newTmp();
		switch(toType){
			case Variable.INT:
				out.println("   " + tmp + " = (int) " + exp + ";");
				break;
			case Variable.FLOAT:
				out.println("   " + tmp + " = (float) " + exp + ";");
				break;		
		}
		return new Variable(tmp, toType);
	}

	public static void print(String exp) {
		out.println("   print " + exp + ";");	
	}

	public static void goTo(String label) {
		out.println("   goto " + label + ";");
	}

	public static void label(String tag) {
		out.println(tag + ":");
	}

	public static void error(String err) {
		out.println("   error;");
		out.println("# ERROR: " + err);
		out.println("   halt;");
	}

	public static void error() {
		out.println("   error;");
		out.println("   halt;");
	}

	public static Variable postIncr(Variable ident, String op){
		if(!ident.getValue().matches("[_a-zA-Z$][_a-zA-Z0-9$]*") || ident.getValue().matches("t[0-9]+")){ // if it isnt an identifier throw error
			error("Post increment/decrement to an invalid expression");
		}
		String tmp = newTmp();
		out.println("   " + tmp + " = " + ident + ";");
		if(op.equals("++")) out.println("   " + ident + " = " + ident + " + 1;");
		else out.println("   " + ident + " = " + ident + " - 1;");
		return new Variable(tmp, ident.getType());
	}

	public static Variable preIncr(Variable ident, String op){
		if(!ident.getValue().matches("[_a-zA-Z$][_a-zA-Z0-9$]*") || ident.getValue().matches("t[0-9]+")){ // if it isnt an identifier throw error
			error("Post increment/decrement to an invalid expression");
		}
		if(op.equals("++")) out.println("   " + ident + " = " + ident + " + 1;");
		else out.println("   " + ident + " = " + ident + " - 1;");
		return ident;
	}

	public static Condition condition(Variable e1, int type, Variable e2){
		Condition tags = new Condition();
		
		switch(type){
			/* If a == b goto trueTag, else, goto falseTag */
			case Condition.EQ:
				out.println("   if (" + e1 + " == " + e2 +") goto " + tags.trueTag + ";");
				out.println("   goto " + tags.falseTag + ";");
				break;
			/* If a == b goto falseTag, else, goto trueTag */
			case Condition.NEQ:
				out.println("   if (" + e1 + " == " + e2 +") goto " + tags.falseTag + ";");
				out.println("   goto " + tags.trueTag + ";");
				break;
			/* If a < b goto trueTag, else, goto falseTag */
			case Condition.LOW:
				out.println("   if (" + e1 + " < " + e2 +") goto " + tags.trueTag + ";");
				out.println("   goto " + tags.falseTag + ";");				
				break;
			/* If b < a goto falseTag, else, goto trueTag */
			case Condition.LOE:
				out.println("   if (" + e2 + " < " + e1 +") goto " + tags.falseTag + ";");
				out.println("   goto " + tags.trueTag + ";");	
				break;
			/* If b < a goto trueTag, else, goto falseTag */
			case Condition.GRE:
				out.println("   if (" + e2 + " < " + e1 +") goto " + tags.trueTag + ";");
				out.println("   goto " + tags.falseTag + ";");
				break;
			/* If (a < b) goto falseTag, else, goto trueTag */
			case Condition.GOE:
				out.println("   if (" + e1 + " < " + e2 +") goto " + tags.falseTag + ";");
				out.println("   goto " + tags.trueTag + ";");				
				break;
		}
		
		return tags;
	}

	public static void raw(String s) {
		out.println(s);	
	}


}
