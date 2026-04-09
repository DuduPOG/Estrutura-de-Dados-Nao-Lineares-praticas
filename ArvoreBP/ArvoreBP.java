public class ArvoreBP {
    
    private NoBP raiz;

    private int size;


    public ArvoreBP(int value){
        this.raiz = new NoBP(null, value);
        this.size = 1;
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


    public NoBP buscar(NoBP no) throws NoInexistente{
        NoBP atual = this.raiz;
        while(atual != null){
            if (atual == no){
                return no;
            }
            buscar(no.getFE());
            buscar(no.getFD());
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
        
        NoBP novo = new NoBP(paiAtual, value);

        if (value < novo.getPai().getValue()) {
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
}
