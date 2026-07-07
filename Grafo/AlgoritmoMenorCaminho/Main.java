package Grafo.AlgoritmoMenorCaminho;

import java.io.IOException;
import java.util.List;

/**
 * Ponto de entrada do sistema.
 *
 * Uso:
 *   java Main caminho/para/labirinto.dat
 *
 * Se nenhum argumento for passado, tenta usar "labirinto.dat" no
 * diretório corrente.
 */
public class Main {

    public static void main(String[] args) {
        String arquivo = args.length > 0 ? args[0] : "labirinto.dat";

        Labirinto maze;
        try {
            maze = new Labirinto(arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo '" + arquivo + "': " + e.getMessage());
            return;
        } catch (IllegalArgumentException e) {
            System.err.println("Labirinto inválido: " + e.getMessage());
            return;
        }

        System.out.println("Labirinto carregado: " + maze.getRows() + " x " + maze.getCols());
        System.out.println("Partida: " + maze.getStart());
        System.out.println("Saídas: " + maze.getExits());
        System.out.println();

        Dijkstra dijkstra = new Dijkstra();
        AEstrela astar = new AEstrela();

        CaminhoResultante resultDijkstra = dijkstra.solve(maze);
        CaminhoResultante resultAStar = astar.solve(maze);

        System.out.println("=== DIJKSTRA ===");
        imprimirResultado(resultDijkstra);
        System.out.println();
        maze.printWithPath(resultDijkstra.getPath());

        System.out.println();
        System.out.println("=== A* (A-ESTRELA) ===");
        imprimirResultado(resultAStar);
        System.out.println();
        maze.printWithPath(resultAStar.getPath());

        System.out.println();
        System.out.println("=== COMPARATIVO ===");
        compararResultados(resultDijkstra, resultAStar);
    }

    private static void imprimirResultado(CaminhoResultante result) {
        if (!result.isSuccess()) {
            System.out.println("Não foi encontrado caminho até nenhuma saída.");
            System.out.printf("Tempo gasto: %.4f ms%n", result.getElapsedMillis());
            return;
        }
        System.out.println("Saída alcançada: " + result.getExitFound());
        System.out.println("Custo do caminho (nº de passos): " + result.getCost());
        System.out.println("Nós expandidos: " + result.getNodesExpanded());
        System.out.printf("Tempo gasto: %.4f ms%n", result.getElapsedMillis());
        System.out.println("Caminho: " + formatarCaminho(result.getPath()));
    }

    private static String formatarCaminho(List<Celula> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i < path.size() - 1) sb.append(" -> ");
        }
        return sb.toString();
    }

    private static void compararResultados(CaminhoResultante dijkstra, CaminhoResultante astar) {
        if (!dijkstra.isSuccess() || !astar.isSuccess()) {
            System.out.println("Não é possível comparar: ao menos um algoritmo não encontrou solução.");
            return;
        }

        System.out.printf("%-25s %-15s %-15s%n", "Métrica", "Dijkstra", "A*");
        System.out.printf("%-25s %-15d %-15d%n", "Custo do caminho", dijkstra.getCost(), astar.getCost());
        System.out.printf("%-25s %-15d %-15d%n", "Nós expandidos", dijkstra.getNodesExpanded(), astar.getNodesExpanded());
        System.out.printf("%-25s %-15.4f %-15.4f%n", "Tempo (ms)", dijkstra.getElapsedMillis(), astar.getElapsedMillis());

        System.out.println();
        if (dijkstra.getCost() == astar.getCost()) {
            System.out.println("Ambos encontraram caminhos de mesmo custo (" + dijkstra.getCost()
                    + "), confirmando a otimalidade do A* com heurística admissível.");
        } else {
            System.out.println("ATENÇÃO: os custos divergem (" + dijkstra.getCost() + " vs " + astar.getCost()
                    + "). Isso indicaria uma heurística não admissível — não deveria ocorrer com Manhattan em grid 4-direcional.");
        }

        int reducao = dijkstra.getNodesExpanded() - astar.getNodesExpanded();
        if (reducao > 0) {
            double percentual = 100.0 * reducao / dijkstra.getNodesExpanded();
            System.out.printf("A* expandiu %d nós a menos que Dijkstra (%.1f%% de redução), "
                    + "pois a heurística direciona a busca em direção à saída.%n", reducao, percentual);
        } else if (reducao < 0) {
            System.out.println("Neste caso específico, A* expandiu mais nós que Dijkstra "
                    + "(pode ocorrer em labirintos pequenos/pouco ramificados, onde o overhead "
                    + "do cálculo da heurística não compensa).");
        } else {
            System.out.println("Ambos expandiram exatamente o mesmo número de nós.");
        }
    }
}
