package Grafo.AlgoritmoMenorCaminho;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AEstrela {

    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public CaminhoResultante solve(Labirinto maze) {
        long startTime = System.nanoTime();

        int rows = maze.getRows();
        int cols = maze.getCols();
        List<Celula> exits = maze.getExits();

        int[][] gScore = new int[rows][cols];
        for (int[] linha : gScore) java.util.Arrays.fill(linha, Integer.MAX_VALUE);

        Map<Celula, Celula> prev = new HashMap<>();
        boolean[][] fechado = new boolean[rows][cols];

        Celula start = maze.getStart();
        gScore[start.getRow()][start.getCol()] = 0;

        PriorityQueue<NodeF> aberto = new PriorityQueue<>();
        aberto.add(new NodeF(start, heuristica(start, exits)));

        int nodesExpanded = 0;
        Celula exitFound = null;

        while (!aberto.isEmpty()) {
            NodeF atual = aberto.poll();
            Celula c = atual.cell;

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
                    Celula vizinho = new Celula(nr, nc);
                    prev.put(vizinho, c);
                    int f = tentativeG + heuristica(vizinho, exits);
                    aberto.add(new NodeF(vizinho, f));
                }
            }
        }

        long elapsed = System.nanoTime() - startTime;

        if (exitFound == null) {
            return new CaminhoResultante(Collections.emptyList(), -1, elapsed, nodesExpanded, null);
        }

        List<Celula> caminho = reconstruirCaminho(prev, start, exitFound);
        int custo = gScore[exitFound.getRow()][exitFound.getCol()];
        return new CaminhoResultante(caminho, custo, elapsed, nodesExpanded, exitFound);
    }

    /** Heurística: menor distância de Manhattan entre a célula e qualquer saída. */
    private int heuristica(Celula c, List<Celula> exits) {
        int min = Integer.MAX_VALUE;
        for (Celula saida : exits) {
            int d = Math.abs(c.getRow() - saida.getRow()) + Math.abs(c.getCol() - saida.getCol());
            if (d < min) min = d;
        }
        return min;
    }

    private List<Celula> reconstruirCaminho(Map<Celula, Celula> prev, Celula start, Celula fim) {
        ArrayDeque<Celula> pilha = new ArrayDeque<>();
        Celula atual = fim;
        pilha.push(atual);
        while (!atual.equals(start)) {
            atual = prev.get(atual);
            pilha.push(atual);
        }
        return new ArrayList<>(pilha);
    }

    /** Par (célula, f-score) ordenado por f = g + h — usado na fila de prioridade. */
    private static class NodeF implements Comparable<NodeF> {
        final Celula cell;
        final int f;

        NodeF(Celula cell, int f) {
            this.cell = cell;
            this.f = f;
        }

        @Override
        public int compareTo(NodeF o) {
            return Integer.compare(this.f, o.f);
        }
    }
}
