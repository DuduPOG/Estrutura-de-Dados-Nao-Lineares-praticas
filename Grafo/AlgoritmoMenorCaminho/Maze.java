package Grafo.AlgoritmoMenorCaminho;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o labirinto lido a partir de um arquivo .dat.
 *
 * Convenção das células (conforme especificação do trabalho):
 *   0 - caminho livre
 *   1 - parede
 *   2 - ponto de partida
 *   3 - saída do labirinto (pode haver mais de uma saída)
 *
 * O arquivo é lido linha a linha; cada linha vira uma linha da matriz,
 * e cada caractere da linha (0,1,2,3) vira uma coluna. Não há separador
 * entre os caracteres (igual ao exemplo do enunciado: "1111111111").
 */
public class Maze {

    public static final int LIVRE = 0;
    public static final int PAREDE = 1;
    public static final int PARTIDA = 2;
    public static final int SAIDA = 3;

    private final int[][] grid;
    private final int rows;
    private final int cols;
    private final Cell start;
    private final List<Cell> exits;

    public Maze(String filePath) throws IOException {
        List<int[]> linhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linha;
            int maxCols = 0;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;
                int[] valores = new int[linha.length()];
                for (int i = 0; i < linha.length(); i++) {
                    char c = linha.charAt(i);
                    if (!Character.isDigit(c)) {
                        throw new IllegalArgumentException(
                                "Caractere inválido no arquivo .dat: '" + c + "'");
                    }
                    valores[i] = c - '0';
                }
                maxCols = Math.max(maxCols, valores.length);
                linhas.add(valores);
            }
            this.cols = maxCols;
        }

        this.rows = linhas.size();
        if (this.rows == 0) {
            throw new IllegalArgumentException("Arquivo .dat vazio ou inválido.");
        }

        this.grid = new int[rows][cols];
        Cell startTmp = null;
        List<Cell> exitsTmp = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            int[] linhaValores = linhas.get(r);
            for (int c = 0; c < cols; c++) {
                int valor = (c < linhaValores.length) ? linhaValores[c] : PAREDE;
                grid[r][c] = valor;
                if (valor == PARTIDA) {
                    if (startTmp != null) {
                        throw new IllegalArgumentException(
                                "Mais de um ponto de partida (2) encontrado no labirinto.");
                    }
                    startTmp = new Cell(r, c);
                } else if (valor == SAIDA) {
                    exitsTmp.add(new Cell(r, c));
                }
            }
        }

        if (startTmp == null) {
            throw new IllegalArgumentException("Nenhum ponto de partida (2) encontrado.");
        }
        if (exitsTmp.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma saída (3) encontrada.");
        }

        this.start = startTmp;
        this.exits = exitsTmp;
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public Cell getStart() { return start; }
    public List<Cell> getExits() { return exits; }

    public boolean isWalkable(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return false;
        return grid[row][col] != PAREDE;
    }

    public boolean isExit(Cell cell) {
        return grid[cell.getRow()][cell.getCol()] == SAIDA;
    }

    public int valueAt(int row, int col) {
        return grid[row][col];
    }

    /**
     * Imprime o labirinto, marcando opcionalmente um caminho com '*'.
     */
    public void printWithPath(List<Cell> path) {
        boolean[][] inPath = new boolean[rows][cols];
        if (path != null) {
            for (Cell c : path) inPath[c.getRow()][c.getCol()] = true;
        }
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int v = grid[r][c];
                char ch;
                if (inPath[r][c] && v != PARTIDA && v != SAIDA) {
                    ch = '*';
                } else {
                    switch (v) {
                        case PAREDE: ch = '#'; break;
                        case PARTIDA: ch = 'P'; break;
                        case SAIDA: ch = 'S'; break;
                        default: ch = '.'; break;
                    }
                }
                sb.append(ch);
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
