package absyntax.sent;

import java.util.List;

public final class ListaSent extends Sent {

    private final List<Sent> listaSent;

    public ListaSent(List<Sent> listaSent) {
        this.listaSent = listaSent;
    }

    @Override
    public Object eval() {
        for(Sent s: listaSent)
            s.eval();

        return null;
    }
}
