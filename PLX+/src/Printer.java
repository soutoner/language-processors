import java.io.PrintStream;
import java.util.List;

public class Printer {

    private PrintStream out = Generator.getInstance().out();
    private SymbolTable symTable = Generator.getInstance().symbolTable();

    public Printer() {
    }

    // Creates temporary variables (String tag)
    public String newTmp(int type) {
        return Generator.getInstance().newTmp(type);
    }

    // Creates temporary array variables (String tag)
    public String newTmpArray(int type, int size) {
        return Generator.getInstance().newTmpArray(type, size);
    }

    /**
     * ASSIGNMENTS
     */

    // Assignment ARR[i] = EXP
    public String assignment(String id, Object idx, Object exp) {
        // Assign float to int, error
        if(isEntero(id) && isReal(exp))
            error("Se esta asignando un valor real a una variable enter ");

        // Check range
        checkRange(id, idx);

        // If we need to cast, we need one step more with tmp variables
        Object expression = exp;
        if((isReal(id) && isEntero(exp)))
            expression = casting(exp, typeOf(id));

        return rawAssignment(id, idx, expression, null);
    }

    // Assignments ID = EXP, ARR = ARR
    public String assignment(String id, Object exp) {
        if(isArray(id) && isArray(exp))
            return arrayToArrayAssignment(id, exp);

        // Assign float to int, error
        if(isEntero(id) && isReal(exp))
            error("tried to assign float value to int");

        return rawAssignment(id, (isReal(id) && isEntero(exp)) ? "(float) " + exp : exp);
    }

    // Explicit casting
    public String casting(Object exp, int toType) {
        return rawAssignment(newTmp(toType), (toType == Occurrence.INT) ? "(int) " + exp : "(float) " + exp);
    }

    /**
     * ARRAYS
     */

    // Load array to tmp variable
    public String loadArray(String id, Object idx) {
        String tmp = newTmp(typeOf(id));

        // Check Range
        checkRange(id, idx);

        return rawAssignment(tmp, null, id, idx );
    }

    // assign one array to the other
    public String arrayToArrayAssignment(String arr1, Object arr2){
        if(sizeOf(arr1) < sizeOf(arr2))
            error("Las matrices no son compatibles");

        if(typeOf(arr1) != typeOf(arr2))
            error("Error de tipos");

        String tmp = newTmp(typeOf(arr1));

        for(int i = 0; i < sizeOf(arr2); i++){
            rawAssignment(tmp, null, arr2, i);
            rawAssignment(arr1, i, tmp, null);
        }

        return rawAssignment(arr1, arr2);
    }

    // Check if the idx is in range of the array
    public void checkRange(String id, Object idx) {
        if(!isArray(id))
            return;

        int size = sizeOf(id);

        Condition cond = new Condition();
        comment("Comprobacion de rango");
        ifLower(idx, "0", cond.getTrueLabel());
        ifLower(size, idx, cond.getTrueLabel());
        ifEqual(size, idx, cond.getTrueLabel());
        goTo(cond.getFalseLabel());
        label(cond.getTrueLabel());
        out.println("   error;");
        out.println("   halt;");
        label(cond.getFalseLabel());
    }

    /**
     * ARITHMETIC OPERATIONS
     */

    // Arithmetic operation with tmp
    public String tern(Object e1, String op, Object e2) {
        String tmp = "";
        String op1 = e1.toString(), op2 = e2.toString();

        if (isReal(e1) || isReal(e2)) { // If any of the operand is Real
            tmp = newTmp(Occurrence.FLOAT);
            op += "r";
            if (isEntero(e2)) { // e1 FLOAT, e2 INTEGER (casting needed)
                op2 = assignment(newTmp(Occurrence.FLOAT), e2);
            } else if (isEntero(e1)) { // e1 INTEGER (casting needed), e2 FLOAT
                op1 = assignment(newTmp(Occurrence.FLOAT), e1);
            }
        } else {
            tmp = newTmp(Occurrence.INT);
        }

        return rawAssignment(tmp, op1 + " " + op + " " + op2);
    }

    // Arithmetic operation with id
    public String tern(String id, Object e1, String op, Object e2) {
        String op1 = e1.toString(), op2 = e2.toString();

        if (isReal(e1) || isReal(e2)) { // If any of the operand is Real
            op += "r";
            if (isEntero(e2)) { // e1 FLOAT, e2 INTEGER (casting needed)
                op2 = assignment(newTmp(Occurrence.FLOAT), e2);
            } else if (isEntero(e1)) { // e1 INTEGER (casting needed), e2 FLOAT
                op1 = assignment(newTmp(Occurrence.FLOAT), e1);
            }
        }

        return rawAssignment(id, op1 + " " + op + " " + op2);
    }

    public String mod(Object e1, Object e2) {
        String tmp1 = tern(e1, "/", e2);
        String tmp2 = tern(tmp1, "*", e2);

        return tern(e1, "-", tmp2);
    }

    public String preIncrDecr(String id, String op) {
        if (op.equals("++"))
            return tern(id, id, "+", new Integer(1));
        else
            return tern(id, id, "-", new Integer(1));
    }

    public String postIncrDecr(String id, String op) {
        String tmp = assignment(newTmp(typeOf(id)), id);

        if (op.equals("++"))
            tern(id, id, "+", new Integer(1));
        else
            tern(id, id, "-", new Integer(1));

        return tmp;
    }

    /**
     * CONDITIONS
     */

    public String askOperator(Condition c, Object e1, Object e2, String askLabel){
        String tmp = newTmp(typeOf(e1));

        label(c.getTrueLabel());
        rawAssignment(tmp, e1);
        goTo(askLabel);
        label(c.getFalseLabel());
        rawAssignment(tmp, e2);
        label(askLabel);

        return tmp;
    }

    public Condition condition(Object e1, int type, Object e2) {
        Condition cond = new Condition();
        String trueLabel = cond.getTrueLabel(), falseLabel = cond.getFalseLabel();

        switch (type) {
            // If a == b goto trueLabel, else, goto falseLabel
            case Condition.EQ:
                ifEqual(e1, e2, trueLabel);
                goTo(falseLabel);
                break;
            // If a == b goto falseLabel, else, goto trueLabel
            case Condition.NEQ:
                ifEqual(e1, e2, falseLabel);
                goTo(trueLabel);
                break;
            // If a < b goto trueLabel, else, goto falseLabel
            case Condition.LOW:
                ifLower(e1, e2, trueLabel);
                goTo(falseLabel);
                break;
            // If b < a goto falseLabel, else, goto trueLabel
            case Condition.LOE:
                ifLower(e2, e1, falseLabel);
                goTo(trueLabel);
                break;
            // If b < a goto trueLabel, else, goto falseLabel
            case Condition.GRE:
                ifLower(e2, e1, trueLabel);
                goTo(falseLabel);
                break;
            // If (a < b) goto falseLabel, else, goto trueLabel
            case Condition.GOE:
                ifLower(e1, e2, falseLabel);
                goTo(trueLabel);
                break;
        }

        return cond;
    }

    public Condition forIn(String id, String forTag, Object array){
        if (sizeOf(array) <= 0)
            error("tipo incorrecto");

        String ident = (id.indexOf('[') == -1) ? id : id.substring(0, id.indexOf('[')); // get id even if you have x[i]

        if(typeOf(ident) != typeOf(array))
            error("tipos incompatibles");

        String index = newTmp(typeOf(ident));

        rawAssignment(index, "-1");
        label(forTag);
        tern(index, index, "+" , new Integer(1));
        Condition cond = condition(index, Condition.LOW, sizeOf(array));
        label(cond.getTrueLabel());
        if(id.indexOf('[') == -1){ // Simple ident
            rawAssignment(id, null, array, index);
        } else { // Ident style b[i]
            String tmp = newTmp(typeOf(ident));
            rawAssignment(tmp, null, array, index);
            rawAssignment(id, tmp);
        }

        return cond;
    }

    /**
     * ERRORS & LOGGING
     */

    // # ERROR: msg
    // error;
    // halt;
    public void error(String msg) {
        if (msg != null) comment("ERROR: " + msg);
        out.println("   error;");
        out.println("   halt;");
        throw new RuntimeException();
    }

    // error;
    // halt;
    public void error() { error(null); }

    /**
     * AVAILABLE INSTRUCTIONS
     */

    // asigned = exp; asigned[idx1] = exp; asigned = arr[idx2]; (with casting if needed)
    public String rawAssignment(String asigned, Object idx1, Object expOrArray, Object idx2) {
        String index1 = (idx1 != null) ? "[" + idx1 + "]" : "";
        String index2 = (idx2 != null) ? "[" + idx2 + "]" : "";

        rawAssignment(asigned + index1, expOrArray + index2);

        return asigned;
    }

    // left = right;
    public String rawAssignment(String left, Object right) {
        out.println("   " + left + " = " + right + ";");

        return left;
    }

    // goto label;
    public void goTo(String label) { out.println("   goto " + label + ";"); }

    // if (e1 == e2) goto label;
    public void ifEqual(Object e1, Object e2, String label) { out.println("   if (" + e1 + " == " + e2 + ") goto " + label + ";"); }

    // if (e1 != e2) goto label;
    public void ifNotEqual(Object e1, Object e2, String label) { out.println("   if (" + e1 + " != " + e2 + ") goto " + label + ";"); }

    // if (e1 < e2) goto label;
    public void ifLower(Object e1, Object e2, String label) { out.println("   if (" + e1 + " < " + e2 + ") goto " + label + ";"); }

    // label:
    public void label(String label) { out.println(label + ":"); }

    // print e;
    public void print(Object e) { out.println("   print " + e + ";"); }

    // # comment;
    public void comment(String comment) { out.println("# " + comment); }

    /**
     * GETTERS
     */

    public int typeOf(Object o) { return symTable.typeOf(o); }

    public int sizeOf(Object o) { return symTable.sizeOf(o); }

    /**
     * BOOLEANS
     */

    public boolean isReal(Object o){ return symTable.isReal(o); }

    public boolean isEntero(Object o){ return symTable.isEntero(o); }

    public boolean isArray(Object o){ return symTable.isArray(o); }

    public boolean isTmp(Object o){ return symTable.isTmp(o); }
}
