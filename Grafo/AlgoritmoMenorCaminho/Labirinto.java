package Grafo.AlgoritmoMenorCaminho;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Labirinto {

    public static final int LIVRE = 0;
    public static final int PAREDE = 1;
    public static final int PARTIDA = 2;
    public static final int SAIDA = 3;

    private final int[][] grade;
    private final int linhas;
    private final int colunas;
    private final Celula inicio;
    private final List<Celula> saidas;

    public Labirinto(String caminhoDoArquivo) throws IOException {
        List<int[]> linhasArquivo = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoDoArquivo))) {
            String linha;
            int maxColunas = 0;
            while ((linha = leitor.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;
                int[] valores = new int[linha.length()];
                for (int i = 0; i < linha.length(); i++) {
                    char caractere = linha.charAt(i);
                    if (!Character.isDigit(caractere)) {
                        throw new IllegalArgumentException(
                                "Caractere inválido no arquivo .dat: '" + caractere + "'");
                    }
                    valores[i] = caractere - '0';
                }
                maxColunas = Math.max(maxColunas, valores.length);
                linhasArquivo.add(valores);
            }
            this.colunas = maxColunas;
        }

        this.linhas = linhasArquivo.size();
        if (this.linhas == 0) {
            throw new IllegalArgumentException("Arquivo .dat vazio ou inválido.");
        }

        this.grade = new int[linhas][colunas];
        Celula inicioTemporario = null;
        List<Celula> saidasTemporarias = new ArrayList<>();

        for (int linha = 0; linha < linhas; linha++) {
            int[] valoresDaLinha = linhasArquivo.get(linha);
            for (int coluna = 0; coluna < colunas; coluna++) {
                int valor = (coluna < valoresDaLinha.length) ? valoresDaLinha[coluna] : PAREDE;
                grade[linha][coluna] = valor;
                if (valor == PARTIDA) {
                    if (inicioTemporario != null) {
                        throw new IllegalArgumentException(
                                "Mais de um ponto de partida (2) encontrado no labirinto.");
                    }
                    inicioTemporario = new Celula(linha, coluna);
                } else if (valor == SAIDA) {
                    saidasTemporarias.add(new Celula(linha, coluna));
                }
            }
        }

        if (inicioTemporario == null) {
            throw new IllegalArgumentException("Nenhum ponto de partida (2) encontrado.");
        }
        if (saidasTemporarias.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma saída (3) encontrada.");
        }

        this.inicio = inicioTemporario;
        this.saidas = saidasTemporarias;
    }

    public int getQuantidadeLinhas() { return linhas; }
    public int getQuantidadeColunas() { return colunas; }
    public Celula getInicio() { return inicio; }
    public List<Celula> getSaidas() { return saidas; }

    public boolean ehAndavel(int linha, int coluna) {
        if (linha < 0 || linha >= linhas || coluna < 0 || coluna >= colunas) return false;
        return grade[linha][coluna] != PAREDE;
    }

    public boolean ehSaida(Celula celula) {
        return grade[celula.getLinha()][celula.getColuna()] == SAIDA;
    }

    public int valorNaPosicao(int linha, int coluna) {
        return grade[linha][coluna];
    }

    public void imprimirComCaminho(List<Celula> caminho) {
        boolean[][] noCaminho = new boolean[linhas][colunas];
        if (caminho != null) {
            for (Celula celula : caminho) noCaminho[celula.getLinha()][celula.getColuna()] = true;
        }
        StringBuilder sb = new StringBuilder();
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                int valor = grade[linha][coluna];
                char caractere;
                if (noCaminho[linha][coluna] && valor != PARTIDA && valor != SAIDA) {
                    caractere = '*';
                } else {
                    switch (valor) {
                        case PAREDE: caractere = '#'; break;
                        case PARTIDA: caractere = 'P'; break;
                        case SAIDA: caractere = 'S'; break;
                        default: caractere = '.'; break;
                    }
                }
                sb.append(caractere);
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
