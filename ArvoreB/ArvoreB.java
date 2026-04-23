public class ArvoreB {

    private NoB raiz;
    
    private final int t; // Grau mínimo

    public ArvoreB(int t) {
        this.t = t;
        this.raiz = new NoB(t);
    }
}