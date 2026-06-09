package Grafo;

public class Aresta<TV, TA> {
    
    private TA elemento;

    private final Vertice<TV, TA> origem;
    private final Vertice<TV, TA> destino;

    private final boolean direcionada;

    public Aresta(Vertice<TV, TA> origem, Vertice<TV, TA> destino, TA elemento, boolean direcionada) {
        this.origem = origem;
        this.destino = destino;
        this.elemento = elemento;
        this.direcionada = direcionada;
    }

    public TA getElemento() {
        return this.elemento;
    }

    public void setElemento(TA elemento) {
        this.elemento = elemento;
    }

    public Vertice<TV, TA> getOrigem() {
        return this.origem;
    }

    public Vertice<TV, TA> getDestino() {
        return this.destino;
    }

    public boolean ehDirecionada() {
        return this.direcionada;
    }
}
