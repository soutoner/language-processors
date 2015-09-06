package absyntax.cond;

import absyntax.Printer;
import absyntax.exp.Exp;

public final class BinaryCond extends Cond {

    // Enum for types of condition
    public final static int EQ = 0;
    public final static int NEQ = 1;
    public final static int LOW = 2;
    public final static int LOE = 3;
    public final static int GRE = 4;
    public final static int GOE = 5;

    private final Cond.CondTags tags;
    private final int type;
    private final Exp exp1, exp2;

    public BinaryCond(Exp exp1, int type, Exp exp2) {
        this.tags = new Cond.CondTags();
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.type = type;
    }

    public BinaryCond(Cond.CondTags tags, Exp exp1, int type, Exp exp2) {
        this.tags = tags;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.type = type;
    }

    @Override
    public Object eval() {
        String trueTag = tags.getTrueTag(), falseTag = tags.getFalseTag();

        switch(type){
            case EQ:
                Printer.ifEqual(exp1.eval(), exp2.eval(), trueTag);
                Printer.goTo(falseTag);
                break;
            case NEQ:
                Printer.ifEqual(exp1.eval(), exp2.eval(), falseTag);
                Printer.goTo(trueTag);
                break;
            case LOW:
                Printer.ifLower(exp1.eval(), exp2.eval(), trueTag);
                Printer.goTo(falseTag);
                break;
            case LOE:
                Printer.ifLower(exp2.eval(), exp1.eval(), falseTag);
                Printer.goTo(trueTag);
                break;
            case GRE:
                Printer.ifLower(exp2.eval(), exp1.eval(), trueTag);
                Printer.goTo(falseTag);
                break;
            case GOE:
                Printer.ifLower(exp1.eval(), exp2.eval(), falseTag);
                Printer.goTo(trueTag);
                break;
        }

        return null;
    }

    @Override
    public CondTags getTags() {
        return tags;
    }

    @Override
    public Cond not() {
        return new BinaryCond(tags.not(), exp1, type, exp2);
    }
}
