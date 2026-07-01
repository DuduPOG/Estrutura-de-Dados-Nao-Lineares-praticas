package Grafo.AlgoritmoMenorCaminho;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Algoritmo de Dijkstra aplicado a uma grade (grid) onde cada movimento
 * (cima/baixo/esquerda/direita) tem custo uniforme = 1.
 *
 * Como todas as arestas têm o mesmo peso, Dijkstra aqui se comporta de
 * forma equivalente a uma BFS, mas a implementação é mantida genérica
 * (com fila de prioridade por distância acumulada) para refletir
 * fielmente o algoritmo clássico e permitir comparação justa com A*
 * em termos de nós expandidos.
 *
 * Como o labirinto pode ter múltiplas saídas (valor 3), o algoritmo
 * para assim que a primeira saída é retirada da fila de prioridade
 * (garantia de menor custo, já que a fila é ordenada por distância).
 */
public class DijkstraSolver {

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public PathResult solve(Maze maze) {
        long startTime = System.nanoTime();

        int rows = maze.getRows();
        int cols = maze.getCols();
        int[][] dist = new int[rows][cols];
        for (int[] linha : dist) java.util.Arrays.fill(linha, Integer.MAX_VALUE);

        Map<Cell, Cell> prev = new HashMap<>();
        boolean[][] visitado = new boolean[rows][cols];

        Cell start = maze.getStart();
        dist[start.getRow()][start.getCol()] = 0;

        PriorityQueue<NodeDist> fila = new PriorityQueue<>();
        fila.add(new NodeDist(start, 0));

        int nodesExpanded = 0;
        Cell exitFound = null;

        while (!fila.isEmpty()) {
            NodeDist atual = fila.poll();
            Cell c = atual.cell;

            if (visitado[c.getRow()][c.getCol()]) continue;
            visitado[c.getRow()][c.getCol()] = true;
            nodesExpanded++;

            if (maze.isExit(c)) {
                exitFound = c;
                break;
            }

            for (int dir = 0; dir < 4; dir++) {
                int nr = c.getRow() + DR[dir];
                int nc = c.getCol() + DC[dir];
                if (!maze.isWalkable(nr, nc)) continue;
                if (visitado[nr][nc]) continue;

                int novoCusto = dist[c.getRow()][c.getCol()] + 1;
                if (novoCusto < dist[nr][nc]) {
                    dist[nr][nc] = novoCusto;
                    prev.put(new Cell(nr, nc), c);
                    fila.add(new NodeDist(new Cell(nr, nc), novoCusto));
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;

        if (exitFound == null) {
            return new PathResult(Collections.emptyList(), -1, elapsed, nodesExpanded, null);
        }

        List<Cell> caminho = reconstruirCaminho(prev, start, exitFound);
        int custo = dist[exitFound.getRow()][exitFound.getCol()];
        return new PathResult(caminho, custo, elapsed, nodesExpanded, exitFound);
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

    /** Par (célula, distância acumulada) ordenado pela distância — usado na fila de prioridade. */
    private static class NodeDist implements Comparable<NodeDist> {
        final Cell cell;
        final int dist;

        NodeDist(Cell cell, int dist) {
            this.cell = cell;
            this.dist = dist;
        }

        @Override
        public int compareTo(NodeDist o) {
            return Integer.compare(this.dist, o.dist);
        }
    }
}
