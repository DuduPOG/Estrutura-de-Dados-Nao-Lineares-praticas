import java.util.Iterator;

public interface ArvoreBPInterface {

    int size();

    int altura();

    Iterator elements();

    Iterator nos();

    NoBP raiz();

    NoBP pai();

    NoBP buscar(NoBP no) throws NoInexistente;

    void inserir(int value);

    void remover(int value);

    boolean ehInterno(NoBP no);

    boolean ehExterno(NoBP no);

    boolean ehRaiz();

    int profundidade(NoBP no);

    int replace(NoBP no, int value);
    
}