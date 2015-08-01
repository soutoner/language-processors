import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class JPLC {
	public static PrintStream out;
  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      parser p = new parser(new Yylex(new FileReader(argv[0])));
	  out = System.out;
      if (argv.length>1 && argv[1]!=null) {
    	 out = new PrintStream(new FileOutputStream(argv[1]));
      }
      Object result = p.parse().value;      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

