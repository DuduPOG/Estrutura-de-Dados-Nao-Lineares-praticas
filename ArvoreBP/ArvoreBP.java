public class ArvoreBP {
    
    private NoBP raiz;

    private int size;


    public ArvoreBP(){
        this.raiz = null;
        this.size = 0;
    }


    public int size(){
        return this.size;
    }
    

    public NoBP raiz(){
        return this.raiz;
    }


    public NoBP pai(NoBP no){
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
        NoBP atual = this.raiz;
        NoBP paiAtual = null;

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
            this.raiz = new NoBP(null, value);
            this.size = 1;
            return;
        }
        
        NoBP novo = new NoBP(paiAtual, value);

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

    private NoBP removerRec(NoBP no, int value) {
        if (no == null){
            return null;
        }

        if (value < no.getValue()) {
            NoBP novo = removerRec(no.getFE(), value);
            if (novo != null) novo.setPai(no);
            no.setFE(novo);
        } 
        else if (value > no.getValue()) {
            NoBP novo = removerRec(no.getFD(), value);
            if (novo != null) novo.setPai(no);
            no.setFD(novo);
        }
        else {
            if (no.getFE() == null) {
                NoBP filho = no.getFD();
                if (filho != null){
                    filho.setPai(no.getPai());
                }
                this.size--;
                return filho;
            }

            if (no.getFD() == null) {
                NoBP filho = no.getFE();
                if (filho != null){
                    filho.setPai(no.getPai());
                }
                this.size--;
                return filho;
            }

            NoBP temp = no.getFD();
            while (temp.getFE() != null){
                temp = temp.getFE();
            }

            no.setValue(temp.getValue());

            NoBP novo = removerRec(no.getFD(), temp.getValue());
            if (novo != null) novo.setPai(no);
            no.setFD(novo);
        }

        return no;
    }


    public boolean ehInterno(NoBP no){
        return no != null && (no.getFE() != null || no.getFD() != null);
    }


    public boolean ehExterno(NoBP no){
        return no != null && (no.getFE() == null && no.getFD() == null);
    }


    public boolean ehRaiz(NoBP no){
        return no == this.raiz;
    }


    public int altura(NoBP no){
        if (no == null){
            return 0;
        }
        return 1 + Math.max(altura(no.getFE()), altura(no.getFD()));
    }


    public int profundidade(NoBP no){
        if (no == this.raiz){
            return 0;
        }
        return 1 + profundidade(no.getPai());
    }

    public void desenharArvoreBP(){
        int h = altura(raiz);
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

    private void preencherMatrizBP(NoBP no, String[][] mat, int linha, int esq, int dir) {
        if (no == null){
            return;
        }
        int meio = (esq + dir) / 2;
        mat[linha][meio] = String.valueOf(no.getValue());
        preencherMatrizBP(no.getFE(), mat, linha + 1, esq, meio - 1);
        preencherMatrizBP(no.getFD(), mat, linha + 1, meio + 1, dir);
    }
}
