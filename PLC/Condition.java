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

	public static Condition condition(String e1, int type, String e2){
		Condition tags = new Condition();
		
		switch(type){
			/* If a == b goto trueTag, else, goto falseTag */
			case Condition.EQ:
				PLC.out.println("   if (" + e1 + " == " + e2 +") goto " + tags.trueTag + " ;");
				PLC.out.println("   goto " + tags.falseTag + " ;");
				break;
			/* If a == b goto falseTag, else, goto trueTag */
			case Condition.NEQ:
				PLC.out.println("   if (" + e1 + " == " + e2 +") goto " + tags.falseTag + " ;");
				PLC.out.println("   goto " + tags.trueTag + " ;");
				break;
			/* If a < b goto trueTag, else, goto falseTag */
			case Condition.LOW:
				PLC.out.println("   if (" + e1 + " < " + e2 +") goto " + tags.trueTag + " ;");
				PLC.out.println("   goto " + tags.falseTag + " ;");				
				break;
			/* If b < a goto falseTag, else, goto trueTag */
			case Condition.LOE:
				PLC.out.println("   if (" + e2 + " < " + e1 +") goto " + tags.falseTag + " ;");
				PLC.out.println("   goto " + tags.trueTag + " ;");	
				break;
			/* If b < a goto trueTag, else, goto falseTag */
			case Condition.GRE:
				PLC.out.println("   if (" + e2 + " < " + e1 +") goto " + tags.trueTag + " ;");
				PLC.out.println("   goto " + tags.falseTag + " ;");
				break;
			/* If (a < b) goto falseTag, else, goto trueTag */
			case Condition.GOE:
				PLC.out.println("   if (" + e1 + " < " + e2 +") goto " + tags.falseTag + " ;");
				PLC.out.println("   goto " + tags.trueTag + " ;");				
				break;
		}
		
		return tags;
	}
}
