package Grafo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Grafo<TV, TA> implements IGrafo<TV, TA> {
    
    private final List<Vertice<TV>> vertices;
    private final List<Aresta<TA>> arestas;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    @Override
    public Vertice<TV>[] finalVertices(Aresta<TA> e) {

        /*
         * PSEUDOCÓDIGO
         *
         * criar vetor de tamanho 2
         * vetor[0] = origem
         * vetor[1] = destino
         * retornar vetor
         */

        return null;
    }

    @Override
    public Vertice<TV> oposto(
            Vertice<TV> v,
            Aresta<TA> e) {

        /*
         * PSEUDOCÓDIGO
         *
         * se v == origem
         *      retornar destino
         *
         * senão se v == destino
         *      retornar origem
         *
         * senão
         *      lançar exceção
         */

        return null;
    }

    @Override
    public boolean ehAdjacente(
            Vertice<TV> v,
            Vertice<TV> w) {

        /*
         * PSEUDOCÓDIGO
         *
         * percorrer arestas incidentes de v
         *
         * para cada aresta
         *      se conecta v e w
         *          retornar true
         *
         * retornar false
         */

        return false;
    }

    @Override
    public void substituir(
            Vertice<TV> v,
            TV x) {

        /*
         * PSEUDOCÓDIGO
         *
         * v.setElemento(x)
         */
    }

    @Override
    public void substituir(
            Aresta<TA> e,
            TA x) {

        /*
         * PSEUDOCÓDIGO
         *
         * e.setElemento(x)
         */
    }

    @Override
    public Vertice<TV> inserirVertice(TV o) {

        /*
         * PSEUDOCÓDIGO
         *
         * criar novo vértice
         * adicionar na lista de vértices
         * retornar vértice criado
         */

        return null;
    }

    @Override
    public Aresta<TA> inserirAresta(
            Vertice<TV> v,
            Vertice<TV> w,
            TA o) {

        /*
         * PSEUDOCÓDIGO
         *
         * criar nova aresta não direcionada
         *
         * adicionar na lista global de arestas
         *
         * adicionar nas listas de incidência
         * de v e de w
         *
         * retornar aresta criada
         */

        return null;
    }

    @Override
    public TV removerVertice(
            Vertice<TV> v) {

        /*
         * PSEUDOCÓDIGO
         *
         * obter todas as arestas incidentes
         *
         * para cada aresta
         *      removerAresta(aresta)
         *
         * remover vértice da coleção
         *
         * retornar elemento armazenado
         */

        return null;
    }

    @Override
    public TA removerAresta(
            Aresta<TA> e) {

        /*
         * PSEUDOCÓDIGO
         *
         * remover da lista global
         *
         * remover da lista de incidência
         * do vértice origem
         *
         * remover da lista de incidência
         * do vértice destino
         *
         * retornar elemento armazenado
         */

        return null;
    }

    @Override
    public Collection<Aresta<TA>> arestasIncidentes(
            Vertice<TV> v) {

        /*
         * PSEUDOCÓDIGO
         *
         * retornar coleção de arestas
         * associadas ao vértice
         */

        return null;
    }

    @Override
    public Collection<Vertice<TV>> vertices() {

        /*
         * PSEUDOCÓDIGO
         *
         * retornar lista de vértices
         */

        return null;
    }

    @Override
    public Collection<Aresta<TA>> arestas() {

        /*
         * PSEUDOCÓDIGO
         *
         * retornar lista de arestas
         */

        return null;
    }

    @Override
    public boolean ehDirecionada(
            Aresta<TA> e) {

        /*
         * PSEUDOCÓDIGO
         *
         * retornar atributo direcionada
         */

        return false;
    }

    @Override
    public Aresta<TA> inserirArestaDirecionada(
            Vertice<TV> origem,
            Vertice<TV> destino,
            TA elemento) {

        /*
         * PSEUDOCÓDIGO
         *
         * criar aresta direcionada
         *
         * adicionar à lista global
         *
         * adicionar à lista de incidência
         * da origem e destino
         *
         * retornar aresta criada
         */

        return null;
    }
}
