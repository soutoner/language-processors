import java.lang.*;
import java.util.*;

public class SymbolTable {

    private Map<String, List<Occurrence>> symTable;     // Map of identifier -> list of occurrence
    private Printer printer;                            // Variable for printing

    public SymbolTable() {
        symTable = new HashMap<String, List<Occurrence>>();
        printer = new Printer();
    }

    // Declare variables (array) into Symbol Table
    public String declare(String id, int actualScope, int type, int size) {
        List<Occurrence> occurrences = symTable.get(id); // occurrences of an identifier

        if (occurrences == null) { // we need to add it to the symbol table
            symTable.put(id, new ArrayList<Occurrence>());
            symTable.get(id).add(new Occurrence(actualScope, type, size));
        } else { // identifier is declared, fetch first occurrence
            Occurrence closerOccurrence = occurrences.get(0);

            if (closerOccurrence.getScope() == actualScope) { // multiple declaration of variable, throw error
                printer.error("variable \'" + id + "\' ya declarada");
            } else if (closerOccurrence.getScope() < actualScope) { // not declared in this scope so add occurrence at beginning
                occurrences.add(0, new Occurrence(actualScope, type, size));
            } else {
                throw new RuntimeException("Purge is not working correctly");
            }
        }

        // printSymTable();
        return id;
    }

    // Declare variables into Symbol Table
    public String declare(String id, int actualScope, int type) {
        declare(id, actualScope, type, 0);

        return id;
    }

    // Check if an variable was previously declared or not
    public String lookUp(String id) {
        List<Occurrence> occurrences = symTable.get(id); // occurrences of an identifier

        if (occurrences == null) { // variable non declared, throw error
            printer.error("variable \'" + id + "\' no declarada");
            return null;
        } else { // identifier is declared, fetch closer occurrence
            if (occurrences.size() > 1) { // variable declared more than one, append suffix
                return id + "_" + occurrences.get(0).getScope();
            } else {
                return id;
            }
        }
    }

    /**
     * MUTATORS
     */

    // Purge the symbol table when a scope is left
    public void purge(int actualScope) {
        Iterator it = symTable.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, List<Occurrence>> entry = (Map.Entry<String, List<Occurrence>>) it.next();

            List<Occurrence> occurrences = entry.getValue();
            Occurrence closerOccurrence = occurrences.get(0); // get occurrences of a identifier

            if (closerOccurrence.getScope() == actualScope) { // occurrence that belong to the actual scope
                occurrences.remove(0); // so purge it
                if (occurrences.size() == 0) { // if there is no more occurrences, remove the entry
                    it.remove();
                }
            }
        }
    }

    public void setSizeOf(Object id, int size){
        if(! (id instanceof String))
            throw new RuntimeException("setTmpSize on non tmp");

        symTable.get((String) id).get(0).setSize(size);
    }

    /**
     * GETTERS
     */

    // Returns the type of a variable
    public int typeOf(Object o) {
        if (o instanceof Integer) { // Integer
            return Occurrence.INT;
        } else if (o instanceof Double) { // Double
            return Occurrence.FLOAT;
        } else { // Ident or tmp, fetch closer occurrence type
            if(((String) o).matches(".*_[0-9]+$")) // Variable in a inner scope
                return symTable.get(((String) o).replaceAll("_[0-9]+$", "")).get(0).getType();
            else
                return symTable.get(o).get(0).getType();
        }
    }

    // Returns the size of a variable (if not in the symbolTable, always 0)
    public int sizeOf(Object o) {
        return (symTable.get(o) != null) ? symTable.get(o).get(0).getSize() : 0;
    }

    /**
     * BOOLEANS
     */

    public boolean isReal(Object o){
        return typeOf(o) == Occurrence.FLOAT;
    }

    public boolean isEntero(Object o){
        return typeOf(o) == Occurrence.INT;
    }

    public boolean isArray(Object o){
        return (o instanceof String) && symTable.get(o) != null && sizeOf(o) > 0;
    }

    public boolean isTmp(Object o) { return (o instanceof String) && (((String) o).matches("t[0-9]+") || ((String) o).matches("\\$t[0-9]+")); }

    /**
     * DEBUGGING
     */

    public void printSymTable() {
        printer.comment("--------------------");
        printer.comment("ID\tSCOPE\tTYPE");
        printer.comment("--------------------");

        Iterator it = symTable.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, List<Occurrence>> entry = (Map.Entry<String, List<Occurrence>>) it.next();

            List<Occurrence> occurrences = entry.getValue();

            for (int i = 0; i < occurrences.size(); i++) {
                Occurrence occurrence = occurrences.get(i); // get occurrences of a identifier
                printer.comment(entry.getKey() + "\t" + occurrence);
            }
        }
    }
}