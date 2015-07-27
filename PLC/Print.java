public class Print {
	/* Temporary variables */
	private static int actualTmp = 0;
	
	private static String newTmp() {
		return "t"+(actualTmp++);
	}

	public static String assignment(String ident, String exp) {
		PLC.out.println("   " + ident + " = " + exp + " ;");
		return ident;
	}

	public static String tern(String operation) {
		String tmp = newTmp();		
		PLC.out.println("   " + tmp + " = " + operation + " ;");
		return tmp;
	}

	public static void print(String exp) {
		PLC.out.println("   print " + exp + " ;");	
	}

	public static void goTo(String label) {
		PLC.out.println("   goto " + label + " ;");
	}

	public static void label(String tag) {
		PLC.out.println(tag + ":");
	}

}
