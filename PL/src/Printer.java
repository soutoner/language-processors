import java.io.PrintStream;

public class Printer {

	private PrintStream out = Generator.getInstance().out();	// Print stream

	public Printer(){}

	// Creates temporary variables (String tag)
	private String newTmp(){
		return Generator.getInstance().newTmp();
	}

	// Tern (arithmetic operations)
	public String tern(String operation) {
		return assignment(newTmp(), operation);
	}

	/**
	 * CONDITIONS
	 */

	public Condition condition(Object e1, int type, Object e2){
		Condition cond = new Condition();
		String trueLabel = cond.getTrueLabel(), falseLabel = cond.getFalseLabel();

		switch(type){
			// If a == b goto trueLabel, else, goto falseLabel
			case Condition.EQ:
				ifEqual(e1, e2, trueLabel);
				goTo(falseLabel);
				break;
			// If a == b goto falseLabel, else, goto trueLabel
			case Condition.NEQ:
				ifEqual(e1, e2, falseLabel);
				goTo(trueLabel);
				break;
			// If a < b goto trueLabel, else, goto falseLabel
			case Condition.LOW:
				ifLower(e1, e2, trueLabel);
				goTo(falseLabel);
				break;
			// If b < a goto falseLabel, else, goto trueLabel
			case Condition.LOE:
				ifLower(e2, e1, falseLabel);
				goTo(trueLabel);
				break;
			// If b < a goto trueLabel, else, goto falseLabel
			case Condition.GRE:
				ifLower(e2, e1, trueLabel);
				goTo(falseLabel);
				break;
			// If (a < b) goto falseLabel, else, goto trueLabel
			case Condition.GOE:
				ifLower(e1, e2, falseLabel);
				goTo(trueLabel);
				break;
		}

		return cond;
	}

	/**
	 * AVAILABLE INSTRUCTIONS
	 */

	// left = right;
	public String assignment(String left, Object right){
		out.println("   " + left + " = " + right + ";");

		return left;
	}

	// goto label;
	public void goTo(String label){
		out.println("   goto " + label + ";");
	}

	// if (e1 == e2) goto label;
	public void ifEqual(Object e1, Object e2, String label){
		out.println("   if(" + e1 + " == " + e2 + ") goto " + label + ";");
	}

	// if (e1 < e2) goto label;
	public void ifLower(Object e1, Object e2, String label){
		out.println("   if(" + e1 + " < " + e2 + ") goto " + label + ";");
	}

	// label:
	public void label(String label){
		out.println(label + ":");
	}

	// print e;
	public void print(Object e){
		out.println("   print " + e + ";");
	}

	// halt;
	public void halt(){
		out.println("   halt;");
	}
}
