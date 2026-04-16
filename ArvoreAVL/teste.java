public class teste {
    public static void main(String[] args) {
        ArvoreAVL teste = new ArvoreAVL();
        teste.inserir(10);
        teste.inserir(5);
        teste.inserir(15);
        teste.inserir(2);
        teste.inserir(8);
        teste.inserir(22);
        teste.inserir(25);
        teste.remover(5);
        //System.out.println(teste.raiz());
        teste.desenharArvore();
    }
}
