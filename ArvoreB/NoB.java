/**
 * Classe que representa um nó em uma Árvore B
 * 
 * Genérica para qualquer tipo comparável T, mas nesta implementação é usada com String
 */
public class NoB<T extends Comparable<T>> {
    private T[] chaves;                 // Array de chaves
    private NoB<T>[] filhos;            // Array de apontadores para filhos
    private int numChaves;              // Número atual de chaves no nó
    private boolean isFolha;            // Indica se o nó é folha
    private final int t;                // Grau mínimo da árvore B

    /**
     * Construtor do nó
     * @param t grau mínimo (parâmetro de capacidade)
     */
    @SuppressWarnings("unchecked")
    public NoB(int t) {
        this.t = t;
        this.chaves = (T[]) new Comparable[2 * t - 1];  // Máximo 2t-1 chaves
        this.filhos = (NoB<T>[]) new NoB[2 * t];             // Máximo 2t filhos
        this.numChaves = 0;
        this.isFolha = true;
    }

    /**
     * Retorna a chave no índice especificado
     */
    public T getChave(int indice) {
        return chaves[indice];
    }

    /**
     * Define a chave no índice especificado
     */
    public void setChave(int indice, T chave) {
        this.chaves[indice] = chave;
    }

    /**
     * Retorna o filho no índice especificado
     */
    public NoB<T> getFilho(int indice) {
        return filhos[indice];
    }

    /**
     * Define o filho no índice especificado
     */
    public void setFilho(int indice, NoB<T> filho) {
        this.filhos[indice] = filho;
    }

    /**
     * Retorna o número de chaves no nó
     */
    public int getNumChaves() {
        return numChaves;
    }

    /**
     * Incrementa o número de chaves
     */
    public void increaseNumChaves() {
        this.numChaves++;
    }

    /**
     * Decrementa o número de chaves
     */
    public void decreaseNumChaves() {
        this.numChaves--;
    }

    /**
     * Verifica se o nó é uma folha
     */
    public boolean getIsFolha() {
        return isFolha;
    }

    /**
     * Define se o nó é folha
     */
    public void setFolha(boolean folha) {
        this.isFolha = folha;
    }
}
