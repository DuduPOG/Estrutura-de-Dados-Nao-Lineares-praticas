package Grafo.AlgoritmoMenorCaminho;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Algoritmo A* (A-estrela), derivado do Dijkstra conforme pedido no
 * enunciado: a única diferença estrutural em relação ao Dijkstra é o
 * critério de ordenação da fila de prioridade, que passa a usar
 * f(n) = g(n) + h(n) em vez de apenas g(n).
 *
 *   g(n) = custo acumulado real do início até n
 *   h(n) = heurística admissível: distância de Manhattan de n até a
 *          saída mais próxima (em linha reta, ignorando paredes)
 *
 * Como o labirinto pode ter múltiplas saídas, h(n) é calculada como o
 * MÍNIMO entre as distâncias de Manhattan até cada saída. Isso mantém
 * a heurística admissível (nunca superestima o custo real) e portanto
 * preserva a garantia de otimalidade do A*.
 */
public class AStarSolver {

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public PathResult solve(Maze maze) {
        long startTime = System.nanoTime();

        int rows = maze.getRows();
        int cols = maze.getCols();
        List<Cell> exits = maze.getExits();

        int[][] gScore = new int[rows][cols];
        for (int[] linha : gScore) java.util.Arrays.fill(linha, Integer.MAX_VALUE);

        Map<Cell, Cell> prev = new HashMap<>();
        boolean[][] fechado = new boolean[rows][cols];

        Cell start = maze.getStart();
        gScore[start.getRow()][start.getCol()] = 0;

        PriorityQueue<NodeF> aberto = new PriorityQueue<>();
        aberto.add(new NodeF(start, heuristica(start, exits)));

        int nodesExpanded = 0;
        Cell exitFound = null;

        while (!aberto.isEmpty()) {
            NodeF atual = aberto.poll();
            Cell c = atual.cell;

            if (fechado[c.getRow()][c.getCol()]) continue;
            fechado[c.getRow()][c.getCol()] = true;
            nodesExpanded++;

            if (maze.isExit(c)) {
                exitFound = c;
                break;
            }

            for (int dir = 0; dir < 4; dir++) {
                int nr = c.getRow() + DR[dir];
                int nc = c.getCol() + DC[dir];
                if (!maze.isWalkable(nr, nc)) continue;
                if (fechado[nr][nc]) continue;

                int tentativeG = gScore[c.getRow()][c.getCol()] + 1;
                if (tentativeG < gScore[nr][nc]) {
                    gScore[nr][nc] = tentativeG;
                    Cell vizinho = new Cell(nr, nc);
                    prev.put(vizinho, c);
                    int f = tentativeG + heuristica(vizinho, exits);
                    aberto.add(new NodeF(vizinho, f));
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;

        if (exitFound == null) {
            return new PathResult(Collections.emptyList(), -1, elapsed, nodesExpanded, null);
        }

        List<Cell> caminho = reconstruirCaminho(prev, start, exitFound);
        int custo = gScore[exitFound.getRow()][exitFound.getCol()];
        return new PathResult(caminho, custo, elapsed, nodesExpanded, exitFound);
    }

    /** Heurística: menor distância de Manhattan entre a célula e qualquer saída. */
    private int heuristica(Cell c, List<Cell> exits) {
        int min = Integer.MAX_VALUE;
        for (Cell saida : exits) {
            int d = Math.abs(c.getRow() - saida.getRow()) + Math.abs(c.getCol() - saida.getCol());
            if (d < min) min = d;
        }
        return min;
    }

    private List<Cell> reconstruirCaminho(Map<Cell, Cell> prev, Cell start, Cell fim) {
        ArrayDeque<Cell> pilha = new ArrayDeque<>();
        Cell atual = fim;
        pilha.push(atual);
        while (!atual.equals(start)) {
            atual = prev.get(atual);
            pilha.push(atual);
        }
        return new ArrayList<>(pilha);
    }

    /** Par (célula, f-score) ordenado por f = g + h — usado na fila de prioridade. */
    private static class NodeF implements Comparable<NodeF> {
        final Cell cell;
        final int f;

        NodeF(Cell cell, int f) {
            this.cell = cell;
            this.f = f;
        }

        @Override
        public int compareTo(NodeF o) {
            return Integer.compare(this.f, o.f);
        }
    }
}
