public class ArvoreB {

    private NoB raiz;
    
    private final int t; // Grau mínimo

    private int size;

    public ArvoreB(int t) {
        this.t = t;
        this.raiz = new NoB(t);
        this.size = 1;
    }

    /*
     public int size(){
        return this.size;
    }

    public No raiz(){
        return this.raiz;
    }

    public int altura(NoB[] no){
        int height = 0;
        while(!(isFolha(no))){
            return height += 1 + altura(no.getChave[0]);
        }
    }

    public boolean isFolha(NoB[] no){
        return no.getIsFolha() == null;        
    }

    public NoB buscar(Object chave){
        NoB atual = this.raiz;
        while (atual != null){
            Object atualChave = atual.chaves[0]
            for (int i = 0; i < atual.getNumChaves(); ++i){
                atualChave = atual.chaves[i]
                if (atualChave == chave){
                    return atual
                }
            }
            if (atualChave == )
            }
        }
        for (int i = 0; i < this.raiz.getNumFilhos()); ++i){

        }
    }

    private NoB buscarRec()

    public void inserir(Object value){
    
    }

    public void remover(Object value){
    
    }

    public void desenharArvore(){
        int h = altura(raiz);
        int largura = (int) Math.pow(2, h + 1) - 1;
        String[][] mat = new String[h + 1][largura];

        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                mat[i][j] = "    ";
            }
        }
        preencherMatriz(raiz, mat, 0, 0, largura - 1);
        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }

    private void preencherMatriz(No no, String[][] mat, int linha, int esq, int dir) {
        if (no == null){
            return;
        }
        int meio = (esq + dir) / 2;
        mat[linha][meio] = String.valueOf(no.getValue());
        mat[linha][meio] += "[" + String.valueOf(no.getFB()) + "]";
        preencherMatriz(no.getFE(), mat, linha + 1, esq, meio - 1);
        preencherMatriz(no.getFD(), mat, linha + 1, meio + 1, dir);
    }

    */
}