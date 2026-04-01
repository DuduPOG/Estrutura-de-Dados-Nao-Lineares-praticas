public class ArvoreBP {
    
    private No raiz;

    private int size;

    public ArvoreBP(){
        this.raiz = null;
        this.size = 0;
    }

    public int size(){
        return this.size;
    }

    public No raiz(){
        return this.raiz;
    }

    public int replace(No no, int value){
        if (no != null){
            int oldElement = no.getValue();
            no.setValue(value);
            return oldElement;
        }
        throw new RuntimeException("Nó vazio");
    }


}