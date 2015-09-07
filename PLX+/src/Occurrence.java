import java.lang.Override;
import java.lang.String;

public class Occurrence {

    // Enum for variable types
    public final static int INT = 20;
    public final static int FLOAT = 21;

    // Holds the structure of an variable ocurrence [scope, type, size (if array)]
    private int scope;
    private int type;
    private int size; // if not array -> 0, else -> >0

    public Occurrence(int scope, int type, int size){
        this.scope = scope;
        this.type = type;
        this.size = size;
    }

    public Occurrence(int scope, int type){
        this(scope, type, 0);
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // Helpers (DEBUGGING)

    public String printType(){
        String array = (size > 0) ? "[" + size + "]" : "";
        switch(type){
            case INT:
                return "INT" + array;
            case FLOAT:
                return "FLOAT" + array;
        }
        return null;
    }

    @Override
    public String toString() {
        return scope + "\t" + ((type == INT) ? "INT" : "FLOAT") + ((size > 0) ? "[" + size + "]" : "");
    }
}