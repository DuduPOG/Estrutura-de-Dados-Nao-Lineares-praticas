package Grafo.AlgoritmoMenorCaminho;

import java.util.List;

public class CaminhoResultante {
    private final List<Celula> caminho;
    private final int custo;
    private final long tempoDecorridoEmNanosegundos;
    private final int nosExpandidos;
    private final Celula saidaEncontrada;

    public CaminhoResultante(List<Celula> caminho, int custo, long tempoDecorridoEmNanosegundos, int nosExpandidos, Celula saidaEncontrada) {
        this.caminho = caminho;
        this.custo = custo;
        this.tempoDecorridoEmNanosegundos = tempoDecorridoEmNanosegundos;
        this.nosExpandidos = nosExpandidos;
        this.saidaEncontrada = saidaEncontrada;
    }

    public List<Celula> getCaminho() {
        return caminho;
    }

    public int getCusto() {
        return custo;
    }
    
    public long getTempoDecorridoEmNanosegundos() {
        return tempoDecorridoEmNanosegundos;
    }

    public double getTempoDecorridoEmMilissegundos() {
        return tempoDecorridoEmNanosegundos / 1_000_000.0;
    }

    public int getNosExpandidos() {
        return nosExpandidos;
    }

    public Celula getSaidaEncontrada() {
        return saidaEncontrada;
    }
    public boolean isSucesso() {
        return !caminho.isEmpty();
    }
}
