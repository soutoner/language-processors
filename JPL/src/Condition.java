public class Condition {

	// Enum for types of logic operators
	public final static int EQ = 1;
	public final static int NEQ = 2;
	public final static int LOW = 3;
	public final static int LOE = 4;
	public final static int GRE = 5;
	public final static int GOE = 6;

	// Both labels, true and false
	public String trueLabel, falseLabel;

	public Condition() {
		this.trueLabel = Generator.getInstance().newLabel();
		this.falseLabel = Generator.getInstance().newLabel();
	}

	public Condition(String trueLabel, String falseLabel) {
		this.trueLabel = trueLabel;
		this.falseLabel = falseLabel;
	}

	// Interchange true and false Label (logical not)
	public Condition not(){
		return new Condition(this.falseLabel, this.trueLabel);
	}

	public String getTrueLabel() {
		return trueLabel;
	}

	public String getFalseLabel() {
		return falseLabel;
	}
}
