public class Printer {
	/* Temporary variables */
	private static int actualTmp = 0;
	private static java.io.PrintStream out = PLXC.out;
	
	private static String newTmp() {
		return "t"+(actualTmp++);
	}

	public static String assignment(String ident, String exp) {
		out.println("   " + ident + " = " + exp + ";");
		return ident;
	}

	public static String tern(String operation) {
		String tmp = newTmp();		
		out.println("   " + tmp + " = " + operation + ";");
		return tmp;
	}

	public static String mod(String e1, String e2) {
		String tmp1 = newTmp();
		out.println("   " + tmp1 + " = " + e1 + " / " + e2 + ";");
		String tmp2 = newTmp();
		out.println("   " + tmp2 + " = " + tmp1 + " * " + e2 + ";");
		String tmp3 = newTmp();
		out.println("   " + tmp3 + " = " + e1 + " - " + tmp2 + ";");
		return tmp3;
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
		out.println("   # " + err);
		out.println("   halt;");
	}

	public static void error() {
		out.println("   error;");
		out.println("   halt;");
	}

	public static String postIncr(String ident, String op){
		if(!ident.matches("[_a-zA-Z$][_a-zA-Z0-9$]*") || ident.matches("t[0-9]+")){ // if it isnt and identifier throw error
			error();
		}
		String tmp = newTmp();
		out.println("   " + tmp + " = " + ident + ";");
		if(op.equals("++")) out.println("   " + ident + " = " + ident + " + 1;");
		else out.println("   " + ident + " = " + ident + " - 1;");
		return tmp;
	}

	public static String preIncr(String ident, String op){
		if(!ident.matches("[_a-zA-Z$][_a-zA-Z0-9$]*") || ident.matches("t[0-9]+")){ // if it isnt and identifier throw error
			error();
		}
		if(op.equals("++")) out.println("   " + ident + " = " + ident + " + 1;");
		else out.println("   " + ident + " = " + ident + " - 1;");
		return ident;
	}

	public static Condition condition(String e1, int type, String e2){
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
