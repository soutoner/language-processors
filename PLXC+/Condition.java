public class Condition {
	/* Enum for types of logic operators*/
	public final static int EQ = 50;
	public final static int NEQ = 52;
	public final static int LOW = 53;
	public final static int LOE = 54;
	public final static int GRE = 55;
	public final static int GOE = 56;

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
