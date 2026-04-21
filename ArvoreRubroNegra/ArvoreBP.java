public class ArvoreBP {
    
    private NoBP raiz;

    private int size;


    public ArvoreBP(){
        this.raiz = null;
        this.size = 0;
    }


    public int sizeBP(){
        return this.size;
    }
    

    public NoBP raizBP(){
        return this.raiz;
    }


    public NoBP pai(NoBP NoBP){
        return NoBP.getPai();
    }


    public NoBP buscarBP(NoBP NoBP) throws NoInexistente{
        NoBP atual = this.raiz;
        while(atual != null){
            if (atual == NoBP){
                return NoBP;
            }
            if (atual.getValue() < NoBP.getValue()){
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
        
        NoBP NoBPvo = new NoBP(paiAtual, value);

        if (value < paiAtual.getValue()) {
            paiAtual.setFE(NoBPvo);
        } 
        else {
            paiAtual.setFD(NoBPvo);
        }
        this.size++;
    }


    public void remover(int value) {
        this.raiz = removerRec(this.raiz, value);
    }

    private NoBP removerRec(NoBP NoBP, int value) {
        if (NoBP == null){
            return null;
        }

        if (value < NoBP.getValue()) {
            NoBP NoBPvo = removerRec(NoBP.getFE(), value);
            if (NoBPvo != null) NoBPvo.setPai(NoBP);
            NoBP.setFE(NoBPvo);
        } 
        else if (value > NoBP.getValue()) {
            NoBP NoBPvo = removerRec(NoBP.getFD(), value);
            if (NoBPvo != null) NoBPvo.setPai(NoBP);
            NoBP.setFD(NoBPvo);
        }
        else {
            if (NoBP.getFE() == null) {
                NoBP filho = NoBP.getFD();
                if (filho != null){
                    filho.setPai(NoBP.getPai());
                }
                this.size--;
                return filho;
            }

            if (NoBP.getFD() == null) {
                NoBP filho = NoBP.getFE();
                if (filho != null){
                    filho.setPai(NoBP.getPai());
                }
                this.size--;
                return filho;
            }

            NoBP temp = NoBP.getFD();
            while (temp.getFE() != null){
                temp = temp.getFE();
            }

            NoBP.setValue(temp.getValue());

            NoBP NoBPvo = removerRec(NoBP.getFD(), temp.getValue());
            if (NoBPvo != null) NoBPvo.setPai(NoBP);
            NoBP.setFD(NoBPvo);
        }

        return NoBP;
    }


    public boolean ehInterNoBP(NoBP NoBP){
        return NoBP != null && (NoBP.getFE() != null || NoBP.getFD() != null);
    }


    public boolean ehExterNoBP(NoBP NoBP){
        return NoBP != null && (NoBP.getFE() == null && NoBP.getFD() == null);
    }


    public boolean ehRaiz(NoBP NoBP){
        return NoBP == this.raiz;
    }


    public int alturaBP(NoBP NoBP){
        if (NoBP == null){
            return 0;
        }
        return 1 + Math.max(alturaBP(NoBP.getFE()), alturaBP(NoBP.getFD()));
    }


    public int profundidade(NoBP NoBP){
        if (NoBP == this.raiz){
            return 0;
        }
        return 1 + profundidade(NoBP.getPai());
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

    private void preencherMatrizBP(NoBP NoBP, String[][] mat, int linha, int esq, int dir) {
        if (NoBP == null){
            return;
        }
        int meio = (esq + dir) / 2;
        mat[linha][meio] = String.valueOf(NoBP.getValue());
        preencherMatrizBP(NoBP.getFE(), mat, linha + 1, esq, meio - 1);
        preencherMatrizBP(NoBP.getFD(), mat, linha + 1, meio + 1, dir);
    }
}