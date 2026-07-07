package Grafo.AlgoritmoMenorCaminho;

import java.util.Objects;

/**
 * Representa uma célula (posição) da matriz do labirinto.
 * Imutável, com equals/hashCode sobrescritos para uso correto em
 * HashSet/HashMap (visitados, distâncias, predecessores).
 */
public final class Celula {
    private final int linha;
    private final int coluna;

    public Celula(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() { return linha; }
    public int getColuna() { return coluna; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Celula)) return false;
        Celula celula = (Celula) o;
        return linha == celula.linha && coluna == celula.coluna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(linha, coluna);
    }

    @Override
    public String toString() {
        return "(" + linha + "," + coluna + ")";
    }
}
