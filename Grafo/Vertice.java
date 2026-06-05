package Grafo;

import java.util.ArrayList;
import java.util.List;

public class Vertice<T> {
    
    private T elemento;
    private final List<Aresta<?>> arestasIncidentes;

    public Vertice(T elemento) {
        this.elemento = elemento;
        this.arestasIncidentes = new ArrayList<>();
    }

    public T getElemento() {
        return this.elemento;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }

    public List<Aresta<?>> getArestasIncidentes() {
        return this.arestasIncidentes;
    }
}
