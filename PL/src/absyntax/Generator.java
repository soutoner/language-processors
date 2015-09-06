package absyntax;

import java.io.PrintStream;

public class Generator {

    private static Generator instance;
    private PrintStream out = PLC.out;    // Output to print (can be System.out or a file)
    private int actualTag;                // Actual label
    private int actualTmp;                // Actual tmp

    public Generator(){ this.actualTmp = 0; this.actualTag = 0; }

    public static synchronized Generator getInstance()
    {
        if (instance == null)
            instance = new Generator();

        return instance;
    }

    public PrintStream out(){
        return out;
    }

    public String newTag(){ return "L" + actualTag++; }

    public String newTmp(){ return "t" + actualTmp++; }
}