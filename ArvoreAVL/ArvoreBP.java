public class ArvoreBP {
    
    private No raiz;

    private int size;


    public ArvoreBP(int value){
        this.raiz = new No(null, value);
        this.size = 1;
    }


    public int size(){
        return this.size;
    }
    

    public No raiz(){
        return this.raiz;
    }


    public No pai(No no){
        return no.getPai();
    }


    public No buscar(No no) throws NoInexistente{
        No atual = this.raiz;
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


    public int altura(No no){
        if (no == null){
            return 0;
        }
        return 1 + Math.max(altura(no.getFE()), altura(no.getFD()));
    }


    public int profundidade(No no){
        if (no == this.raiz){
            return 0;
        }
        return 1 + profundidade(no.getPai());
    }
}