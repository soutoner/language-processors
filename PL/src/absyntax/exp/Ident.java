package absyntax.exp;

public final class Ident extends Exp {

    public final String value;

    public Ident(String value) {
        this.value = value;
    }

    @Override
    public Object eval() {
        return value;
    }

    @Override
    public String toString(){
        return value;
    }
}
