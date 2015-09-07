import java.io.*;
   
public class PLXC {

	public static PrintStream out;

	static public void main(String argv[]) {
		parser p = null;
		try {
			Reader in = new InputStreamReader(System.in);
			out = System.out;

			// Si nos proporcionan fichero de entrada
			if(argv.length > 0)
				in = new FileReader(argv[0]);

			// Si nos proporcionan fichero de salida
			if(argv.length > 1)
				out = new PrintStream(new FileOutputStream(argv[1]));

			p = new parser(new Yylex(in));
			Object result = p.parse().value;
		} catch (RuntimeException re) {
			p.done_parsing();						// If exception is thrown, stop parsing
			// re.printStackTrace(System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
