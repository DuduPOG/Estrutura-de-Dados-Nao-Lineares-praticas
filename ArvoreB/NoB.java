public class NoB{

    private Object[] chaves;
    private int t; // Grau ou Ordem
    private NoB[] filhos; // Array de filhos
    private int numChaves; // Número atual de chaves

    public NoB(int t) {
        this.t = t; // Grau ou Ordem
        this.chaves = new Object[2 * t - 1]; // 2t – 1 chaves
        this.filhos = new NoB[2 * t]; // 2t filhos
        this.numChaves = 0; // Inicialmente sem chaves
    }
}