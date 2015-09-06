import java.io.PrintStream;

public class Generator {

    private static Generator instance;
    private static PrintStream out = PLXC.out;                  // Output to print (can be System.out or a file)
    private static SymbolTable symTable = new SymbolTable();    // Symbol Table
    private static int actualLabel = 0;                         // Actual label
    private static int actualTmp = 0;                           // Actual tmp

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

    public SymbolTable symbolTable(){
        return symTable;
    }

    public String newLabel(){ return "L" + actualLabel++; }

    public String newTmp(){ return "$t" + actualTmp++; }
}
