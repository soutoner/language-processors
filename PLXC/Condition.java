public class Condition {
	/* Enum for types of logic operators*/
	public final static int EQ = 1;
	public final static int NEQ = 2;
	public final static int LOW = 3;
	public final static int LOE = 4;
	public final static int GRE = 5;
	public final static int GOE = 6;

	/* Both tag, true and false */
	public String trueTag, falseTag;

	public Condition() {
		this.trueTag = Yylex.newTag();
		this.falseTag = Yylex.newTag();
	}

	public Condition(String trueT, String falseT) {
		this.trueTag = trueT;
		this.falseTag = falseT;
	}

	public Condition not(){
		return new Condition(falseTag, trueTag);
	}
}
