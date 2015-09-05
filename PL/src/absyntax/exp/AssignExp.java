package absyntax.exp;

import absyntax.Generator;

public final class AssignExp extends Exp {

    public final Ident ident;
    public final Exp exp;

    public AssignExp(Ident ident, Exp exp) {
        this.ident = ident;
        this.exp = exp;
    }

    @Override
    public Object eval() {
        Generator.getInstance().out().println("    " + ident.eval() + " = " + exp.eval() + ";");

        return ident;
    }
}
