public class ArvoreB {

    private NoB<String> raiz;
    private final int t;
    private int size;

    public ArvoreB(int t) {
        this.t = t;
        this.raiz = new NoB<>(t);
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public NoB<String> raiz() {
        return this.raiz;
    }

    public int altura(NoB<String> no) {
        if (no == null){
            return -1;
        }
        int altura = 0;
        NoB<String> atual = no;

        while (!atual.getIsFolha()) {
            atual = atual.getFilho(0);
            altura++;
        }
        return altura;
    }

    public boolean isFolha(NoB<String> no) {
        return no.getIsFolha();
    }

    public NoB<String> buscar(String chave) {

        NoB<String> atual = this.raiz;

        while (atual != null) {
            int i = 0;
            while (i < atual.getNumChaves() &&
                   chave.compareTo(atual.getChave(i)) > 0) {
                i++;
            }
            if (i < atual.getNumChaves() &&
                chave.compareTo(atual.getChave(i)) == 0) {
                return atual;
            }
            if (atual.getIsFolha()) {
                return null;
            }
            atual = atual.getFilho(i);
        }
        return null;
    }

    public void inserir(String value) {

        if (this.raiz == null) {
            this.raiz = new NoB<>(t);
            this.raiz.setChave(0, value);
            this.raiz.increaseNumChaves();
            this.size++;
            return;
        }

        if (this.raiz.getNumChaves() == (2 * t - 1)) {

            NoB<String> antigaRaiz = this.raiz;
            NoB<String> novaRaiz = new NoB<>(t);
            novaRaiz.setFolha(false);
            novaRaiz.setFilho(0, antigaRaiz);

            NoB<String> novo = new NoB<>(t);
            novo.setFolha(antigaRaiz.getIsFolha());

            String meio = antigaRaiz.getChave(t - 1);

            for (int j = 0; j < t - 1; j++) {
                novo.setChave(j, antigaRaiz.getChave(j + t));
            }

            if (!antigaRaiz.getIsFolha()) {
                for (int j = 0; j < t; j++) {
                    novo.setFilho(j, antigaRaiz.getFilho(j + t));
                }
            }

            for (int j = t - 1; j < 2 * t - 1; j++) {
                antigaRaiz.setChave(j, null);
            }

            while (antigaRaiz.getNumChaves() > t - 1) {
                antigaRaiz.decreaseNumChaves();
            }

            novaRaiz.setFilho(1, novo);
            novaRaiz.setChave(0, meio);
            novaRaiz.increaseNumChaves();

            this.raiz = novaRaiz;
        }

        NoB<String> atual = this.raiz;


        while (true) {

            int i = atual.getNumChaves() - 1;

            if (atual.getIsFolha()) {

                while (i >= 0 && value.compareTo(atual.getChave(i)) < 0) {
                    atual.setChave(i + 1, atual.getChave(i));
                    i--;
                }

                if (i >= 0 && value.compareTo(atual.getChave(i)) == 0) {
                    return;
                }

                atual.setChave(i + 1, value);
                atual.increaseNumChaves();
                this.size++;
                return;
            }

            while (i >= 0 && value.compareTo(atual.getChave(i)) < 0) {
                i--;
            }

            if (i >= 0 && value.compareTo(atual.getChave(i)) == 0) {
                return;
            }

            i++;

            NoB<String> filho = atual.getFilho(i);

            if (filho.getNumChaves() == (2 * t - 1)) {

                NoB<String> novo = new NoB<>(t);
                novo.setFolha(filho.getIsFolha());

                String meio = filho.getChave(t - 1);

                for (int j = 0; j < t - 1; j++) {
                    novo.setChave(j, filho.getChave(j + t));
                }

                if (!filho.getIsFolha()) {
                    for (int j = 0; j < t; j++) {
                        novo.setFilho(j, filho.getFilho(j + t));
                    }
                }

                for (int j = t - 1; j < 2 * t - 1; j++) {
                    filho.setChave(j, null);
                }

                while (filho.getNumChaves() > t - 1) {
                    filho.decreaseNumChaves();
                }

                for (int j = atual.getNumChaves(); j >= i + 1; j--) {
                    atual.setFilho(j + 1, atual.getFilho(j));
                }

                atual.setFilho(i + 1, novo);

                for (int j = atual.getNumChaves() - 1; j >= i; j--) {
                    atual.setChave(j + 1, atual.getChave(j));
                }

                atual.setChave(i, meio);
                atual.increaseNumChaves();

                if (value.compareTo(meio) >= 0) {
                    i++;
                }
            }

            atual = atual.getFilho(i);
        }
    }

    public void remover(String value){

        if (this.raiz == null){
            return;
        }

        removerRec(this.raiz, value);

        if (raiz.getNumChaves() == 0) {
            if (this.raiz.getIsFolha()) {
                this.raiz = null;
            } 
            else {
                this.raiz = this.raiz.getFilho(0);
            }
        }
    }

    private void removerRec(NoB<String> no, String value) {

        int indice = 0;

        while (indice < no.getNumChaves() &&
            value.compareTo(no.getChave(indice)) > 0) {
            indice++;
        }

        if (indice < no.getNumChaves() &&
            value.compareTo(no.getChave(indice)) == 0) {

            if (no.getIsFolha()) {
                removerDeFolha(no, indice);
            } else {
                removerDeInterno(no, indice);
            }

        } 
        else {

            if (no.getIsFolha()) {
                return;
            }

            boolean ultimo = indice == no.getNumChaves();

            if (no.getFilho(indice).getNumChaves() < t) {
                preencher(no, indice);
            }

            if (ultimo && indice > no.getNumChaves()) {
                removerRec(no.getFilho(indice - 1), value);
            } 
            else {
                removerRec(no.getFilho(indice), value);
            }
        }
    }

    private void removerDeFolha(NoB<String> no, int indice) {
        for (int i = indice + 1; i < no.getNumChaves(); i++) {
            no.setChave(i - 1, no.getChave(i));
        }

        no.setChave(no.getNumChaves() - 1, null);
        no.decreaseNumChaves();
        this.size--;
    }

    private void removerDeInterno(NoB<String> no, int indice) {

        String k = no.getChave(indice);

        NoB<String> filhoEsquerdo = no.getFilho(indice);
        NoB<String> filhoDireito = no.getFilho(indice + 1);

        if (filhoEsquerdo.getNumChaves() >= t) {

            String pred = getPredecessor(filhoEsquerdo);
            no.setChave(indice, pred);
            removerRec(filhoEsquerdo, pred);

        }
        else if (filhoDireito.getNumChaves() >= t) {

            String succ = getSucessor(filhoDireito);
            no.setChave(indice, succ);
            removerRec(filhoDireito, succ);

        }
        else {
            merge(no, indice);
            removerRec(filhoEsquerdo, k);
        }
    }

    private String getPredecessor(NoB<String> no) {
        while (!no.getIsFolha()) {
            no = no.getFilho(no.getNumChaves());
        }
        return no.getChave(no.getNumChaves() - 1);
    }

    private String getSucessor(NoB<String> no) {
        while (!no.getIsFolha()) {
            no = no.getFilho(0);
        }
        return no.getChave(0);
    }

    private void preencher(NoB<String> no, int indice) {
        if (indice != 0 && no.getFilho(indice - 1).getNumChaves() >= t) {
            emprestarDoAnterior(no, indice);
        }
        else if (indice != no.getNumChaves() &&
                no.getFilho(indice + 1).getNumChaves() >= t) {
            emprestarDoProximo(no, indice);
        }
        else {
            if (indice != no.getNumChaves()) {
                merge(no, indice);
            } 
            else {
                merge(no, indice - 1);
            }
        }
    }

    private void emprestarDoAnterior(NoB<String> no, int indice) {

        NoB<String> filho = no.getFilho(indice);
        NoB<String> irmao = no.getFilho(indice - 1);

        int nFilho = filho.getNumChaves();
        int nIrmao = irmao.getNumChaves();

        for (int i = nFilho - 1; i >= 0; i--) {
            filho.setChave(i + 1, filho.getChave(i));
        }

        if (!filho.getIsFolha()) {
            for (int i = nFilho; i >= 0; i--) {
                filho.setFilho(i + 1, filho.getFilho(i));
            }
        }

        filho.setChave(0, no.getChave(indice - 1));

        if (!filho.getIsFolha()) {
            filho.setFilho(0, irmao.getFilho(nIrmao));
        }

        no.setChave(indice - 1, irmao.getChave(nIrmao - 1));

        irmao.setChave(nIrmao - 1, null);
        if (!irmao.getIsFolha()) {
            irmao.setFilho(nIrmao, null);
        }

        irmao.decreaseNumChaves();
        filho.increaseNumChaves();
    }

    private void emprestarDoProximo(NoB<String> no, int indice) {

        NoB<String> filho = no.getFilho(indice);
        NoB<String> irmao = no.getFilho(indice + 1);

        int nFilho = filho.getNumChaves();
        int nIrmao = irmao.getNumChaves();

        filho.setChave(nFilho, no.getChave(indice));

        if (!filho.getIsFolha()) {
            filho.setFilho(nFilho + 1, irmao.getFilho(0));
        }

        no.setChave(indice, irmao.getChave(0));

        for (int i = 1; i < nIrmao; i++) {
            irmao.setChave(i - 1, irmao.getChave(i));
        }

        if (!irmao.getIsFolha()) {
            for (int i = 1; i <= nIrmao; i++) {
                irmao.setFilho(i - 1, irmao.getFilho(i));
            }
        }

        irmao.setChave(nIrmao - 1, null);
        if (!irmao.getIsFolha()) {
            irmao.setFilho(nIrmao, null);
        }

        irmao.decreaseNumChaves();
        filho.increaseNumChaves();
    }

    private void merge(NoB<String> no, int indice) {

        NoB<String> filho = no.getFilho(indice);
        NoB<String> irmao = no.getFilho(indice + 1);

        int nFilho = filho.getNumChaves();
        int nIrmao = irmao.getNumChaves();

        filho.setChave(nFilho, no.getChave(indice));

        for (int i = 0; i < nIrmao; i++) {
            filho.setChave(nFilho + 1 + i, irmao.getChave(i));
        }

        if (!filho.getIsFolha()) {
            for (int i = 0; i <= nIrmao; i++) {
                filho.setFilho(nFilho + 1 + i, irmao.getFilho(i));
            }
        }

        for (int i = 0; i < nIrmao + 1; i++) {
            filho.increaseNumChaves();
        }

        for (int i = indice + 1; i < no.getNumChaves(); i++) {
            no.setChave(i - 1, no.getChave(i));
        }

        for (int i = indice + 2; i <= no.getNumChaves(); i++) {
            no.setFilho(i - 1, no.getFilho(i));
        }

        no.setChave(no.getNumChaves() - 1, null);
        no.setFilho(no.getNumChaves(), null);

        no.decreaseNumChaves();
    }

    public void desenharArvore() {
        int h = altura(this.raiz);
        int largura = (int) Math.pow(2, h + 2);

        String[][] mat = new String[h + 1][largura];

        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                mat[i][j] = "     ";
            }
        }

        preencherMatriz(this.raiz, mat, 0, 0, largura - 1);

        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }

    private void preencherMatriz(NoB<String> no, String[][] mat, int linha, int esq, int dir) {
        if (no == null) return;

        int meio = (esq + dir) / 2;

        mat[linha][meio] = noToString(no);

        if (no.getIsFolha()){
            return;
        }

        int numFilhos = no.getNumChaves() + 1;

        int intervalo = (dir - esq) / numFilhos;

        for (int i = 0; i < numFilhos; i++) {
            int novoEsq = esq + i * intervalo;
            int novoDir = (i == numFilhos - 1) ? dir : (novoEsq + intervalo - 1);

            preencherMatriz(no.getFilho(i), mat, linha + 1, novoEsq, novoDir);
        }
    }
    
    private String noToString(NoB<String> no) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < no.getNumChaves(); i++) {
            sb.append(no.getChave(i));
            if (i < no.getNumChaves() - 1) {
                sb.append("|");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}
 