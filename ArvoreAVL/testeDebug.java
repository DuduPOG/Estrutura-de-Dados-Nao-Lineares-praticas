public class testeDebug {
    public static void main(String[] args) {
        ArvoreAVL teste = new ArvoreAVL();
        teste.inserir(4);
        teste.inserir(2);
        teste.inserir(6);
        teste.inserir(1);
        teste.inserir(3);
        teste.inserir(5);
        teste.inserir(7);
        teste.inserir(8);
        teste.inserir(10);
        teste.inserir(12);
        teste.inserir(15);
        
        System.out.println("=== Após inserir todos ===");
        teste.desenharArvore();
        
        teste.remover(15);
        System.out.println("\n=== Após remover 15 ===");
        teste.desenharArvore();
        
        teste.remover(10);
        System.out.println("\n=== Após remover 10 ===");
        teste.desenharArvore();
        
        teste.remover(8);
        System.out.println("\n=== Após remover 8 ===");
        teste.desenharArvore();
    }
}
