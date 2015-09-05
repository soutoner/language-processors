package absyntax;

public class AST {

    private Node root;

    public AST(){

    }

    public Node getRoot(){
        return root;
    }

    public void setRoot(Node r){
        this.root = r;
    }

    public void eval(){
        root.eval();
    }

}
