package Grafo.AlgoritmoMenorCaminho;

import java.util.Collections;
import java.util.List;

/**
 * Resultado da execução de um algoritmo de busca de caminho.
 */
public class CaminhoResultante {
    private final List<Celula> caminho;                  // caminho do início até a saída encontrada (vazio se não há solução)
    private final int custo;                             // custo total (número de passos, já que cada aresta tem peso 1)
    private final long tempoDecorridoEmNanosegundos;     // tempo de execução em nanossegundos
    private final int nosExpandidos;                    // quantidade de células efetivamente expandidas (removidas da fila)
    private final Celula saidaEncontrada;               // qual saída foi alcançada

    public CaminhoResultante(List<Celula> caminho, int custo, long tempoDecorridoEmNanosegundos, int nosExpandidos, Celula saidaEncontrada) {
        this.caminho = caminho == null ? Collections.emptyList() : caminho;
        this.custo = custo;
        this.tempoDecorridoEmNanosegundos = tempoDecorridoEmNanosegundos;
        this.nosExpandidos = nosExpandidos;
        this.saidaEncontrada = saidaEncontrada;
    }

    public List<Celula> getCaminho() { return caminho; }
    public int getCusto() { return custo; }
    public long getTempoDecorridoEmNanosegundos() { return tempoDecorridoEmNanosegundos; }
    public double getTempoDecorridoEmMilissegundos() { return tempoDecorridoEmNanosegundos / 1_000_000.0; }
    public int getNosExpandidos() { return nosExpandidos; }
    public Celula getSaidaEncontrada() { return saidaEncontrada; }
    public boolean isSucesso() { return !caminho.isEmpty(); }
}
