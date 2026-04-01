import java.util.Iterator;

public interface ArvoreBPInterface {

    int size();

    int altura();

    boolean isEmpty();

    Iterator elements();

    Iterator nos();

    No raiz();

    No pai();

    Iterator filhos();

    boolean ehInterno();

    boolean ehExterno();

    boolean ehRaiz();

    int profundidade(No no);

    int replace(No no, int value);
    
}