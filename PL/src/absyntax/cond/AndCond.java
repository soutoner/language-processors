package absyntax.cond;

import absyntax.Printer;

public final class AndCond extends Cond {

    private final Cond cond1;
    private final Cond cond2;

    public AndCond(Cond c1, Cond c2){
        this.cond1 = c1;
        this.cond2 = c2;
    }

    @Override
    public Object eval() {
        cond1.eval();
        Printer.label(cond1.getTags().getTrueTag());
        cond2.eval();
        Printer.label(cond1.getTags().getFalseTag());
        Printer.goTo(cond2.getTags().getFalseTag());

        return null;
    }

    @Override
    public CondTags getTags() {
        return cond2.getTags();
    }

    @Override
    public Cond not() {
        return new OrCond(cond1.not(), cond2.not());
    }
}
