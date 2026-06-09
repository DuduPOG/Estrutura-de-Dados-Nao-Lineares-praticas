package Grafo;

import java.util.ArrayList;
import java.util.List;

public class Vertice<TV, TA> {
    
    private TV elemento;
    private final List<Aresta<TV, TA>> arestasIncidentes;

    public Vertice(TV elemento) {
        this.elemento = elemento;
        this.arestasIncidentes = new ArrayList<>();
    }

    public TV getElemento() {
        return this.elemento;
    }

    public void setElemento(TV elemento) {
        this.elemento = elemento;
    }

    public List<Aresta<TV, TA>> getArestasIncidentes() {
        return this.arestasIncidentes;
    }
}
