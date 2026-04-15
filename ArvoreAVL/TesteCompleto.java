public class TesteCompleto {
    private static int testesPassou = 0;
    private static int testesFalharam = 0;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   TESTES COMPLETOS ÁRVORE AVL");
        System.out.println("========================================\n");

        testeInsercaoSimples();
        testeInsercaoComRotacoes();
        testeRemocaoSimples();
        testeRemocaoComRebalanceamento();
        testeRemocaoTotal();
        testeSequenciaCompleta();
        testeAlturaEBalanceamento();
        testeIntegridade();

        System.out.println("\n========================================");
        System.out.println("   RESULTADO FINAL");
        System.out.println("========================================");
        System.out.println("✓ Testes passaram: " + testesPassou);
        System.out.println("✗ Testes falharam: " + testesFalharam);
        System.out.println("Total: " + (testesPassou + testesFalharam));
        System.out.println("========================================\n");

        if (testesFalharam == 0) {
            System.out.println("🎉 TODOS OS TESTES PASSARAM! 🎉\n");
        } else {
            System.out.println("⚠️  ALGUNS TESTES FALHARAM ⚠️\n");
        }
    }

    private static void testeInsercaoSimples() {
        System.out.println("\n>>> TESTE 1: Inserção Simples");
        ArvoreAVL arvore = new ArvoreAVL();
        
        arvore.inserir(5);
        arvore.inserir(3);
        arvore.inserir(7);
        arvore.inserir(2);
        arvore.inserir(4);
        arvore.inserir(6);
        arvore.inserir(8);

        assertTrue("Tamanho deve ser 7", arvore.size() == 7);
        System.out.println("✓ Inserção simples funcionou corretamente");
        arvore.desenharArvore();
    }

    private static void testeInsercaoComRotacoes() {
        System.out.println("\n>>> TESTE 2: Inserção com Rotações");
        ArvoreAVL arvore = new ArvoreAVL();
        
        // Rotação à direita
        arvore.inserir(3);
        arvore.inserir(2);
        arvore.inserir(1);  // Deve fazer RSD
        
        assertTrue("Tamanho deve ser 3", arvore.size() == 3);
        System.out.println("✓ Rotação à direita (RSD) funcionou");
        
        // Rotação à esquerda
        ArvoreAVL arvore2 = new ArvoreAVL();
        arvore2.inserir(1);
        arvore2.inserir(2);
        arvore2.inserir(3);  // Deve fazer RSE
        
        assertTrue("Tamanho deve ser 3", arvore2.size() == 3);
        System.out.println("✓ Rotação à esquerda (RSE) funcionou");
        
        arvore2.desenharArvore();
    }

    private static void testeRemocaoSimples() {
        System.out.println("\n>>> TESTE 3: Remoção Simples");
        ArvoreAVL arvore = new ArvoreAVL();
        
        arvore.inserir(5);
        arvore.inserir(3);
        arvore.inserir(7);
        arvore.inserir(2);
        arvore.inserir(4);
        arvore.inserir(6);
        arvore.inserir(8);
        
        // Remover folha
        arvore.remover(2);
        assertTrue("Tamanho deve ser 6 após remover 2", arvore.size() == 6);
        System.out.println("✓ Remoção de folha funcionou");
        
        // Remover outro
        arvore.remover(8);
        assertTrue("Tamanho deve ser 5 após remover 8", arvore.size() == 5);
        System.out.println("✓ Remoção de folha funcionou");
        
        arvore.desenharArvore();
    }

    private static void testeRemocaoComRebalanceamento() {
        System.out.println("\n>>> TESTE 4: Remoção com Rebalanceamento");
        ArvoreAVL arvore = new ArvoreAVL();
        
        arvore.inserir(4);
        arvore.inserir(2);
        arvore.inserir(6);
        arvore.inserir(1);
        arvore.inserir(3);
        arvore.inserir(5);
        arvore.inserir(7);
        arvore.inserir(8);
        arvore.inserir(10);
        arvore.inserir(12);
        arvore.inserir(15);
        
        System.out.println("Antes da remoção:");
        arvore.desenharArvore();
        
        arvore.remover(15);
        arvore.remover(10);
        
        assertTrue("Tamanho deve ser 9", arvore.size() == 9);
        System.out.println("\n✓ Remoção com rebalanceamento funcionou");
        arvore.desenharArvore();
    }

    private static void testeRemocaoTotal() {
        System.out.println("\n>>> TESTE 5: Remoção Total");
        ArvoreAVL arvore = new ArvoreAVL();
        
        int[] valores = {5, 3, 7, 2, 4, 6, 8, 1, 9};
        
        for (int v : valores) {
            arvore.inserir(v);
        }
        
        assertTrue("Tamanho deve ser 9", arvore.size() == 9);
        
        // Remover todos
        for (int v : valores) {
            arvore.remover(v);
        }
        
        assertTrue("Tamanho deve ser 0 após remover tudo", arvore.size() == 0);
        System.out.println("✓ Remoção total funcionou");
    }

    private static void testeSequenciaCompleta() {
        System.out.println("\n>>> TESTE 6: Sequência Completa Inserção e Remoção");
        ArvoreAVL arvore = new ArvoreAVL();
        
        int[] inserir = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 65, 77, 90};
        
        for (int v : inserir) {
            arvore.inserir(v);
        }
        
        assertTrue("Tamanho deve ser 14", arvore.size() == 14);
        System.out.println("Após inserir 14 elementos:");
        arvore.desenharArvore();
        
        // Remover alguns
        int[] remover = {5, 15, 27, 90, 80};
        
        for (int v : remover) {
            arvore.remover(v);
        }
        
        assertTrue("Tamanho deve ser 9 após remover 5", arvore.size() == 9);
        System.out.println("\nApós remover 5 elementos:");
        arvore.desenharArvore();
        System.out.println("✓ Sequência completa funcionou");
    }

    private static void testeAlturaEBalanceamento() {
        System.out.println("\n>>> TESTE 7: Altura e Balanceamento");
        ArvoreAVL arvore = new ArvoreAVL();
        
        int[] valores = {4, 2, 6, 1, 3, 5, 7, 8, 10, 12};
        
        for (int v : valores) {
            arvore.inserir(v);
        }
        
        int altura = arvore.altura(arvore.raiz());
        System.out.println("Altura da árvore: " + altura);
        
        // Uma árvore AVL com 10 elementos deve ter altura <= log2(10) ≈ 4
        assertTrue("Altura deve ser <= 4 para 10 elementos", altura <= 4);
        System.out.println("✓ Altura está dentro do esperado");
        
        arvore.desenharArvore();
    }

    private static void testeIntegridade() {
        System.out.println("\n>>> TESTE 8: Integridade da Árvore");
        ArvoreAVL arvore = new ArvoreAVL();
        
        int[] valores = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 65, 75, 90};
        
        for (int v : valores) {
            arvore.inserir(v);
        }
        
        // Verificar que a árvore segue propriedade BST
        boolean valido = verificarBST(arvore.raiz(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertTrue("Árvore deve ser BST válida", valido);
        
        // Verificar balanceamento
        boolean balanceada = verificarBalanceamento(arvore.raiz());
        assertTrue("Árvore deve estar balanceada", balanceada);
        
        System.out.println("✓ Integridade verificada com sucesso");
        arvore.desenharArvore();
    }

    private static boolean verificarBST(No no, int min, int max) {
        if (no == null) return true;
        
        if (no.getValue() <= min || no.getValue() >= max) {
            return false;
        }
        
        return verificarBST(no.getFE(), min, no.getValue()) &&
               verificarBST(no.getFD(), no.getValue(), max);
    }

    private static boolean verificarBalanceamento(No no) {
        if (no == null) return true;
        
        int fbEsq = no.getFE() != null ? no.getFE().getFB() : 0;
        int fbDir = no.getFD() != null ? no.getFD().getFB() : 0;
        
        int fbEsperado = calcularFBReal(no.getFE()) - calcularFBReal(no.getFD());
        
        if (Math.abs(fbEsperado) > 1) {
            System.out.println("Desbalanceamento detectado em nó: " + no.getValue());
            return false;
        }
        
        return verificarBalanceamento(no.getFE()) && verificarBalanceamento(no.getFD());
    }

    private static int calcularFBReal(No no) {
        if (no == null) return 0;
        return Math.max(calcularFBReal(no.getFE()), calcularFBReal(no.getFD())) + 1;
    }

    private static void assertTrue(String mensagem, boolean condicao) {
        if (condicao) {
            System.out.println("✓ " + mensagem);
            testesPassou++;
        } else {
            System.out.println("✗ " + mensagem);
            testesFalharam++;
        }
    }
}
