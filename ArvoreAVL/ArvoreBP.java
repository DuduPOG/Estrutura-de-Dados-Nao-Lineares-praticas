public class ArvoreBP {
    
    private No raiz;

    private int size;


    public ArvoreBP(){
        this.raiz = null;
        this.size = 0;
    }


    public int sizeBP(){
        return this.size;
    }
    

    public No raizBP(){
        return this.raiz;
    }


    public No pai(No no){
        return no.getPai();
    }


    public NoBP buscarBP(NoBP no) throws NoInexistente{
        NoBP atual = this.raiz;
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
        No atual = this.raiz;
        No paiAtual = null;

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
            this.raiz = new No(null, value);
            this.size = 1;
            return;
        }
        
        No novo = new No(paiAtual, value);

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

    private No removerRec(No no, int value) {
        if (no == null){
            return null;
        }

        if (value < no.getValue()) {
            No novo = removerRec(no.getFE(), value);
            if (novo != null) novo.setPai(no);
            no.setFE(novo);
        } 
        else if (value > no.getValue()) {
            No novo = removerRec(no.getFD(), value);
            if (novo != null) novo.setPai(no);
            no.setFD(novo);
        }
        else {
            if (no.getFE() == null) {
                No filho = no.getFD();
                if (filho != null){
                    filho.setPai(no.getPai());
                }
                this.size--;
                return filho;
            }

            if (no.getFD() == null) {
                No filho = no.getFE();
                if (filho != null){
                    filho.setPai(no.getPai());
                }
                this.size--;
                return filho;
            }

            No temp = no.getFD();
            while (temp.getFE() != null){
                temp = temp.getFE();
            }

            no.setValue(temp.getValue());

            No novo = removerRec(no.getFD(), temp.getValue());
            if (novo != null) novo.setPai(no);
            no.setFD(novo);
        }

        return no;
    }


    public boolean ehInterno(No no){
        return no != null && (no.getFE() != null || no.getFD() != null);
    }


    public boolean ehExterno(No no){
        return no != null && (no.getFE() == null && no.getFD() == null);
    }


    public boolean ehRaiz(No no){
        return no == this.raiz;
    }


    public int alturaBP(No no){
        if (no == null){
            return 0;
        }
        return 1 + Math.max(alturaBP(no.getFE()), alturaBP(no.getFD()));
    }


    public int profundidade(No no){
        if (no == this.raiz){
            return 0;
        }
        return 1 + profundidade(no.getPai());
    }

    public void desenharArvoreBP(){
        int h = alturaBP(this.raiz);
        int largura = (int) Math.pow(2, h + 1) - 1;
        String[][] mat = new String[h + 1][largura];

        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                mat[i][j] = " ";
            }
        }
        preencherMatrizBP(raiz, mat, 0, 0, largura - 1);
        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }

    private void preencherMatrizBP(No no, String[][] mat, int linha, int esq, int dir) {
        if (no == null){
            return;
        }
        int meio = (esq + dir) / 2;
        mat[linha][meio] = String.valueOf(no.getValue());
        preencherMatrizBP(no.getFE(), mat, linha + 1, esq, meio - 1);
        preencherMatrizBP(no.getFD(), mat, linha + 1, meio + 1, dir);
    }
}