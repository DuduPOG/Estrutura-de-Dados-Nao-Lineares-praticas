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
        if (no == null) return -1;

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

    // ================= BUSCA =================

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

    // ================= INSERÇÃO =================

    public void inserir(String value) {

        if (this.raiz == null) {
            this.raiz = new NoB<>(t);
            this.raiz.setChave(0, value);
            this.raiz.increaseNumChaves();
            this.size++;
            return;
        }

        if (this.raiz.getNumChaves() == (2 * t - 1)) {

            NoB<String> novaRaiz = new NoB<>(t);
            novaRaiz.setFolha(false);
            novaRaiz.setFilho(0, this.raiz);

            NoB<String> cheio = this.raiz;
            NoB<String> novo = new NoB<>(t);
            novo.setFolha(cheio.getIsFolha());

            for (int j = 0; j < t - 1; j++) {
                novo.setChave(j, cheio.getChave(j + t));
            }

            if (!cheio.getIsFolha()) {
                for (int j = 0; j < t; j++) {
                    novo.setFilho(j, cheio.getFilho(j + t));
                }
            }

            String meio = cheio.getChave(t - 1);

            for (int j = t - 1; j < 2 * t - 1; j++) {
                cheio.setChave(j, null);
            }

            while (cheio.getNumChaves() > t - 1) {
                cheio.decreaseNumChaves();
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

                for (int j = 0; j < t - 1; j++) {
                    novo.setChave(j, filho.getChave(j + t));
                }

                if (!filho.getIsFolha()) {
                    for (int j = 0; j < t; j++) {
                        novo.setFilho(j, filho.getFilho(j + t));
                    }
                }

                String meio = filho.getChave(t - 1);

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

    public void remover(Comparable<String> value){

        if (this.raiz == null){
            return;
        }

        removerRec(this.raiz, value);

        // ajuste da raiz (caso fique vazia)
        if (raiz.getNumChaves() == 0) {
            if (this.raiz.getIsFolha()) {
                this.raiz = null;
            } else {
                this.raiz = this.raiz.getFilho(0);
            }
        }
    }

    private void removerRec(NoB<String> no, Comparable<String> key) {

        Comparable<String> chave = (Comparable<String>) key;

        int idx = 0;

        while (idx < no.getNumChaves() &&
            chave.compareTo(no.getChave(idx)) > 0) {
            idx++;
        }

        // CASO: chave encontrada
        if (idx < no.getNumChaves() &&
            chave.compareTo(no.getChave(idx)) == 0) {

            if (no.getIsFolha()) {
                removerDeFolha(no, idx);
            } else {
                removerDeInterno(no, idx);
            }

        } else {

            if (no.getIsFolha()) {
                return; // não existe
            }

            boolean ultimo = (idx == no.getNumChaves());

            // garante que filho[idx] tem >= t chaves
            if (no.getFilho(idx).getNumChaves() < t) {
                preencher(no, idx);
            }

            if (ultimo && idx > no.getNumChaves()) {
                removerRec(no.getFilho(idx - 1), key);
            } else {
                removerRec(no.getFilho(idx), key);
            }
        }
    }

    private void removerDeFolha(NoB<String> no, int idx) {
        for (int i = idx + 1; i < no.getNumChaves(); i++) {
            no.setChave(i - 1, no.getChave(i));
        }

        no.setChave(no.getNumChaves() - 1, null);
        no.decreaseNumChaves();
    }

    private void removerDeInterno(NoB<String> no, int idx) {

        String k = no.getChave(idx);

        NoB<String> filhoEsq = no.getFilho(idx);
        NoB<String> filhoDir = no.getFilho(idx + 1);

        // 2a: predecessor
        if (filhoEsq.getNumChaves() >= t) {

            String pred = getPredecessor(filhoEsq);
            no.setChave(idx, pred);
            removerRec(filhoEsq, pred);

        }
        // 2b: sucessor
        else if (filhoDir.getNumChaves() >= t) {

            String succ = getSucessor(filhoDir);
            no.setChave(idx, succ);
            removerRec(filhoDir, succ);

        }
        // 2c: merge
        else {

            merge(no, idx);
            removerRec(filhoEsq, k);
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

    private void preencher(NoB<String> no, int idx) {
        if (idx != 0 && no.getFilho(idx - 1).getNumChaves() >= t) {
            emprestarDoAnterior(no, idx);
        }
        else if (idx != no.getNumChaves() &&
                no.getFilho(idx + 1).getNumChaves() >= t) {
            emprestarDoProximo(no, idx);
        }
        else {
            if (idx != no.getNumChaves()) {
                merge(no, idx);
            } else {
                merge(no, idx - 1);
            }
        }
    }

    private void emprestarDoAnterior(NoB<String> no, int idx) {
        NoB<String> filho = no.getFilho(idx);
        NoB<String> irmao = no.getFilho(idx - 1);

        // desloca filho para direita
        for (int i = filho.getNumChaves() - 1; i >= 0; i--) {
            filho.setChave(i + 1, filho.getChave(i));
        }

        if (!filho.getIsFolha()) {
            for (int i = filho.getNumChaves(); i >= 0; i--) {
                filho.setFilho(i + 1, filho.getFilho(i));
            }
        }

        filho.setChave(0, no.getChave(idx - 1));

        if (!filho.getIsFolha()) {
            filho.setFilho(0, irmao.getFilho(irmao.getNumChaves()));
        }

        no.setChave(idx - 1, irmao.getChave(irmao.getNumChaves() - 1));

        irmao.setChave(irmao.getNumChaves() - 1, null);
        irmao.decreaseNumChaves();
        filho.increaseNumChaves();
    }

    private void emprestarDoProximo(NoB<String> no, int idx) {

        NoB<String> filho = no.getFilho(idx);
        NoB<String> irmao = no.getFilho(idx + 1);

        filho.setChave(filho.getNumChaves(), no.getChave(idx));

        if (!filho.getIsFolha()) {
            filho.setFilho(filho.getNumChaves() + 1, irmao.getFilho(0));
        }

        no.setChave(idx, irmao.getChave(0));

        // shift no irmão
        for (int i = 1; i < irmao.getNumChaves(); i++) {
            irmao.setChave(i - 1, irmao.getChave(i));
        }

        if (!irmao.getIsFolha()) {
            for (int i = 1; i <= irmao.getNumChaves(); i++) {
                irmao.setFilho(i - 1, irmao.getFilho(i));
            }
        }

        irmao.setChave(irmao.getNumChaves() - 1, null);
        irmao.decreaseNumChaves();
        filho.increaseNumChaves();
    }

    private void merge(NoB<String> no, int idx) {
        NoB<String> filho = no.getFilho(idx);
        NoB<String> irmao = no.getFilho(idx + 1);

        // puxa chave do pai
        filho.setChave(t - 1, no.getChave(idx));

        // copia chaves do irmão
        for (int i = 0; i < irmao.getNumChaves(); i++) {
            filho.setChave(i + t, irmao.getChave(i));
        }

        // copia filhos
        if (!filho.getIsFolha()) {
            for (int i = 0; i <= irmao.getNumChaves(); i++) {
                filho.setFilho(i + t, irmao.getFilho(i));
            }
        }

        // shift no pai
        for (int i = idx + 1; i < no.getNumChaves(); i++) {
            no.setChave(i - 1, no.getChave(i));
        }

        for (int i = idx + 2; i <= no.getNumChaves(); i++) {
            no.setFilho(i - 1, no.getFilho(i));
        }

        filho.increaseNumChaves(); // chave do meio

        for (int i = 0; i < irmao.getNumChaves(); i++) {
            filho.increaseNumChaves();
        }

        no.decreaseNumChaves();
    }

    public void desenharArvore(NoB<String> raiz) {
        int h = altura(this.raiz);
        int largura = (int) Math.pow(2, h + 2); // leve ajuste para caber nós maiores

        String[][] mat = new String[h + 1][largura];

        for (int i = 0; i <= h; i++) {
            for (int j = 0; j < largura; j++) {
                mat[i][j] = "     ";
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

    private void preencherMatriz(NoB<String> no, String[][] mat, int linha, int esq, int dir) {
        if (no == null) return;

        int meio = (esq + dir) / 2;

        // escreve o nó (com múltiplas chaves)
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
}