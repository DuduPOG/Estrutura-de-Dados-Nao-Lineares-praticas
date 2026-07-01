# Labirinto: Dijkstra vs A*

Implementação em Java pura (sem dependências externas) dos algoritmos
de Dijkstra e A* aplicados a um labirinto lido de arquivo `.dat`,
conforme o enunciado do trabalho.

## Estrutura

```
src/
  Cell.java            posição (linha, coluna) na matriz
  Maze.java            leitura do .dat e representação do labirinto
  PathResult.java       encapsula caminho, custo, tempo e nós expandidos
  DijkstraSolver.java   algoritmo de Dijkstra (fila de prioridade por g(n))
  AStarSolver.java      algoritmo A* (fila de prioridade por f(n)=g(n)+h(n))
  Main.java             executa os dois algoritmos e imprime o comparativo
labirinto.dat           matriz de exemplo, igual à do enunciado
```

## Convenção da matriz (.dat)

- `0` caminho livre
- `1` parede
- `2` ponto de partida (deve haver exatamente um)
- `3` saída (pode haver mais de uma; o algoritmo encontra a mais próxima)

Cada linha do arquivo é uma linha da matriz; cada caractere (sem
separador) é uma coluna — exatamente como no exemplo do PDF.

## Compilar

```bash
cd src
javac -d ../out *.java
```

## Executar

```bash
cd out
java -Dfile.encoding=UTF-8 Main ../labirinto.dat
```

Se nenhum argumento for passado, o programa procura por
`labirinto.dat` no diretório corrente.

> Nota: a flag `-Dfile.encoding=UTF-8` evita que acentos (ã, ó, º)
> sejam corrompidos em terminais com locale não-UTF-8 (comum em alguns
> ambientes Windows/CI). Em terminais Linux/Mac modernos normalmente
> não é necessária.

## O que o programa mostra

Para cada algoritmo:
- Saída do labirinto alcançada (qual das possivelmente várias saídas)
- Custo do caminho (número de passos)
- Quantidade de nós expandidos (removidos da fila de prioridade)
- Tempo de execução (ms)
- O caminho completo, célula a célula
- Uma impressão ASCII do labirinto com o caminho marcado (`*`)

Ao final, um comparativo lado a lado entre Dijkstra e A*, com análise
automática:
- Confirma se os custos batem (devem bater sempre, pois a heurística
  de Manhattan é admissível em grid 4-direcional — A* garante
  otimalidade igual ao Dijkstra)
- Calcula a redução percentual de nós expandidos pelo A*

## Por que A* tende a expandir menos nós que Dijkstra aqui

Dijkstra explora uniformemente em todas as direções a partir da
origem (equivalente a uma BFS por camadas de custo, já que todas as
arestas têm peso 1). A* usa a heurística de Manhattan até a saída mais
próxima para **priorizar** a expansão de nós que parecem estar na
direção certa, podando ramos que se afastam do objetivo. Como a
heurística é admissível (nunca superestima a distância real, pois
ignora paredes), o A* preserva a garantia de encontrar o caminho de
custo mínimo — apenas chega lá expandindo, em geral, menos nós.

Em labirintos muito estreitos e sinuosos (corredores únicos sem
ramificações alternativas), a vantagem do A* tende a ser pequena,
porque não há "direções erradas" a podar. A vantagem cresce em
labirintos mais abertos, com múltiplos caminhos possíveis.
