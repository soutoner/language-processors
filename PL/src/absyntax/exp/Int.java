package absyntax.exp;

public final class Int extends Exp {

    public final Integer value;

    public Int(Integer v) {
        value = v;
    }

    @Override
    public Object eval() {
        return value;
    }
}
