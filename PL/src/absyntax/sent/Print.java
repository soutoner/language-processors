package absyntax.sent;

import absyntax.Generator;
import absyntax.exp.Exp;

public final class Print extends Sent {

    private final Exp expr;

    public Print(Exp e){
        this.expr = e;
    }

    @Override
    public Object eval() {
        Generator.getInstance().out().println("    print " + expr.eval() + ";");

        return null;
    }
}
