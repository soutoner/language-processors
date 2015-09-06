package absyntax.sent;

import absyntax.Generator;
import absyntax.Printer;
import absyntax.cond.Cond;

import java.io.PrintStream;

public final class IfSent extends Sent {

    private final String ifTag;
    private final Cond cond;
    private final Sent sent;

    public IfSent(String ifTag, Cond cond, Sent sent) {
        this.ifTag = ifTag;
        this.cond = cond;
        this.sent = sent;
    }

    @Override
    public Object eval() {
        cond.eval();
        Printer.label(cond.getTags().getTrueTag());
        sent.eval();
        Printer.goTo(ifTag);
        Printer.label(cond.getTags().getFalseTag());
        Printer.label(ifTag);

        return null;
    }
}
