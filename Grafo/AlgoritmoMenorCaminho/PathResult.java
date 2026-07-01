package Grafo.AlgoritmoMenorCaminho;

import java.util.Collections;
import java.util.List;

/**
 * Resultado da execução de um algoritmo de busca de caminho.
 */
public class PathResult {
    private final List<Cell> path;       // caminho do início até a saída encontrada (vazio se não há solução)
    private final int cost;              // custo total (número de passos, já que cada aresta tem peso 1)
    private final long elapsedNanos;     // tempo de execução em nanossegundos
    private final int nodesExpanded;     // quantidade de células efetivamente expandidas (removidas da fila)
    private final Cell exitFound;        // qual saída foi alcançada

    public PathResult(List<Cell> path, int cost, long elapsedNanos, int nodesExpanded, Cell exitFound) {
        this.path = path == null ? Collections.emptyList() : path;
        this.cost = cost;
        this.elapsedNanos = elapsedNanos;
        this.nodesExpanded = nodesExpanded;
        this.exitFound = exitFound;
    }

    public List<Cell> getPath() { return path; }
    public int getCost() { return cost; }
    public long getElapsedNanos() { return elapsedNanos; }
    public double getElapsedMillis() { return elapsedNanos / 1_000_000.0; }
    public int getNodesExpanded() { return nodesExpanded; }
    public Cell getExitFound() { return exitFound; }
    public boolean isSuccess() { return !path.isEmpty(); }
}
