import java.util.ArrayList;
import java.util.Iterator;

public class NoB<T extends Comparable<T>> {

    private T[] chaves;

    private int t; // Grau ou Ordem

    private NoB<T>[] filhos; // Array de filhos

    private int numChaves; // Número atual de chaves

    private boolean folha;

    @SuppressWarnings("unchecked")
    public NoB(int t) {
        this.t = t; // Grau ou Ordem
        this.chaves = (T[]) new Comparable[2 * t - 1]; // 2t – 1 chaves
        this.filhos = (NoB<T>[]) new NoB[2 * t]; // 2t filhos
        this.numChaves = 0; // Inicialmente sem chaves
        this.folha = true;
    }
    
    public int getT() {
        return t;
    }

    public void setChave(int i, T chave) {
        this.chaves[i] = chave;
    }

    public T getChave(int i) {
        return chaves[i];
    }
    
    public int getNumFilhos(){
        return filhos.length;
    }
    
    public void setFilho(int i, NoB<T> filho) {
        this.filhos[i] = filho;
    }

    public NoB<T> getFilho(int i) {
        return filhos[i];
    }
    
    public int getNumChaves() {
        return numChaves;
    }
    
    public void increaseNumChaves() {
        if (numChaves < 2 * t - 1) {
            this.numChaves++;
        }
    }
    
    public void decreaseNumChaves() {
        if(numChaves > 0) {
            this.numChaves--;
        }
    }

    public void setFolha(boolean b){
        this.folha = b;
    }

    public boolean getIsFolha(){
        return this.folha;
    }

    public Iterator<NoB> iteratorFilhos(){
        ArrayList<NoB> filhosArvore = new ArrayList<>();
        for (int i = 0; i <= numChaves; ++i){
            if (filhos[i] != null) {
                filhosArvore.add(this.filhos[i]);
            }
        }
        return filhosArvore.iterator();
    }
}