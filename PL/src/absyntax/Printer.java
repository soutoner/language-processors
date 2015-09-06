package absyntax;

import java.io.PrintStream;

public class Printer {

    private static PrintStream out = Generator.getInstance().out();

    public Printer(){}

    /**
     *  Instructions
     */

    // left = right;
    public static void assignation(Object left, Object right){
        out.println("   " + left + " = " + right + ";");
    }

    // goto label;
    public static void goTo(String label){
        out.println("   goto " + label + ";");
    }

    // if (e1 == e2) goto label;
    public static void ifEqual(Object e1, Object e2, String label){
        out.println("   if(" + e1 + " == " + e2 + ") goto " + label + ";");
    }

    // if (e1 < e2) goto label;
    public static void ifLower(Object e1, Object e2, String label){
        out.println("   if(" + e1 + " < " + e2 + ") goto " + label + ";");
    }

    // label:
    public static void label(String label){
        out.println(label + ":");
    }

    // print e;
    public static void print(Object e){
        out.println("   print " + e + ";");
    }

    // halt;
    public static void halt(){
        out.println("   halt;");
    }

}
