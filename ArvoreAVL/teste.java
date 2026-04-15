public class teste {
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
        //teste.remover(3);
        //teste.remover(8);
        teste.desenharArvore();
    }
}
