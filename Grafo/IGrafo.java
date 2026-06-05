package Grafo;

import java.util.Collection;

public interface IGrafo<TV, TA> {

    // MÉTODOS DE ACESSO

    Vertice<TV>[] finalVertices(Aresta<TA> e);

    Vertice<TV> oposto(Vertice<TV> v, Aresta<TA> e);

    boolean ehAdjacente(Vertice<TV> v, Vertice<TV> w);

    void substituir(Vertice<TV> v, TV x);

    void substituir(Aresta<TA> e, TA x);

    // MÉTODOS DE ATUALIZAÇÃO

    Vertice<TV> inserirVertice(TV o);

    Aresta<TA> inserirAresta(Vertice<TV> v, Vertice<TV> w, TA o);

    TV removerVertice(Vertice<TV> v);

    TA removerAresta(Aresta<TA> e);

    // ITERADORES

    Collection<Aresta<TA>> arestasIncidentes(Vertice<TV> v);

    Collection<Vertice<TV>> vertices();

    Collection<Aresta<TA>> arestas();

    // GRAFOS DIRECIONADOS

    boolean ehDirecionada(Aresta<TA> e);

    Aresta<TA> inserirArestaDirecionada(Vertice<TV> origem, Vertice<TV> destino, TA elemento);

}