public class ArvoreAVL extends ArvoreBP {
    
    private No raiz;

    private int size;

    private static class ResultadoRemocao {
        No no;
        boolean mudouAltura;
        ResultadoRemocao(No no, boolean mudouAltura) {
            this.no = no;
            this.mudouAltura = mudouAltura;
        }
    }

    public ArvoreAVL(){
        super();
    }
    
    public int size(){
        return this.size;
    }

    public No raiz(){
        return this.raiz;
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

    public No buscar(int value) throws NoInexistente {
        No atual = this.raiz;
        while(atual != null){
            if (atual.getValue() == value){
                return atual;
            }
            if (atual.getValue() < value) {
                atual = atual.getFE();
            }
            else{
              atual = atual.getFD();  
            }
        }
        throw new NoInexistente("Nó não encontrado");
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

        No filhoAtual = paiAtual;

        while (current != null) {
            if (filhoAtual == current.getFE()) {
                current.setFB(current.getFB() + 1);
                if (current.getFB() > 1){
                    if (filhoAtual.getFB() >= 0){
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
                    if (filhoAtual.getFB() <= 0){
                        RSE(current);
                    }
                    else {
                        RDE(current);
                    }
                    break;
                }
            }
            if (current.getFB() == 0){
                break;
            }
            filhoAtual = current;
            current = current.getPai();
        }
    }

    @Override
    public void remover(int value) {
        ResultadoRemocao resultado = removerRec(this.raiz, value);
        this.raiz = resultado.no;
    }

    private ResultadoRemocao removerRec(No no, int value) {
        if (no == null) {
            return new ResultadoRemocao(null, false);
        }
        
        if (value < no.getValue()) {
            ResultadoRemocao subArvoreEsquerda = removerRec(no.getFE(), value);
            if (subArvoreEsquerda.no != null) {
                subArvoreEsquerda.no.setPai(no);
            }
            no.setFE(subArvoreEsquerda.no);
            
            if (!subArvoreEsquerda.mudouAltura) {
                return new ResultadoRemocao(no, false);
            }
            
            no.setFB(no.getFB() - 1);
        }
        else if (value > no.getValue()) {
            ResultadoRemocao subArvoreDireita = removerRec(no.getFD(), value);
            if (subArvoreDireita.no != null) {
                subArvoreDireita.no.setPai(no);
            }
            no.setFD(subArvoreDireita.no);

            if (!subArvoreDireita.mudouAltura) {
                return new ResultadoRemocao(no, false);
            }
            no.setFB(no.getFB() + 1);
        }
        else {
            if (no.getFE() == null) {
                No fe = no.getFD();
                if (fe != null) {
                    fe.setPai(no.getPai());
                }
                this.size--;
                return new ResultadoRemocao(fe, true);
            }

            if (no.getFD() == null) {
                No filho = no.getFE();
                if (filho != null) {
                    filho.setPai(no.getPai());
                }
                this.size--;
                return new ResultadoRemocao(filho, true);
            }

            No temp = no.getFD();
            while (temp.getFE() != null) {
                temp = temp.getFE();
            }
            no.setValue(temp.getValue());

            ResultadoRemocao sucessor = removerRec(no.getFD(), temp.getValue());
            if (sucessor.no != null) {
                sucessor.no.setPai(no);
            }
            no.setFD(sucessor.no);

            if (!sucessor.mudouAltura) {
                return new ResultadoRemocao(no, false);
            }
            no.setFB(no.getFB() + 1);
        }

        boolean teveRotacao = false;
        No novoNo = no;
        if (no.getFB() > 1) {
            No fe = no.getFE();
            if (fe != null && fe.getFB() >= 0) {
                novoNo = RSD(no);
                teveRotacao = true;
            }
            else if (fe != null) {
                novoNo = RDD(no);
                teveRotacao = true;
            }
        }
        else if (no.getFB() < -1) {
            No fd = no.getFD();
            if (fd != null && fd.getFB() <= 0) {
                novoNo = RSE(no);
                teveRotacao = true;
            }
            else if (fd != null) {
                novoNo = RDE(no);
                teveRotacao = true;
            }
        }

        boolean mudouAltura = novoNo.getFB() == 0;
        
        return new ResultadoRemocao(novoNo, mudouAltura);
    }


    public No RSE(No no){
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
        
        return subArvoreDireita;
    }

    public No RSD(No no){
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
        
        return subArvoreEsquerda;
    }

    public No RDE(No no){
        no.setFD(RSD(no.getFD()));
        return RSE(no);
    }

    public No RDD(No no){
        no.setFE(RSE(no.getFE()));
        return RSD(no);
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
        mat[linha][meio] += "[" + String.valueOf(no.getFB()) + "]";
        preencherMatriz(no.getFE(), mat, linha + 1, esq, meio - 1);
        preencherMatriz(no.getFD(), mat, linha + 1, meio + 1, dir);
    }

    public static void main(String[] args) throws NoInexistente {
        // exceção para busca de nó
        ArvoreAVL teste = new ArvoreAVL();
    }
}
 