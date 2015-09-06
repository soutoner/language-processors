package absyntax.exp;

import absyntax.*;

public final class BinaryOp extends Exp {

    private final char op;
    private final Exp exp1, exp2;

    public BinaryOp(char op, Exp exp1, Exp exp2) {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Object eval() {
        String tmp = Generator.getInstance().newTmp();

        Printer.assignation(tmp, exp1.eval() + " " + op + " " + exp2.eval());

        return tmp;
    }
}
