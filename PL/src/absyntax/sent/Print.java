package absyntax.sent;

import absyntax.Generator;
import absyntax.exp.Exp;

public final class Print extends Sent {

    private final Exp exp;

    public Print(Exp e){
        this.exp = e;
    }

    @Override
    public Object eval() {
        Generator.getInstance().out().println("    print " + exp.eval() + ";");

        return null;
    }
}
