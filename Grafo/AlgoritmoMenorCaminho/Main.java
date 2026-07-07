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

        Labirinto labirinto;
        try {
            labirinto = new Labirinto(arquivo);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo '" + arquivo + "': " + e.getMessage());
            return;
        } catch (IllegalArgumentException e) {
            System.err.println("Labirinto inválido: " + e.getMessage());
            return;
        }

        System.out.println("Labirinto carregado: " + labirinto.getQuantidadeLinhas() + " x " + labirinto.getQuantidadeColunas());
        System.out.println("Partida: " + labirinto.getInicio());
        System.out.println("Saídas: " + labirinto.getSaidas());
        System.out.println();

        Dijkstra dijkstra = new Dijkstra();
        AEstrela aEstrela = new AEstrela();

        CaminhoResultante resultadoDijkstra = dijkstra.resolver(labirinto);
        CaminhoResultante resultadoAEstrela = aEstrela.resolver(labirinto);

        System.out.println("=== DIJKSTRA ===");
        imprimirResultado(resultadoDijkstra);
        System.out.println();
        labirinto.imprimirComCaminho(resultadoDijkstra.getCaminho());

        System.out.println();
        System.out.println("=== A* (A-ESTRELA) ===");
        imprimirResultado(resultadoAEstrela);
        System.out.println();
        labirinto.imprimirComCaminho(resultadoAEstrela.getCaminho());

        System.out.println();
        System.out.println("=== COMPARATIVO ===");
        compararResultados(resultadoDijkstra, resultadoAEstrela);
    }

    private static void imprimirResultado(CaminhoResultante resultado) {
        if (!resultado.isSucesso()) {
            System.out.println("Não foi encontrado caminho até nenhuma saída.");
            System.out.printf("Tempo gasto: %.4f ms%n", resultado.getTempoDecorridoEmMilissegundos());
            return;
        }
        System.out.println("Saída alcançada: " + resultado.getSaidaEncontrada());
        System.out.println("Custo do caminho (nº de passos): " + resultado.getCusto());
        System.out.println("Nós expandidos: " + resultado.getNosExpandidos());
        System.out.printf("Tempo gasto: %.4f ms%n", resultado.getTempoDecorridoEmMilissegundos());
        System.out.println("Caminho: " + formatarCaminho(resultado.getCaminho()));
    }

    private static String formatarCaminho(List<Celula> caminho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < caminho.size(); i++) {
            sb.append(caminho.get(i));
            if (i < caminho.size() - 1) sb.append(" -> ");
        }
        return sb.toString();
    }

    private static void compararResultados(CaminhoResultante dijkstra, CaminhoResultante aEstrela) {
        if (!dijkstra.isSucesso() || !aEstrela.isSucesso()) {
            System.out.println("Não é possível comparar: ao menos um algoritmo não encontrou solução.");
            return;
        }

        System.out.printf("%-25s %-15s %-15s%n", "Métrica", "Dijkstra", "A*");
        System.out.printf("%-25s %-15d %-15d%n", "Custo do caminho", dijkstra.getCusto(), aEstrela.getCusto());
        System.out.printf("%-25s %-15d %-15d%n", "Nós expandidos", dijkstra.getNosExpandidos(), aEstrela.getNosExpandidos());
        System.out.printf("%-25s %-15.4f %-15.4f%n", "Tempo (ms)", dijkstra.getTempoDecorridoEmMilissegundos(), aEstrela.getTempoDecorridoEmMilissegundos());

        System.out.println();
        if (dijkstra.getCusto() == aEstrela.getCusto()) {
            System.out.println("Ambos encontraram caminhos de mesmo custo (" + dijkstra.getCusto()
                    + "), confirmando a otimalidade do A* com heurística admissível.");
        } else {
            System.out.println("ATENÇÃO: os custos divergem (" + dijkstra.getCusto() + " vs " + aEstrela.getCusto()
                    + "). Isso indicaria uma heurística não admissível — não deveria ocorrer com Manhattan em grid 4-direcional.");
        }

        int reducao = dijkstra.getNosExpandidos() - aEstrela.getNosExpandidos();
        if (reducao > 0) {
            double percentual = 100.0 * reducao / dijkstra.getNosExpandidos();
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
