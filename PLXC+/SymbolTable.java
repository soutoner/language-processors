import java.lang.Double;
import java.lang.Runtime;
import java.lang.RuntimeException;
import java.util.*;
import java.util.ArrayList;

public class SymbolTable {

    // Ocurrence array indexes
    public final static int OCURRENCE_ARR_SIZE = 2;
    public final static int IDX_SCOPE = 0;
    public final static int IDX_TYPE = 1;

    // Enum for variable types
    public final static int INT = 20;
    public final static int FLOAT = 21;

    private Map<String, List<int []>> symTable;    // Map of identifier -> list of ocurrences [scope, type]
    private Printer out;                            // Variable for printing

    public SymbolTable(){
        symTable = new HashMap<String, List<int []>>();
        out = new Printer();
    }

    // Declare variables into Symbol Table
    public void declare(String id, int actualScope, int type){
        List<int []> occurrences = symTable.get(id); // ocurrences of an identifier

        if(occurrences == null){ // we need to add it to the symbol table
            symTable.put(id, new ArrayList<int[]>());
            symTable.get(id).add(createOcurrence(actualScope, type));
        } else { // identifier is declared, fetch first occurrence
            int [] closerScope = occurrences.get(0);

            if(closerScope[IDX_SCOPE] == actualScope ){ // multiple declaration of variable, throw error
                out.error("variable \'" + id + "\' ya declarada");
            } else if (closerScope[IDX_SCOPE] < actualScope){ // not declared in this scope so add occurrence at beginning
                occurrences.add(0, createOcurrence(actualScope, type));
            } else {
                throw new RuntimeException("Purge is not working correctly");
            }
        }

        // TODO: remove this
        // printSymTable();
    }

    // Create an ocurrence array for a variable [scope, type]
    private int [] createOcurrence(int actualScope, int type){
        int [] ocurrence = new int[OCURRENCE_ARR_SIZE];
        ocurrence[IDX_SCOPE] = actualScope;
        ocurrence[IDX_TYPE] = type;
        return ocurrence;
    }

    // Check if an variable was previously declared or not
    public String lookUp(String id){
        List<int []> occurrences = symTable.get(id); // ocurrences of an identifier

        if(occurrences == null){ // variable non declared, throw error
            out.error("variable \'" + id + "\' no declarada");
            return null;
        } else { // identifier is declared, fetch closer occurrence
            if(occurrences.size() > 1 ){ // variable declared more than one, append suffix
                return id + "_" + occurrences.get(0)[IDX_SCOPE];
            } else {
                return id;
            }
        }
    }

    // Purge the symbol table when a scope is left
    public void purge(int actualScope){
        Iterator it = symTable.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<String, List<int []>> entry = (Map.Entry<String, List<int []>>) it.next();

            List<int []> occurrences = entry.getValue();
            int [] closerScope = occurrences.get(0); // get occurrences of a identifier

            if(closerScope[IDX_SCOPE] == actualScope){ // occurrence that belong to the actual scope
                occurrences.remove(0); // so purge it
                if(occurrences.size() == 0){ // if there is no more occurrences, remove the entry
                    it.remove();
                }
            }
        }
    }

    // GETTERS

    // Returns the type of a variable
    public int typeOf(Object o){
        if(o instanceof Integer) { // Integer
            return INT;
        } else if(o instanceof Double) { // Double
            return FLOAT;
        } else { // String
            if(symTable.get(o) == null){ // Tmp
                if(((String) o).charAt(0) == 't'){
                    return INT;
                } else {
                    return FLOAT;
                }
            } else { // Ident, fetch closer ocurrence type
                return symTable.get(o).get(0)[IDX_TYPE];
            }
        }
    }

    /**
     * DEBUGGING
     */

    private void printSymTable(){
        out.raw("# --------------------");
        out.raw("# ID\tSCOPE\tTYPE");
        out.raw("# --------------------");

        Iterator it = symTable.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<String, List<int []>> entry = (Map.Entry<String, List<int []>>) it.next();

            List<int []> occurrences = entry.getValue();

            for(int i = 0; i < occurrences.size(); i++){
                int [] ocurrence = occurrences.get(i); // get occurrences of a identifier
                out.raw("# " + entry.getKey() + "\t" + ocurrence[IDX_SCOPE] + "\t" + printType(ocurrence[IDX_TYPE]));
            }
        }
    }

    private String printType(int type){
        switch(type){
            case SymbolTable.INT:
                return "INT";
            case SymbolTable.FLOAT:
                return "FLOAT";
        }
        return null;
    }

}