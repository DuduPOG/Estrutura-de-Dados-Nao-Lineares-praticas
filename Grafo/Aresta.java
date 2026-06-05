package Grafo;

public class Aresta<T> {
    
    private T elemento;

    private final Vertice<?> origem;
    private final Vertice<?> destino;

    private final boolean direcionada;

    public Aresta(Vertice<?> origem, Vertice<?> destino, T elemento, boolean direcionada) {
        this.origem = origem;
        this.destino = destino;
        this.elemento = elemento;
        this.direcionada = direcionada;
    }

    public T getElemento() {
        return this.elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public Vertice<?> getOrigem() {
        return this.origem;
    }

    public Vertice<?> getDestino() {
        return this.destino;
    }

    public boolean isDirecionada() {
        return this.direcionada;
    }
}
