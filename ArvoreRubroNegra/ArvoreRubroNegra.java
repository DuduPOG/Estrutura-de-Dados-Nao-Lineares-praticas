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

        NoRB y = alvo; // nó efetivamente removido
        NoRB.Cores corOriginal = y.getCor();

        NoRB x;        // substituto
        NoRB paiX;     // importante caso x seja null

        if (alvo.getFE() == null) {
            x = alvo.getFD();
            paiX = alvo.getPai();
            transplantar(alvo, alvo.getFD());
        }
        else if (alvo.getFD() == null) {
            x = alvo.getFE();
            paiX = alvo.getPai();
            transplantar(alvo, alvo.getFE());
        }
        else {
            y = minimo(alvo.getFD());
            corOriginal = y.getCor();

            x = y.getFD();

            if (y.getPai() == alvo) {
                paiX = y;
                if (x != null) {
                    x.setPai(y);
                }
            } else {
                paiX = y.getPai();
                transplantar(y, y.getFD());

                y.setFD(alvo.getFD());
                y.getFD().setPai(y);
            }

            transplantar(alvo, y);

            y.setFE(alvo.getFE());
            y.getFE().setPai(y);

            y.setCor(alvo.getCor());
        }

        this.size--;

        if (corOriginal == NoRB.Cores.P) {
            rebalancearRemocao(x, paiX);
        }

        if (this.raiz != null) {
            this.raiz.setCor(NoRB.Cores.P);
        }
    }

    private void rebalancearRemocao(NoRB x, NoRB pai) {

        while (x != this.raiz && cor(x) == NoRB.Cores.P) {

            if (pai == null) {
                break;
            }

            // x é filho esquerdo
            if (x == pai.getFE()) {
                NoRB w = pai.getFD();

                if (w == null) {
                    x = pai;
                    pai = x.getPai();
                    continue;
                }

                // CASO 1
                if (cor(w) == NoRB.Cores.V) {
                    w.setCor(NoRB.Cores.P);
                    pai.setCor(NoRB.Cores.V);
                    RSE(pai);
                    w = pai.getFD();
                }

                // CASO 2
                if (cor(w.getFE()) == NoRB.Cores.P &&
                    cor(w.getFD()) == NoRB.Cores.P) {

                    w.setCor(NoRB.Cores.V);
                    x = pai;
                    pai = x.getPai();
                }

                else {
                    // CASO 3
                    if (cor(w.getFD()) == NoRB.Cores.P) {
                        if (w.getFE() != null) {
                            w.getFE().setCor(NoRB.Cores.P);
                        }

                        w.setCor(NoRB.Cores.V);
                        RSD(w);
                        w = pai.getFD();
                    }

                    // CASO 4
                    w.setCor(pai.getCor());
                    pai.setCor(NoRB.Cores.P);

                    if (w.getFD() != null) {
                        w.getFD().setCor(NoRB.Cores.P);
                    }

                    RSE(pai);
                    x = this.raiz;
                }
            }

            // espelhado
            else {
                NoRB w = pai.getFE();

                if (w == null) {
                    x = pai;
                    pai = x.getPai();
                    continue;
                }

                // CASO 1
                if (cor(w) == NoRB.Cores.V) {
                    w.setCor(NoRB.Cores.P);
                    pai.setCor(NoRB.Cores.V);
                    RSD(pai);
                    w = pai.getFE();
                }

                // CASO 2
                if (cor(w.getFE()) == NoRB.Cores.P &&
                    cor(w.getFD()) == NoRB.Cores.P) {

                    w.setCor(NoRB.Cores.V);
                    x = pai;
                    pai = x.getPai();
                }

                else {
                    // CASO 3
                    if (cor(w.getFE()) == NoRB.Cores.P) {
                        if (w.getFD() != null) {
                            w.getFD().setCor(NoRB.Cores.P);
                        }

                        w.setCor(NoRB.Cores.V);
                        RSE(w);
                        w = pai.getFE();
                    }

                    // CASO 4
                    w.setCor(pai.getCor());
                    pai.setCor(NoRB.Cores.P);

                    if (w.getFE() != null) {
                        w.getFE().setCor(NoRB.Cores.P);
                    }

                    RSD(pai);
                    x = this.raiz;
                }
            }
        }

        if (x != null) {
            x.setCor(NoRB.Cores.P);
        }
    }

    private void transplantar(NoRB u, NoRB v) {
        if (u.getPai() == null) {
            this.raiz = v;
        }
        else if (u == u.getPai().getFE()) {
            u.getPai().setFE(v);
        }
        else {
            u.getPai().setFD(v);
        }

        if (v != null) {
            v.setPai(u.getPai());
        }
    }

    private NoRB minimo(NoRB no) {
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

            // pai está à esquerda do avô
            if (pai == avo.getFE()) {
                NoRB tio = avo.getFD();

                // CASO 1: tio vermelho
                if (cor(tio) == NoRB.Cores.V) {
                    pai.setCor(NoRB.Cores.P);
                    tio.setCor(NoRB.Cores.P);
                    avo.setCor(NoRB.Cores.V);
                    no = avo;
                }

                else {
                    // CASO 2: LR
                    if (no == pai.getFD()) {
                        no = pai;
                        RSE(no);
                        pai = no.getPai();
                        avo = pai.getPai();
                    }

                    // CASO 3: LL
                    pai.setCor(NoRB.Cores.P);
                    avo.setCor(NoRB.Cores.V);
                    RSD(avo);
                }
            }

            // casos espelhados
            else {
                NoRB tio = avo.getFE();

                // CASO 1
                if (cor(tio) == NoRB.Cores.V) {
                    pai.setCor(NoRB.Cores.P);
                    tio.setCor(NoRB.Cores.P);
                    avo.setCor(NoRB.Cores.V);
                    no = avo;
                }

                else {
                    // CASO 2: RL
                    if (no == pai.getFE()) {
                        no = pai;
                        RSD(no);
                        pai = no.getPai();
                        avo = pai.getPai();
                    }

                    // CASO 3: RR
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
        // Testes adaptados: remover na ordem inversa de inserção
        runCase("RSD", new int[]{30, 20, 10}, new int[]{20, 10, 30});
        runCase("RSE", new int[]{10, 20, 30}, new int[]{20, 30, 10});
        runCase("RDD", new int[]{30, 10, 20}, new int[]{10, 20, 30});
        runCase("RDE", new int[]{10, 30, 20}, new int[]{30, 20, 10});
        runCase("Misto", new int[]{41, 38, 31, 12, 19, 8, 50, 60, 55, 54}, new int[]{12, 60, 38, 54, 41, 8, 31, 55, 50, 19});
        runCase(
            "DoisFilhos",
            new int[]{50, 30, 70, 20, 40, 60, 80},
            new int[]{50, 70, 30, 60, 80, 20, 40}
        );

        runCase(
            "RaizRepetida",
            new int[]{10, 5, 15, 3, 7},
            new int[]{10, 7, 5, 15, 3}
        );

        System.out.println("Todos os testes de insercao e remocao passaram.");
    }

    private static void runCase(String nome, int[] insercoes, int[] remocoes) {
        ArvoreRubroNegra arvore = new ArvoreRubroNegra();
        System.out.println("\n===== Caso " + nome + " =====");
        int tamanhoEsperado = 0;

        for (int v : insercoes) {
            arvore.inserir(v);
            tamanhoEsperado++;
            System.out.println("\nInserindo: " + v);
            arvore.desenharArvore();
            validateOrThrow(arvore, nome + " apos inserir " + v);
            validateSizeOrThrow(arvore, tamanhoEsperado, nome + " apos inserir " + v);
        }

        for (int v : remocoes) {
            arvore.remover(v);
            tamanhoEsperado--;
            System.out.println("\nRemovendo: " + v);
            arvore.desenharArvore();
            validateOrThrow(arvore, nome + " apos remover " + v);
            validateSizeOrThrow(arvore, tamanhoEsperado, nome + " apos remover " + v);
            validateRemovedOrThrow(arvore, v, nome + " apos remover " + v);
        }

        if (!arvore.isEmpty()) {
            throw new IllegalStateException("Arvore deveria estar vazia ao fim de " + nome);
        }

        System.out.println("OK: " + nome);
    }

    private static void validateRemovedOrThrow(ArvoreRubroNegra arvore, int valor, String contexto) {
        if (arvore.buscar(arvore.raiz(), valor) != null) {
            throw new IllegalStateException("Elemento " + valor + " ainda encontrado em " + contexto);
        }
    }

    private static void validateSizeOrThrow(ArvoreRubroNegra arvore, int esperado, String contexto) {
        if (arvore.size() != esperado) {
            throw new IllegalStateException("Tamanho incorreto em " + contexto + ": esperado " + esperado + ", obtido " + arvore.size());
        }
    }

    private static void validateOrThrow(ArvoreRubroNegra arvore, String contexto) {
        NoRB raiz = arvore.raiz();

        if (raiz == null) {
            return;
        }

        if (!NoRB.Cores.P.equals(raiz.getCor())) {
            throw new IllegalStateException("Raiz nao-preta em " + contexto);
        }

        if (!isBST(raiz, null, null)) {
            throw new IllegalStateException("Violacao BST em " + contexto);
        }

        if (hasRedRedViolation(raiz)) {
            throw new IllegalStateException("Violacao vermelho-vermelho em " + contexto);
        }

        int blackHeight = blackHeightOrFail(raiz);

        if (blackHeight < 0) {
            throw new IllegalStateException("Altura negra inconsistente em " + contexto);
        }
    }

    private static boolean hasRedRedViolation(NoRB no) {
        if (no == null) {
            return false;
        }

        if (NoRB.Cores.V.equals(no.getCor())) {
            NoRB esq = no.getFE();
            NoRB dir = no.getFD();
            if ((esq != null && NoRB.Cores.V.equals(esq.getCor())) || (dir != null && NoRB.Cores.V.equals(dir.getCor()))) {
                return true;
            }
        }

        return hasRedRedViolation(no.getFE()) || hasRedRedViolation(no.getFD());
    }

    private static int blackHeightOrFail(NoRB no) {
        if (no == null) {
            return 1;
        }

        int esquerda = blackHeightOrFail(no.getFE());
        int direita = blackHeightOrFail(no.getFD());

        if (esquerda < 0 || direita < 0 || esquerda != direita) {
            return -1;
        }

        int atual = NoRB.Cores.P.equals(no.getCor()) ? 1 : 0;
        return esquerda + atual;
    }
}
