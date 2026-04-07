import java.util.Iterator;

public interface ArvoreBPInterface {

    int size();

    int altura();

    Iterator elements();

    Iterator nos();

    No raiz();

    No pai();

    No buscar(No no) throws NoInexistente;

    void inserir(int value);

    void remover(int value);

    boolean ehInterno(No no);

    boolean ehExterno(No no);

    boolean ehRaiz();

    int profundidade(No no);

    int replace(No no, int value);
    
}