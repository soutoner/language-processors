package absyntax.cond;

public final class NotCond extends Cond {

    private final Cond cond;

    public NotCond(Cond c) {
        this.cond = c.not();
    }

    @Override
    public Object eval() {
        return cond.eval();
    }

    @Override
    public CondTags getTags() {
        return cond.getTags();
    }

    @Override
    public Cond not() {
        return cond.not();
    }
}
