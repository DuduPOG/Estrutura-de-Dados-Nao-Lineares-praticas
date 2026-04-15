


public class ArvoreAVL extends ArvoreBP {
    
    private No raiz;

    private int size;

    public ArvoreAVL(){
        super();
    }
    
    @Override
    public int size(){
        return this.size;
    }

    public int altura(No no){
        if (no == null){
            return 0;
        }
        return 1 + Math.max(altura(no.getFE()), altura(no.getFD()));
    }

    public boolean isInternal(No no){
        return no != null && (no.getFE() != null || no.getFD() != null);
    }


    public boolean isExternal(No no){
        return no != null && (no.getFE() == null && no.getFD() == null);
    }


    public boolean isRoot(No no){
        return no == this.raiz;
    }

    @Override
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
        int novo_FB;
        if (value < paiAtual.getValue()) {
            if (isExternal(paiAtual)){
                novo_FB = paiAtual.getFB() + 1;
                paiAtual.setFB(novo_FB);
            }
            else {
                paiAtual.setFB(0);
            }
            paiAtual.setFE(novo);
            
        } 
        else {
            if (isExternal(paiAtual)){
                novo_FB = paiAtual.getFB() - 1;
                paiAtual.setFB(novo_FB);
            }
            else {
                paiAtual.setFB(0);
            }
            paiAtual.setFD(novo);
        }
        this.size++;
        if (paiAtual.getFB() == 0){
            return;
        }

        No current = paiAtual.getPai();

        No currentChild = paiAtual;

        while (current != null) {
            if (currentChild == current.getFE()) {
                current.setFB(current.getFB() + 1);
                if (current.getFB() > 1){
                    if (currentChild.getFB() > 0){
                        RSD(current);
                    }
                    else {
                        RDD(current);
                    }
                    break;
                }
            }
            else {
                current.setFB(current.getFB() - 1);
                if (current.getFB() < -1){
                    if (currentChild.getFB() < 0){
                        RSE(current);
                    }
                    else {
                        RDE(current);
                    }
                    break;
                }
            }
            currentChild = current;
            current = current.getPai();
        }
    }

    @Override
    public void remover(int value) {
        this.raiz = removerRec(this.raiz, value);
    }

    private No removerRec(No no, int value) {
        if (no == null){
            return null;
        }
        if (value < no.getValue()) {
            No novo = removerRec(no.getFE(), value);
            if (novo != null){
               novo.setPai(no); 
            }
            no.setFE(novo);
            no.setFB(no.getFB() - 1);
        }
        else if (value > no.getValue()) {
            No novo = removerRec(no.getFD(), value);
            if (novo != null){
                novo.setPai(no);
            }
            no.setFD(novo);
            no.setFB(no.getFB() + 1);
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
            if (novo != null){
                novo.setPai(no);
            }    
            no.setFD(novo);
            no.setFB(no.getFB() + 1);
        }

        if (no.getFB() > 1) {
            No fe = no.getFE();
            if (fe != null && fe.getFB() >= 0) {
                RSD(no);
            }
            else if (fe != null) {
                RDD(no);
            }
        }
        else if (no.getFB() < -1) {
            No fd = no.getFD();
            if (fd != null && fd.getFB() <= 0) {
                RSE(no);
            }
            else if (fd != null) {
                RDE(no);
            }
        }

        return no;
    }


    public void RSE(No no){
        No subArvoreDireita = no.getFD();
        No subArvoreEsquerda = subArvoreDireita.getFE();

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
        int FB_B_novo = no.getFB() + 1 - Math.min(subArvoreDireita.getFB(), 0);
        int FB_A_novo = subArvoreDireita.getFB() + 1 + Math.max(FB_B_novo, 0);
        no.setFB(FB_B_novo);
        subArvoreDireita.setFB(FB_A_novo);
    }

    public void RSD(No no){
        No subArvoreEsquerda = no.getFE();
        No subArvoreDireita = subArvoreEsquerda.getFD();

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
        int FB_B_novo = no.getFB() - 1 - Math.max(subArvoreEsquerda.getFB(), 0);
        int FB_A_novo = subArvoreEsquerda.getFB() - 1 + Math.min(FB_B_novo, 0);
        no.setFB(FB_B_novo);
        subArvoreEsquerda.setFB(FB_A_novo);
    }

    public void RDE(No no){
        RSD(no.getFD());
        RSE(no);
    }

    public void RDD(No no){
        RSE(no.getFE());
        RSD(no);
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
        mat[linha][meio] += "[";
        mat[linha][meio] += String.valueOf(no.getFB());
        mat[linha][meio] += "]";
        preencherMatriz(no.getFE(), mat, linha + 1, esq, meio - 1);
        preencherMatriz(no.getFD(), mat, linha + 1, meio + 1, dir);
    }
/*
    public static void main(String[] args) {
        
        ArvoreAVL teste = new ArvoreAVL(4);
        teste.inserir(2);
        teste.inserir(6);
        teste.inserir(1);
        teste.inserir(3);
        //teste.inserir(5);
        //teste.inserir(7);
        //teste.remover(4);
        teste.desenharArvore();
    }
        */
        
}