import java.io.PrintStream;

public class Printer {

	private PrintStream out = Generator.getInstance().out();				// Print stream
	private SymbolTable stack  = Generator.getInstance().symbolTable();		// Symbol Table

	public Printer(){}

    public void assignment(String id) {
        dup();
        store(stack.lookUp(id));
    }

	public Condition condition(Object e1, int type, Object e2){
		Condition cond = new Condition();

		switch(type){
			// if a-b == 0 goto trueLabel, else falseLabel
			case Condition.EQ:
				sub();
				ifEqual(cond.getTrueLabel());
				goTo(cond.getFalseLabel());
				break;
			// if a-b != 0 goto trueLabel, else falseLabel
			case Condition.NEQ:
				sub();
				ifNotEqual(cond.getTrueLabel());
				goTo(cond.getFalseLabel());
				break;
			// if a-b < 0 goto trueLabel, else falseLabel
			case Condition.LOW:
				sub();
				ifLower(cond.getTrueLabel());
				goTo(cond.getFalseLabel());
				break;
			// if a-b <= 0 goto trueLabel, else falseLabel
			case Condition.LOE:
				sub();
				ifLowerOrEqual(cond.getTrueLabel());
				goTo(cond.getFalseLabel());
				break;
			// if a-b > 0 goto trueLabel, else falseLabel
			case Condition.GRE:
				sub();
				ifGreater(cond.getTrueLabel());
				goTo(cond.getFalseLabel());
				break;
			// if a-b >= 0 goto trueLabel, else falseLabel
			case Condition.GOE:
				ifGreaterOrEqual(cond.getTrueLabel());
				goTo(cond.getFalseLabel());
				break;
		}

		return cond;
	}

	/**
	 * INSTRUCTIONS
	 */

	public void method(String f){
		out.println(".method public static " + f + "(I)I");
	}

	public void push(int n){
		out.println("    sipush " + n);
		stack.push();
	}

	public void load(int n) {
		out.println("    iload " + n);
		stack.push();
	}

	public void store(int n){
		out.println("    istore " + n);
		stack.pop();
	}

	public void add(){
		out.println("    iadd");
		stack.pop();
	}

	public void sub(){
		out.println("    isub");
		stack.pop();
	}

	public void mul(){
		out.println("    imul");
		stack.pop();
	}

	public void div(){
		out.println("    idiv");
		stack.pop();
	}

	public void pop(){
		out.println("    pop");
		stack.pop();
	}

	public void dup(){
		out.println("    dup");
		stack.push();
	}

	public void label(String l){
		out.println(l + ":");
	}

	public void goTo(String l){
		out.println("    goto " + l);
	}

	public void ifEqual(String l){
		out.println("    ifeq " + l);
		stack.pop();
	}

	public void ifNotEqual(String l){
		out.println("    ifne " + l);
		stack.pop();
	}

	public void ifGreater(String l){
		out.println("    ifgt " + l);
		stack.pop();
	}

	public void ifGreaterOrEqual(String l){
		out.println("    ifge " + l);
		stack.pop();
	}

	public void ifLower(String l){
		out.println("    iflt " + l);
		stack.pop();
	}

	public void ifLowerOrEqual(String l){
		out.println("    ifle " + l);
		stack.pop();
	}

	public void invoke(String f){
		out.println("    invokestatic JPL/" + f + "(I)I");
	}

	public void retorno(){
		out.println("    ireturn");
		out.println("    nop");
	}

	public void endMethod(){
		out.println("    .limit stack " + stack.getStackSize());
		out.println("    .limit locals " + stack.getLocalsSize());
		out.println(".end method");
		out.println();
	}
}
