import java.lang.Override;
import java.lang.String;
import java.util.List;

public class Occurrence {

    // Enum for variable types
    public final static int INT = 20;
    public final static int FLOAT = 21;

    // Holds the structure of an variable ocurrence [scope, type, size (if array)]
    private int scope;
    private int type;
    private List<Integer> dimens;

    public Occurrence(int scope, int type, List<Integer> dimens){
        this.scope = scope;
        this.type = type;
        this.dimens = dimens;
    }

    public Occurrence(int scope, int type){
        this(scope, type, null);
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize(int idx) {
        return (dimens != null) ? dimens.get(idx) : 0;
    }

    public int getSize() {
        return (dimens != null) ? getSize(0) : 0;
    }

    public void setSize(int idx, int size) {
        this.dimens.set(idx, size);
    }

    public void setSize(int size) {
        setSize(0, size);
    }

    public boolean isArray(){ return dimens != null; }

    public boolean isMultiArray(){ return dimens.size() > 1; }

    public boolean isInt(){ return type == INT; }

    public boolean isFloat(){ return type == FLOAT; }

    // Helpers (DEBUGGING)

    public String printDimens(){
        if(dimens == null)
            return "";

        StringBuilder res = new StringBuilder();

        for(Integer i: dimens)
            res.append("[" + i + "]");

        return res.toString();
    }

    @Override
    public String toString() {
        return scope + "\t" + ((type == INT) ? "INT" : "FLOAT") + printDimens();
    }
}