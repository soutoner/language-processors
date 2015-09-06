package absyntax.cond;

import absyntax.Printer;

public final class OrCond extends Cond {

    private final Cond cond1;
    private final Cond cond2;

    public OrCond(Cond c1, Cond c2){
        this.cond1 = c1;
        this.cond2 = c2;
    }

    @Override
    public Object eval() {
        cond1.eval();
        Printer.label(cond1.getTags().getFalseTag());
        cond2.eval();
        Printer.label(cond1.getTags().getTrueTag());
        Printer.goTo(cond2.getTags().getTrueTag());

        return null;
    }

    @Override
    public CondTags getTags() {
        return cond1.getTags();
    }

    @Override
    public Cond not() {
        return new AndCond(cond1.not(), cond2.not());
    }
}
