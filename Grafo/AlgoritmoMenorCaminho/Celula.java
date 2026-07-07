package Grafo.AlgoritmoMenorCaminho;

import java.util.Objects;

/**
 * Representa uma célula (posição) da matriz do labirinto.
 * Imutável, com equals/hashCode sobrescritos para uso correto em
 * HashSet/HashMap (visitados, distâncias, predecessores).
 */
public final class Celula {
    private final int row;
    private final int col;

    public Celula(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Celula)) return false;
        Celula cell = (Celula) o;
        return row == cell.row && col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
