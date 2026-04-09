public class ArvoreAVL extends ArvoreBP {
    
    private No raiz;

    private int size;

    public ArvoreAVL(int value){
        super(value);
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
        
        No novo = new No(paiAtual, value);
        int novo_FB;
        if (value < paiAtual.getValue()) {
            paiAtual.setFE(novo);
            novo_FB = paiAtual.getFB() + 1;
        } 
        else {
            paiAtual.setFD(novo);
            novo_FB = paiAtual.getFB() - 1;
        }
        this.size++;
        paiAtual.setFB(novo_FB);

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
            if (novo != null) novo.setPai(no);
            no.setFE(novo);
            no.setFB(no.getFB() - 1);
        }
        else if (value > no.getValue()) {
            No novo = removerRec(no.getFD(), value);
            if (novo != null) novo.setPai(no);
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
            if (fe.getFB() >= 0) {
                RSD(no);
            }
            else {
                RDD(no);
            }
        }

        else if (no.getFB() < -1) {
            No fd = no.getFD();
            if (fd.getFB() <= 0) {
                RSE(no);
            }
            else {
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
}