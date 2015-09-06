package absyntax.sent;

import absyntax.Generator;
import absyntax.Printer;
import absyntax.exp.Exp;

public final class Print extends Sent {

    private final Exp exp;

    public Print(Exp e){
        this.exp = e;
    }

    @Override
    public Object eval() {
        Printer.print(exp.eval());

        return null;
    }
}
