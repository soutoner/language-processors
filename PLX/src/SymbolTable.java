import java.util.*;

public class SymbolTable {

    private Map<String, List<Integer>> symTable;    // Map of identifier -> list of ocurrences (n scope)
    private Printer printer;                        // Variable for printing

    public SymbolTable(){
        this.symTable = new HashMap<String, List<Integer>>();
        this.printer = new Printer();
    }

    // Declare variables into Symbol Table
    public void declare(String id, int actualScope){
        List<Integer> occurrences = symTable.get(id); // ocurrences of an identifier

        if(occurrences == null){ // we need to add it to the symbol table
            symTable.put(id, new ArrayList<Integer>());
            symTable.get(id).add(actualScope);
        } else { // identifier is declared, fetch first occurrence
            Integer closerScope = occurrences.get(0);

            if(closerScope.compareTo(actualScope) == 0 ){ // multiple declaration of variable, throw error
                printer.error("variable \'" + id + "\' ya declarada");
            } else if (closerScope.compareTo(actualScope) < 0){ // not declared in this scope so add occurrence at beginning
                occurrences.add(0, actualScope);
            } else {
                throw new RuntimeException("Purge is not working correctly");
            }
        }
    }

    // Check if an variable was previously declared or not
    public String lookUp(String id){
        List<Integer> occurrences = symTable.get(id); // ocurrences of an identifier

        if(occurrences == null){ // variable non declared, throw error
            printer.error("variable \'" + id + "\' no declarada");
            return id;
        } else { // identifier is declared, fetch closer occurrence
            if(occurrences.size() > 1 ){ // variable declared more than one, append suffix
                return id + "_" + occurrences.get(0).toString();
            } else {
                return id;
            }
        }
    }

    // Purge the symbol table when a scope is left
    public void purge(int actualScope){
        Iterator it = symTable.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<String, List<Integer>> entry = (Map.Entry<String, List<Integer>>) it.next();

            List<Integer> occurrences = entry.getValue();
            Integer closerScope = occurrences.get(0); // get occurrences of a identifier

            if(closerScope.compareTo(actualScope) == 0){ // occurrence that belong to the actual scope
                occurrences.remove(0); // so purge it
                if(occurrences.size() == 0){ // if there is no more occurrences, remove the entry
                    it.remove();
                }
            }
        }
    }

}