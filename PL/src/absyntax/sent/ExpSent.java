package absyntax.sent;

import absyntax.exp.Exp;

public final class ExpSent extends Sent {

    private final Exp expr;

    public ExpSent(Exp expr) {
        this.expr = expr;
    }

    @Override
    public Object eval() {
        expr.eval();

        return null;
    }
}
