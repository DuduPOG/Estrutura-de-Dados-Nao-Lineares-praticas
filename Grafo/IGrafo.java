package Grafo;

import java.util.Collection;
import java.util.List;

public interface IGrafo<TV, TA> {

    // MÉTODOS DE ACESSO

    List<Vertice<TV, TA>> finalVertices(Aresta<TV, TA> e);

    Vertice<TV, TA> oposto(Vertice<TV, TA> v, Aresta<TV, TA> e) throws RuntimeException;

    boolean ehAdjacente(Vertice<TV, TA> v, Vertice<TV, TA> w);

    void substituir(Vertice<TV, TA> v, TV x);

    void substituir(Aresta<TV, TA> e, TA x);

    // MÉTODOS DE ATUALIZAÇÃO

    Vertice<TV, TA> inserirVertice(TV o);

    Aresta<TV, TA> inserirAresta(Vertice<TV, TA> v, Vertice<TV, TA> w, TA o);

    TV removerVertice(Vertice<TV, TA> v);

    TA removerAresta(Aresta<TV, TA> e);

    // ITERADORES

    List<Aresta<TV, TA>> arestasIncidentes(Vertice<TV, TA> v);

    Collection<Vertice<TV, TA>> vertices();

    Collection<Aresta<TV, TA>> arestas();

    // GRAFOS DIRECIONADOS

    boolean ehDirecionada(Aresta<TV, TA> e);

    Aresta<TV, TA> inserirArestaDirecionada(Vertice<TV, TA> origem, Vertice<TV, TA> destino, TA elemento);

}