public class ArvoreRubroNegra {
    private NoRB raiz;

    private int size;


    public ArvoreRubroNegra(){
        this.raiz = null;
        this.size = 0;
    }


    public int sizeBP(){
        return this.size;
    }
    

    public NoRB raizBP(){
        return this.raiz;
    }


    public NoRB pai(NoRB no){
        return no.getPai();
    }


    public NoRB buscar(NoRB no) throws NoInexistente{
        NoRB atual = this.raiz;
        while(atual != null){
            if (atual == no){
                return no;
            }
            if (atual.getValue() < no.getValue()){
                atual = atual.getFE();
            }
            else {
                atual = atual.getFD();
            }
        }
        throw new NoInexistente("Nó não encontrado");
    }


    public void inserir(int value) {
        NoRB atual = this.raiz;
        NoRB paiAtual = null;

        while(atual != null){
            paiAtual = atual;
            if (value == atual.getValue()){
            return;
            }
            if (value < atual.getValue()){
                atual = atual.getFE();
            }
            else {
                atual = atual.getFD();
            }
        }

        if (paiAtual == null) {
            this.raiz = new NoRB(null, value);
            this.size = 1;
            return;
        }
        
        NoRB novo = new NoRB(paiAtual, value);

        if (value < paiAtual.getValue()) {
            paiAtual.setFE(novo);
        } 
        else {
            paiAtual.setFD(novo);
        }
        this.size++;
    }


    public void remover(int value) {
        this.raiz = removerRec(this.raiz, value);
    }

    private NoRB removerRec(NoRB no, int value) {
        if (no == null){
            return null;
        }

        if (value < no.getValue()) {
            NoRB novo = removerRec(no.getFE(), value);
            if (novo != null) novo.setPai(no);
            no.setFE(novo);
        } 
        else if (value > no.getValue()) {
            NoRB novo = removerRec(no.getFD(), value);
            if (novo != null) novo.setPai(no);
            no.setFD(novo);
        }
        else {
            if (no.getFE() == null) {
                NoRB filho = no.getFD();
                if (filho != null){
                    filho.setPai(no.getPai());
                }
                this.size--;
                return filho;
            }

            if (no.getFD() == null) {
                NoRB filho = no.getFE();
                if (filho != null){
                    filho.setPai(no.getPai());
                }
                this.size--;
                return filho;
            }

            NoRB temp = no.getFD();
            while (temp.getFE() != null){
                temp = temp.getFE();
            }

            no.setValue(temp.getValue());

            NoRB novo = removerRec(no.getFD(), temp.getValue());
            if (novo != null) novo.setPai(no);
            no.setFD(novo);
        }

        return no;
    }


    public boolean ehInterno(NoRB no){
        return no != null && (no.getFE() != null || no.getFD() != null);
    }


    public boolean ehExterno(NoRB no){
        return no != null && (no.getFE() == null && no.getFD() == null);
    }


    public boolean ehRaiz(NoRB no){
        return no == this.raiz;
    }


    public int altura(NoRB no){
        if (no == null){
            return 0;
        }
        return 1 + Math.max(altura(no.getFE()), altura(no.getFD()));
    }


    public int profundidade(NoRB no){
        if (no == this.raiz){
            return 0;
        }
        return 1 + profundidade(no.getPai());
    }

    public void desenharArvoreBP(){
        int h = altura(this.raiz);
        int largura = (int) Math.pow(2, h + 1) - 1;
        String[][] mat = new String[h + 1][largura];

        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                mat[i][j] = " ";
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

    private void preencherMatriz(NoRB no, String[][] mat, int linha, int esq, int dir) {
        if (no == null){
            return;
        }
        int meio = (esq + dir) / 2;
        mat[linha][meio] = String.valueOf(no.getValue()) + "[" + no.getCor() + "]";
        preencherMatriz(no.getFE(), mat, linha + 1, esq, meio - 1);
        preencherMatriz(no.getFD(), mat, linha + 1, meio + 1, dir);
    }
}
