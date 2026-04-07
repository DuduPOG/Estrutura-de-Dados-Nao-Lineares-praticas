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

        if (value < novo.getPai().getValue()) {
            paiAtual.setFE(novo);
        } 
        else {
            paiAtual.setFD(novo);
        }
        this.size++;
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


    public void RSE(){
        
    }

    public void RSD(){

    }

    public void RDE(){

    }

    public void RDD(){

    }
}