package Grafo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Grafo<TV, TA> implements IGrafo<TV, TA> {
    
    private final List<Vertice<TV, TA>> vertices;
    private final List<Aresta<TV, TA>> arestas;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
    }

    @Override
    public List<Vertice<TV, TA>> finalVertices(Aresta<TV, TA> e) {

        /*
         * PSEUDOCÓDIGO
         *
         * criar vetor de tamanho 2
         * vetor[0] = origem
         * vetor[1] = destino
         * retornar vetor
         */
        List<Vertice<TV, TA>> verticesAresta = new ArrayList<>();
        verticesAresta.add(e.getOrigem());
        verticesAresta.add(e.getDestino());
        return verticesAresta;

    }

    @Override
    public Vertice<TV, TA> oposto(Vertice<TV, TA> v, Aresta<TV, TA> e) throws RuntimeException{

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
        if (v == e.getOrigem()){
           return e.getDestino();
        }
        else if (v == e.getDestino()){
           return e.getOrigem();
        }
        else {
           throw new RuntimeException("O vértice v não tem aresta incidente");
        }
    }

    @Override
    public boolean ehAdjacente(Vertice<TV, TA> v, Vertice<TV, TA> w) {

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
        for (Aresta<TV, TA> aresta : v.getArestasIncidentes()) {
            if ((aresta.getOrigem() == v && aresta.getDestino() == w) ||
                (aresta.getOrigem() == w && aresta.getDestino() == v)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void substituir(Vertice<TV, TA> v, TV x) {

        /*
         * PSEUDOCÓDIGO
         *
         * v.setElemento(x)
         */
        v.setElemento(x);
    }

    @Override
    public void substituir(Aresta<TV, TA> e, TA x) {

        /*
         * PSEUDOCÓDIGO
         *
         * e.setElemento(x)
         */
        e.setElemento(x);
    }

    @Override
    public Vertice<TV, TA> inserirVertice(TV o) {

        /*
         * PSEUDOCÓDIGO
         *
         * criar novo vértice
         * adicionar na lista de vértices
         * retornar vértice criado
         */
        Vertice<TV, TA> novoVertice = new Vertice<>(o);
        this.vertices.add(novoVertice);
        return novoVertice;
    }

    @Override
    public Aresta<TV, TA> inserirAresta(Vertice<TV, TA> v, Vertice<TV, TA> w, TA o) {

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
        Aresta<TV, TA> novaAresta = new Aresta<>(v, w, o, false);
        this.arestas.add(novaAresta);
        v.getArestasIncidentes().add(novaAresta);
        w.getArestasIncidentes().add(novaAresta);
        return novaAresta;
    }

    @Override
    public TV removerVertice(Vertice<TV, TA> v) {

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
        TV verticeRemovido = v.getElemento();
        List<Aresta<TV, TA>> arestasIncidentes = new ArrayList<>(v.getArestasIncidentes());
        for (Aresta<TV, TA> aresta : arestasIncidentes){
            removerAresta(aresta);
        }
        this.vertices.remove(v);
        return verticeRemovido;
    }

    @Override
    public TA removerAresta(Aresta<TV, TA> e) {

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
        TA arestaRemovida = e.getElemento();
        this.arestas.remove(e);
        e.getOrigem().getArestasIncidentes().remove(e);
        e.getDestino().getArestasIncidentes().remove(e);
        return arestaRemovida;
    }

    @Override
    public List<Aresta<TV, TA>> arestasIncidentes(Vertice<TV, TA> v) {

        /*
         * PSEUDOCÓDIGO
         *
         * retornar coleção de arestas
         * associadas ao vértice
         */

        return v.getArestasIncidentes();
    }

    @Override
    public Collection<Vertice<TV, TA>> vertices() {

        /*
         * PSEUDOCÓDIGO
         *
         * retornar lista de vértices
         */
        return this.vertices;
    }

    @Override
    public Collection<Aresta<TV, TA>> arestas() {

        /*
         * PSEUDOCÓDIGO
         *
         * retornar lista de arestas
         */
        return this.arestas;
    }

    @Override
    public boolean ehDirecionada(Aresta<TV, TA> e) {

        /*
         * PSEUDOCÓDIGO
         *
         * retornar atributo direcionada
         */
        return e.ehDirecionada();
    }

    @Override
    public Aresta<TV, TA> inserirArestaDirecionada(Vertice<TV, TA> origem, Vertice<TV, TA> destino, TA elemento) {

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
        Aresta<TV, TA> novaAresta = new Aresta<>(origem, destino, elemento, true);
        this.arestas.add(novaAresta);
        novaAresta.getOrigem().getArestasIncidentes().add(novaAresta);
        novaAresta.getDestino().getArestasIncidentes().add(novaAresta);
        return novaAresta;
    }
}
