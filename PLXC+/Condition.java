public class Condition {
	// Enum for types of logic operators
	public final static int EQ = 11;
	public final static int NEQ = 12;
	public final static int LOW = 13;
	public final static int LOE = 14;
	public final static int GRE = 15;
	public final static int GOE = 16;

	// Both tag, true and false
	public String trueTag, falseTag;

	public Condition() {
		this.trueTag = Yylex.newTag();
		this.falseTag = Yylex.newTag();
	}
}
