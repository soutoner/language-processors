import java.io.PrintStream;

public class Generator {

    private static Generator instance;
    private static PrintStream out = PLXC.out; // Output to print (can be System.out or a file)
    private int actualLabel = 0;              // Actual label
    private int actualTmp = 0;                // Actual tmp

    public Generator(){ }

    public static synchronized Generator getInstance()
    {
        if (instance == null)
            instance = new Generator();

        return instance;
    }

    public PrintStream out(){
        return out;
    }

    public String newLabel(){ return "L" + actualLabel++; }

    public String newTmp(){ return "$t" + actualTmp++; }
}
