public class Printer {
	// Enum for types of arithmetic operators
	public final static int ADD = 10;
	public final static int SUB = 11;
	public final static int MUL = 12;
	public final static int DIV = 13;
	public final static int USUB = 14;

	// Variables
	private java.io.PrintStream out;
    private SymbolTable stack;

    public Printer(){
        this.out = JPLC.out;
    }

    public Printer(SymbolTable stack){
        this.out = JPLC.out;
        this.stack = stack;
    }

	// Method delcaration
	public void method(String name){
		out.println(".method public static " + name + "(I)I");
	}

	// End method
	public void endMethod(){
		out.println("    .limit stack " + stack.opStackSize());
		out.println("    .limit locals " + stack.localStackSize());
		out.println(".end method");
        out.println();
	}

	// Return
	public void retorno(){
		out.println("    ireturn");
		out.println("    nop");
	}

	// Push
	public void push(Object val){
		out.println("    sipush " + val);
        stack.incrOpStack();
    }

	// Operation
	public void operation(int op){
		switch(op){
			case ADD:
				out.println("    iadd");
				break;
			case SUB:
				out.println("    isub");
				break;
			case MUL:
				out.println("    imul");
				break;
			case DIV:
				out.println("    idiv");
				break;
			case USUB:
				push("-1");
				out.println("    imul");
				break;
		}

        stack.decrOpStack();
    }
	
	public void store(Object pos){
		out.println("    istore " + pos);
        stack.decrOpStack();
	}

    public void assignment(String id) {
        dup();
        store(stack.lookUp(id));
    }

	public void dup() {
		out.println("    dup");
		stack.incrOpStack();
	}

    public void pop() {
        out.println("    pop");
        stack.decrOpStack();
    }

	public void load(Object pos) {
		out.println("    iload " + pos);
        stack.incrOpStack();
	}

	public void callFunction(String ident) {
		out.println("    invokestatic JPL/" + ident + "(I)I");
	}


	public void goTo(String label) {
		out.println("    goto " + label);
	}

	public void label(String tag) {
		out.println(tag + ":");
    }

	public Condition condition(Object e1, int type, Object e2){
		Condition tags = new Condition();

		switch(type){
			/* If a < b goto trueTag, else, goto falseTag */
			case Condition.LOW:
				operation(SUB);
				out.println("    iflt " + tags.trueTag);
				goTo(tags.falseTag);
				break;
			/* If b < a goto trueTag, else, goto falseTag */
			case Condition.GRE:
				operation(SUB);
				out.println("    ifgt " + tags.trueTag);
				goTo(tags.falseTag);
				break;
		}

        stack.decrOpStack();

		return tags;
	}

	public void raw(String s) {
		out.println(s);
	}
}
