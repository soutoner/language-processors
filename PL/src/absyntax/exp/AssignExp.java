package absyntax.exp;

import absyntax.Generator;
import absyntax.Printer;

public final class AssignExp extends Exp {

    public final Ident ident;
    public final Exp exp;

    public AssignExp(Ident ident, Exp exp) {
        this.ident = ident;
        this.exp = exp;
    }

    @Override
    public Object eval() {
        Printer.assignation(ident.eval(), exp.eval());

        return ident;
    }
}
