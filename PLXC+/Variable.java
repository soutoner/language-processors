public class Variable {
	// ENUM for types
	public final static int INT = 20;
	public final static int FLOAT = 21;
	// Represents a number or a identifier
	private String value; 	// numeric value or identifier
	private int type;		// type of the variable
	private int size;		// size of variable (if array, -1 if not)

	public Variable(String value, int type){
		this.value = value;
		this.type = type;
		this.size = -1;
	}

	public Variable(String value, int type, int size){
		this.value = value;
		this.type = type;
		this.size = size;
	}

	public String getValue(){
		return value;	
	}

	public int getType(){
		return type;	
	}

	public int getSize(){
		return size;	
	}

	public boolean isFloat(){
		return type == FLOAT;	
	}
	
	public boolean isInt(){
		return type == INT;	
	}

	public boolean isArray(){
		return size > 0;	
	}

	public String toString(){
		return value;
	}
}
