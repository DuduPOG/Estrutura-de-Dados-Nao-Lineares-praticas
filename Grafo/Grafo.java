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
        List<Vertice<TV, TA>> verticesAresta = new ArrayList<>();
        verticesAresta.add(e.getOrigem());
        verticesAresta.add(e.getDestino());
        return verticesAresta;

    }

    @Override
    public Vertice<TV, TA> oposto(Vertice<TV, TA> v, Aresta<TV, TA> e) throws RuntimeException{
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
        v.setElemento(x);
    }

    @Override
    public void substituir(Aresta<TV, TA> e, TA x) {
        e.setElemento(x);
    }

    @Override
    public Vertice<TV, TA> inserirVertice(TV o) {
        Vertice<TV, TA> novoVertice = new Vertice<>(o);
        this.vertices.add(novoVertice);
        return novoVertice;
    }

    @Override
    public Aresta<TV, TA> inserirAresta(Vertice<TV, TA> v, Vertice<TV, TA> w, TA o) {
        Aresta<TV, TA> novaAresta = new Aresta<>(v, w, o, false);
        this.arestas.add(novaAresta);
        v.getArestasIncidentes().add(novaAresta);
        w.getArestasIncidentes().add(novaAresta);
        return novaAresta;
    }

    @Override
    public TV removerVertice(Vertice<TV, TA> v) {
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
        TA arestaRemovida = e.getElemento();
        this.arestas.remove(e);
        e.getOrigem().getArestasIncidentes().remove(e);
        e.getDestino().getArestasIncidentes().remove(e);
        return arestaRemovida;
    }

    @Override
    public List<Aresta<TV, TA>> arestasIncidentes(Vertice<TV, TA> v) {
        return v.getArestasIncidentes();
    }

    @Override
    public Collection<Vertice<TV, TA>> vertices() {
        return this.vertices;
    }

    @Override
    public Collection<Aresta<TV, TA>> arestas() {
        return this.arestas;
    }

    @Override
    public boolean ehDirecionada(Aresta<TV, TA> e) {
        return e.ehDirecionada();
    }

    @Override
    public Aresta<TV, TA> inserirArestaDirecionada(Vertice<TV, TA> origem, Vertice<TV, TA> destino, TA elemento) {
        Aresta<TV, TA> novaAresta = new Aresta<>(origem, destino, elemento, true);
        this.arestas.add(novaAresta);
        novaAresta.getOrigem().getArestasIncidentes().add(novaAresta);
        novaAresta.getDestino().getArestasIncidentes().add(novaAresta);
        return novaAresta;
    }
}
