package Grafo.AlgoritmoMenorCaminho;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AEstrela {

    private static final int[] MUDANCA_LINHA = {-1, 1, 0, 0};
    private static final int[] MUDANCA_COLUNA = {0, 0, -1, 1};

    public CaminhoResultante resolver(Labirinto labirinto) {
        long inicioTempo = System.nanoTime();

        int linhas = labirinto.getQuantidadeLinhas();
        int colunas = labirinto.getQuantidadeColunas();
        List<Celula> saidas = labirinto.getSaidas();

        int[][] pontuacaoG = new int[linhas][colunas];
        for (int[] linha : pontuacaoG) java.util.Arrays.fill(linha, Integer.MAX_VALUE);

        Map<Celula, Celula> predecessores = new HashMap<>();
        boolean[][] fechado = new boolean[linhas][colunas];

        Celula inicio = labirinto.getInicio();
        pontuacaoG[inicio.getLinha()][inicio.getColuna()] = 0;

        PriorityQueue<NoPrioridade> abertos = new PriorityQueue<>();
        abertos.add(new NoPrioridade(inicio, calcularHeuristica(inicio, saidas)));

        int nosExpandidos = 0;
        Celula saidaEncontrada = null;

        while (!abertos.isEmpty()) {
            NoPrioridade atual = abertos.poll();
            Celula celulaAtual = atual.celula;

            if (fechado[celulaAtual.getLinha()][celulaAtual.getColuna()]) continue;
            fechado[celulaAtual.getLinha()][celulaAtual.getColuna()] = true;
            nosExpandidos++;

            if (labirinto.ehSaida(celulaAtual)) {
                saidaEncontrada = celulaAtual;
                break;
            }

            for (int direcao = 0; direcao < 4; direcao++) {
                int proximaLinha = celulaAtual.getLinha() + MUDANCA_LINHA[direcao];
                int proximaColuna = celulaAtual.getColuna() + MUDANCA_COLUNA[direcao];
                if (!labirinto.ehAndavel(proximaLinha, proximaColuna)) continue;
                if (fechado[proximaLinha][proximaColuna]) continue;

                int tentativaG = pontuacaoG[celulaAtual.getLinha()][celulaAtual.getColuna()] + 1;
                if (tentativaG < pontuacaoG[proximaLinha][proximaColuna]) {
                    pontuacaoG[proximaLinha][proximaColuna] = tentativaG;
                    Celula vizinho = new Celula(proximaLinha, proximaColuna);
                    predecessores.put(vizinho, celulaAtual);
                    int valorF = tentativaG + calcularHeuristica(vizinho, saidas);
                    abertos.add(new NoPrioridade(vizinho, valorF));
                }
            }
        }

        long tempoDecorrido = System.nanoTime() - inicioTempo;

        if (saidaEncontrada == null) {
            return new CaminhoResultante(Collections.emptyList(), -1, tempoDecorrido, nosExpandidos, null);
        }

        List<Celula> caminho = reconstruirCaminho(predecessores, inicio, saidaEncontrada);
        int custo = pontuacaoG[saidaEncontrada.getLinha()][saidaEncontrada.getColuna()];
        return new CaminhoResultante(caminho, custo, tempoDecorrido, nosExpandidos, saidaEncontrada);
    }

    /** Heurística: menor distância de Manhattan entre a célula e qualquer saída. */
    private int calcularHeuristica(Celula celula, List<Celula> saidas) {
        int minimo = Integer.MAX_VALUE;
        for (Celula saida : saidas) {
            int distancia = Math.abs(celula.getLinha() - saida.getLinha()) + Math.abs(celula.getColuna() - saida.getColuna());
            if (distancia < minimo) minimo = distancia;
        }
        return minimo;
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
    private static class NoPrioridade implements Comparable<NoPrioridade> {
        final Celula celula;
        final int valorF;

        NoPrioridade(Celula celula, int valorF) {
            this.celula = celula;
            this.valorF = valorF;
        }

        @Override
        public int compareTo(NoPrioridade outro) {
            return Integer.compare(this.valorF, outro.valorF);
        }
    }
}
