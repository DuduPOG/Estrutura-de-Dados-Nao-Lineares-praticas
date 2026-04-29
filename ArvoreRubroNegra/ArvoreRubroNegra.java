public class ArvoreRubroNegra {
    private NoRB raiz;

    private int size;


    public ArvoreRubroNegra(){
        this.raiz = null;
        this.size = 0;
    }


    public int size(){
        return this.size;
    }
    

    public NoRB raiz(){
        return this.raiz;
    }


    public NoRB pai(NoRB no){
        return no.getPai();
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    private static boolean isBST(NoRB no, Integer min, Integer max) {
        if (no == null) {
            return true;
        }

        if ((min != null && no.getValue() <= min) ||
            (max != null && no.getValue() >= max)) {
            return false;
        }

        return isBST(no.getFE(), min, no.getValue()) &&
            isBST(no.getFD(), no.getValue(), max);
    }

    public NoRB buscar(NoRB no, int value){
        while(no != null){
            if (no.getValue() == value){
                return no;
            }

            if (value < no.getValue()){
                no = no.getFE();
            }
            else {
                no = no.getFD();
            }
        }
        return null;
    }

    private NoRB.Cores cor(NoRB no) {
        return no == null ? NoRB.Cores.P : no.getCor();
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
            this.raiz.setCor(NoRB.Cores.P);
            this.size = 1;
            return;
        }
        
        NoRB novo = new NoRB(paiAtual, value);
        novo.setCor(NoRB.Cores.V);

        if (value < paiAtual.getValue()) {
            paiAtual.setFE(novo);
        } 
        else {
            paiAtual.setFD(novo);
        }
        this.size++;
        rebalanceamento(novo);
    }


    public void remover(int value) {
        NoRB alvo = buscar(this.raiz, value);

        if (alvo == null) {
            return;
        }

        NoRB substituto = alvo;
        NoRB.Cores corOriginal = substituto.getCor();

        NoRB sucessor;
        NoRB paiSucessor;

        if (alvo.getFE() == null) {
            sucessor = alvo.getFD();
            paiSucessor = alvo.getPai();
            transplantar(alvo, sucessor);

        }
        else if (alvo.getFD() == null) {
            sucessor = alvo.getFE();
            paiSucessor = alvo.getPai();
            transplantar(alvo, sucessor);
        }
        else {
            substituto = NoSubstituto(alvo.getFD());
            corOriginal = substituto.getCor();

            sucessor = substituto.getFD();

            if (substituto.getPai() == alvo) {
                paiSucessor = substituto;
                if (sucessor != null) {
                    sucessor.setPai(substituto);
                }
            } else {
                paiSucessor = substituto.getPai();
                transplantar(substituto, substituto.getFD());
                substituto.setFD(alvo.getFD());
                substituto.getFD().setPai(substituto);
            }

            transplantar(alvo, substituto);

            substituto.setFE(alvo.getFE());
            substituto.getFE().setPai(substituto);

            substituto.setCor(alvo.getCor());
        }

        this.size--;

        if (corOriginal == NoRB.Cores.P) {
            rebalancearRemocao(sucessor, paiSucessor);
        }

        if (this.raiz != null) {
            this.raiz.setCor(NoRB.Cores.P);
        }
    }

    private void rebalancearRemocao(NoRB no, NoRB pai) {

        while (no != this.raiz && cor(no) == NoRB.Cores.P) {

            if (pai == null) {
                break;
            }

            if (no == pai.getFE()) {
                NoRB irmaoPai = pai.getFD();

                if (irmaoPai == null) {
                    no = pai;
                    pai = no.getPai();
                    continue;
                }

                if (cor(irmaoPai) == NoRB.Cores.V) {
                    irmaoPai.setCor(NoRB.Cores.P);
                    pai.setCor(NoRB.Cores.V);
                    RSE(pai);
                    irmaoPai = pai.getFD();
                }

                if (cor(irmaoPai.getFE()) == NoRB.Cores.P && cor(irmaoPai.getFD()) == NoRB.Cores.P) {
                    irmaoPai.setCor(NoRB.Cores.V);
                    no = pai;
                    pai = no.getPai();
                }
                else {
                    if (cor(irmaoPai.getFD()) == NoRB.Cores.P) {
                        if (irmaoPai.getFE() != null) {
                            irmaoPai.getFE().setCor(NoRB.Cores.P);
                        }
                        irmaoPai.setCor(NoRB.Cores.V);
                        RSD(irmaoPai);
                        irmaoPai = pai.getFD();
                    }
                    irmaoPai.setCor(pai.getCor());
                    pai.setCor(NoRB.Cores.P);

                    if (irmaoPai.getFD() != null) {
                        irmaoPai.getFD().setCor(NoRB.Cores.P);
                    }
                    RSE(pai);
                    no = this.raiz;
                }
            }

            else {
                NoRB irmaoPai = pai.getFE();
                if (irmaoPai == null) {
                    no = pai;
                    pai = no.getPai();
                    continue;
                }
                if (cor(irmaoPai) == NoRB.Cores.V) {
                    irmaoPai.setCor(NoRB.Cores.P);
                    pai.setCor(NoRB.Cores.V);
                    RSD(pai);
                    irmaoPai = pai.getFE();
                }
                if (cor(irmaoPai.getFE()) == NoRB.Cores.P && cor(irmaoPai.getFD()) == NoRB.Cores.P) {

                    irmaoPai.setCor(NoRB.Cores.V);
                    no = pai;
                    pai = no.getPai();
                }
                else {
                    if (cor(irmaoPai.getFE()) == NoRB.Cores.P) {
                        if (irmaoPai.getFD() != null) {
                            irmaoPai.getFD().setCor(NoRB.Cores.P);
                        }
                        irmaoPai.setCor(NoRB.Cores.V);
                        RSE(irmaoPai);
                        irmaoPai = pai.getFE();
                    }
                    irmaoPai.setCor(pai.getCor());
                    pai.setCor(NoRB.Cores.P);
                    if (irmaoPai.getFE() != null) {
                        irmaoPai.getFE().setCor(NoRB.Cores.P);
                    }
                    RSD(pai);
                    no = this.raiz;
                }
            }
        }
        if (no != null) {
            no.setCor(NoRB.Cores.P);
        }
    }

    private void transplantar(NoRB antigo, NoRB substituto) {
        if (antigo.getPai() == null) {
            this.raiz = substituto;
        }
        else if (antigo == antigo.getPai().getFE()) {
            antigo.getPai().setFE(substituto);
        }
        else {
            antigo.getPai().setFD(substituto);
        }

        if (substituto != null) {
            substituto.setPai(antigo.getPai());
        }
    }

    private NoRB NoSubstituto(NoRB no) {
        while (no.getFE() != null) {
            no = no.getFE();
        }
        return no;
    }

    public NoRB RSE(NoRB no){
        NoRB subArvoreDireita = no.getFD();
        NoRB subArvoreEsquerda = subArvoreDireita.getFE();

        subArvoreDireita.setFE(no);
        no.setFD(subArvoreEsquerda);

        subArvoreDireita.setPai(no.getPai());
        no.setPai(subArvoreDireita);

        if (subArvoreEsquerda != null){
            subArvoreEsquerda.setPai(no);
        }

        if (subArvoreDireita.getPai() == null) {
            this.raiz = subArvoreDireita;
        }
        else if (subArvoreDireita.getPai().getFE() == no) {
            subArvoreDireita.getPai().setFE(subArvoreDireita);
        } 
        else {
            subArvoreDireita.getPai().setFD(subArvoreDireita);
        }        
        return subArvoreDireita;
    }

    public NoRB RSD(NoRB no){
        NoRB subArvoreEsquerda = no.getFE();
        NoRB subArvoreDireita = subArvoreEsquerda.getFD();

        subArvoreEsquerda.setFD(no);
        no.setFE(subArvoreDireita);

        subArvoreEsquerda.setPai(no.getPai());
        no.setPai(subArvoreEsquerda);

        if (subArvoreDireita != null){
            subArvoreDireita.setPai(no);
        }

        if (subArvoreEsquerda.getPai() == null) {
            this.raiz = subArvoreEsquerda;
        }
        else if (subArvoreEsquerda.getPai().getFD() == no) {
            subArvoreEsquerda.getPai().setFD(subArvoreEsquerda);
        } 
        else {
            subArvoreEsquerda.getPai().setFE(subArvoreEsquerda);
        }
        return subArvoreEsquerda;
    }

    public NoRB RDE(NoRB no){
        no.setFD(RSD(no.getFD()));
        return RSE(no);
    }

    public NoRB RDD(NoRB no){
        no.setFE(RSE(no.getFE()));
        return RSD(no);
    }

    private void rebalanceamento(NoRB no) {
        while (no != this.raiz && no.getPai().getCor() == NoRB.Cores.V) {

            NoRB pai = no.getPai();
            NoRB avo = pai.getPai();

            if (avo == null) {
                break;
            }
            if (pai == avo.getFE()) {
                NoRB tio = avo.getFD();
                if (cor(tio) == NoRB.Cores.V) {
                    pai.setCor(NoRB.Cores.P);
                    tio.setCor(NoRB.Cores.P);
                    avo.setCor(NoRB.Cores.V);
                    no = avo;
                }
                else {
                    if (no == pai.getFD()) {
                        no = pai;
                        RSE(no);
                        pai = no.getPai();
                        avo = pai.getPai();
                    }
                    pai.setCor(NoRB.Cores.P);
                    avo.setCor(NoRB.Cores.V);
                    RSD(avo);
                }
            }
            else {
                NoRB tio = avo.getFE();
                if (cor(tio) == NoRB.Cores.V) {
                    pai.setCor(NoRB.Cores.P);
                    tio.setCor(NoRB.Cores.P);
                    avo.setCor(NoRB.Cores.V);
                    no = avo;
                }
                else {
                    if (no == pai.getFE()) {
                        no = pai;
                        RSD(no);
                        pai = no.getPai();
                        avo = pai.getPai();
                    }
                    pai.setCor(NoRB.Cores.P);
                    avo.setCor(NoRB.Cores.V);
                    RSE(avo);
                }
            }
        }
        this.raiz.setCor(NoRB.Cores.P);
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

    public void desenharArvore(){
        int h = altura(this.raiz);
        int largura = (int) Math.pow(2, h + 1) - 1;
        String[][] mat = new String[h + 1][largura];

        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                mat[i][j] = "   ";
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

    public static void main(String[] args) throws NoInexistente {
       ArvoreRubroNegra teste = new ArvoreRubroNegra();
    }
}
