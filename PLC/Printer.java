public class Printer {

	private static int actualTmp = 0; 	// Temporary variables
	private java.io.PrintStream out;	// Print stream

	public Printer(){
		this.out = PLC.out;
	}

	// Creates temporary variables (String tag)
	private String newTmp() {
		return "t"+(actualTmp++);
	}

	// Assignment
	public String assignment(String ident, Object exp) {
		out.println("   " + ident + " = " + exp + " ;");
		return ident;
	}

	// Tern (arithmetic operations)
	public String tern(String operation) {
		String tmp = newTmp();		
		out.println("   " + tmp + " = " + operation + " ;");
		return tmp;
	}

	// Print
	public void print(Object exp) {
		out.println("   print " + exp + " ;");
	}

	// Goto
	public void goTo(String label) {
		out.println("   goto " + label + " ;");
	}

	// Label
	public void label(String tag) {
		out.println(tag + ":");
	}

	// Conditions
	public Condition condition(Object e1, int type, Object e2){
		Condition tags = new Condition();

		switch(type){
			// If a == b goto trueTag, else, goto falseTag
			case Condition.EQ:
				out.println("   if (" + e1 + " == " + e2 +") goto " + tags.trueTag + " ;");
				out.println("   goto " + tags.falseTag + " ;");
				break;
			// If a == b goto falseTag, else, goto trueTag
			case Condition.NEQ:
				out.println("   if (" + e1 + " == " + e2 +") goto " + tags.falseTag + " ;");
				out.println("   goto " + tags.trueTag + " ;");
				break;
			// If a < b goto trueTag, else, goto falseTag
			case Condition.LOW:
				out.println("   if (" + e1 + " < " + e2 +") goto " + tags.trueTag + " ;");
				out.println("   goto " + tags.falseTag + " ;");
				break;
			// If b < a goto falseTag, else, goto trueTag
			case Condition.LOE:
				out.println("   if (" + e2 + " < " + e1 +") goto " + tags.falseTag + " ;");
				out.println("   goto " + tags.trueTag + " ;");
				break;
			// If b < a goto trueTag, else, goto falseTag
			case Condition.GRE:
				out.println("   if (" + e2 + " < " + e1 +") goto " + tags.trueTag + " ;");
				out.println("   goto " + tags.falseTag + " ;");
				break;
			// If (a < b) goto falseTag, else, goto trueTag
			case Condition.GOE:
				out.println("   if (" + e1 + " < " + e2 +") goto " + tags.falseTag + " ;");
				out.println("   goto " + tags.trueTag + " ;");
				break;
		}

		return tags;
	}

}
