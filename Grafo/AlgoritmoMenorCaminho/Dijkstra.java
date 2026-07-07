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
public class Dijkstra {

    private static final int[] MUDANCA_LINHA = {-1, 1, 0, 0};
    private static final int[] MUDANCA_COLUNA = {0, 0, -1, 1};

    public CaminhoResultante resolver(Labirinto labirinto) {
        long inicioTempo = System.nanoTime();

        int linhas = labirinto.getQuantidadeLinhas();
        int colunas = labirinto.getQuantidadeColunas();
        int[][] distancias = new int[linhas][colunas];
        for (int[] linha : distancias) java.util.Arrays.fill(linha, Integer.MAX_VALUE);

        Map<Celula, Celula> predecessores = new HashMap<>();
        boolean[][] visitado = new boolean[linhas][colunas];

        Celula inicio = labirinto.getInicio();
        distancias[inicio.getLinha()][inicio.getColuna()] = 0;

        PriorityQueue<NoDistancia> filaPrioridade = new PriorityQueue<>();
        filaPrioridade.add(new NoDistancia(inicio, 0));

        int nosExpandidos = 0;
        Celula saidaEncontrada = null;

        while (!filaPrioridade.isEmpty()) {
            NoDistancia atual = filaPrioridade.poll();
            Celula celulaAtual = atual.celula;

            if (visitado[celulaAtual.getLinha()][celulaAtual.getColuna()]) continue;
            visitado[celulaAtual.getLinha()][celulaAtual.getColuna()] = true;
            nosExpandidos++;

            if (labirinto.ehSaida(celulaAtual)) {
                saidaEncontrada = celulaAtual;
                break;
            }

            for (int direcao = 0; direcao < 4; direcao++) {
                int proximaLinha = celulaAtual.getLinha() + MUDANCA_LINHA[direcao];
                int proximaColuna = celulaAtual.getColuna() + MUDANCA_COLUNA[direcao];
                if (!labirinto.ehAndavel(proximaLinha, proximaColuna)) continue;
                if (visitado[proximaLinha][proximaColuna]) continue;

                int novoCusto = distancias[celulaAtual.getLinha()][celulaAtual.getColuna()] + 1;
                if (novoCusto < distancias[proximaLinha][proximaColuna]) {
                    distancias[proximaLinha][proximaColuna] = novoCusto;
                    predecessores.put(new Celula(proximaLinha, proximaColuna), celulaAtual);
                    filaPrioridade.add(new NoDistancia(new Celula(proximaLinha, proximaColuna), novoCusto));
                }
            }
        }

        long tempoDecorrido = System.nanoTime() - inicioTempo;

        if (saidaEncontrada == null) {
            return new CaminhoResultante(Collections.emptyList(), -1, tempoDecorrido, nosExpandidos, null);
        }

        List<Celula> caminho = reconstruirCaminho(predecessores, inicio, saidaEncontrada);
        int custo = distancias[saidaEncontrada.getLinha()][saidaEncontrada.getColuna()];
        return new CaminhoResultante(caminho, custo, tempoDecorrido, nosExpandidos, saidaEncontrada);
    }

    private List<Celula> reconstruirCaminho(Map<Celula, Celula> predecessores, Celula inicio, Celula fim) {
        ArrayDeque<Celula> pilha = new ArrayDeque<>();
        Celula atual = fim;
        pilha.push(atual);
        while (!atual.equals(inicio)) {
            atual = predecessores.get(atual);
            pilha.push(atual);
        }
        return new ArrayList<>(pilha);
    }

    /** Par (célula, distância acumulada) ordenado pela distância — usado na fila de prioridade. */
    private static class NoDistancia implements Comparable<NoDistancia> {
        final Celula celula;
        final int distancia;

        NoDistancia(Celula celula, int distancia) {
            this.celula = celula;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(NoDistancia outro) {
            return Integer.compare(this.distancia, outro.distancia);
        }
    }
}
