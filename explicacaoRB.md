# 🌳 Estrutura geral da implementação

Sua implementação segue o modelo clássico de árvore rubro-negra imperativa, baseada em:

* inserção estilo ABB
* recoloração
* rotações
* correção iterativa pós-operação

A árvore mantém implicitamente as 4 propriedades fundamentais descritas no slide:

1. nós nulos são negros
2. raiz sempre negra
3. nó vermelho nunca possui filho vermelho
4. todo caminho possui mesma altura negra

A enum `NoRB.Cores` representa:

* `P` → preto
* `V` → vermelho

---

## 🧠 Ideia central da implementação

AVL controla:

* diferença de alturas

Rubro-negra controla:

* distribuição de nós pretos
* adjacência de nós vermelhos

Ou seja:

AVL:

* balanceamento rígido

Rubro-negra:

* balanceamento relaxado com menos rotações

Resultado prático:

* inserções e remoções costumam exigir menos ajustes que AVL
* complexidade permanece `O(log n)`

---

## 🔍 Busca

O método:

```java
public NoRB buscar(NoRB no, int value)
```

é uma busca binária iterativa tradicional.

Fluxo:

* menor → esquerda
* maior → direita
* igual → retorna nó

Complexidade:

* média: `O(log n)`
* pior caso teórico da RN: ainda `O(log n)`

---

## ⚫ Método `cor(NoRB no)`

```java
private NoRB.Cores cor(NoRB no) {
    return no == null ? NoRB.Cores.P : no.getCor();
}
```

Esse método é extremamente importante.

Ele implementa implicitamente a propriedade:

> “todo nó nulo é preto”

Isso simplifica muito:

* remoção
* verificações de tio
* verificações de irmão
* casos duplo negro

Sem precisar criar nós sentinela NIL explícitos.

---

## 🔄 Rotações

Sua implementação possui as 4 rotações clássicas:

| Método  | Tipo                     |
| ------- | ------------------------ |
| `RSD()` | rotação simples direita  |
| `RSE()` | rotação simples esquerda |
| `RDD()` | rotação dupla direita    |
| `RDE()` | rotação dupla esquerda   |

As rotações:

* preservam propriedade BST
* reorganizam altura negra
* corrigem violações vermelho-vermelho

---

## 🔵 Inserção

### 1. Inserção ABB

O método:

```java
public void inserir(int value)
```

primeiro executa inserção binária tradicional.

Percurso:

* começa na raiz
* encontra posição nula
* insere folha

---

### 2. Novo nó nasce vermelho

```java
novo.setCor(NoRB.Cores.V);
```

Isso segue exatamente o slide:

> novos nós devem nascer rubros

Motivo:

Inserir preto alteraria a altura negra de todos os caminhos.

Inserindo vermelho:

* propriedade IV permanece válida inicialmente

---

### 3. Caso especial: raiz

```java
if (paiAtual == null)
```

Se a árvore estava vazia:

* novo nó vira raiz
* raiz é pintada de preto

```java
this.raiz.setCor(NoRB.Cores.P);
```

Preserva propriedade II.

---

## 🔁 Rebalanceamento de inserção

Método:

```java
rebalanceamento(novo);
```

A correção sobe na árvore enquanto existir:

```java
pai vermelho
```

Ou seja:

```java
while (no != raiz && pai vermelho)
```

O único problema estrutural possível na inserção RN é:

## ❌ vermelho com filho vermelho

---

## 🟢 Caso 1 — Pai preto

Slide:

> se o pai é negro, nada precisa ser feito

Na prática:

o `while` nem executa.

A árvore continua válida.

---

## 🟡 Caso 2 — Pai vermelho + tio vermelho

Trecho:

```java
if (cor(tio) == NoRB.Cores.V)
```

Ação:

```java
pai.setCor(P);
tio.setCor(P);
avo.setCor(V);
```

Equivalente ao slide:

* pai fica preto
* tio fica preto
* avô fica vermelho

---

## 🧠 Interpretação estrutural

Você move o “conflito vermelho” para cima.

Antes:

```text
      preto
     /     \
 vermelho vermelho
```

Depois:

```text
      vermelho
     /       \
 preto      preto
```

A altura negra permanece igual.

Mas:

* o avô pode agora violar regra vermelho-vermelho com o pai dele

Por isso:

```java
no = avo;
```

e o laço continua subindo.

---

## 🔴 Caso 3 — Pai vermelho + tio preto

Aqui rotações são necessárias.

Slide divide em 4 subcasos.

Sua implementação faz isso implicitamente.

---

### Caso 3a — LL (rotação direita)

Situação:

```text
      avo(P)
      /
   pai(V)
   /
 no(V)
```

Código:

```java
pai.setCor(P);
avo.setCor(V);
RSD(avo);
```

Resultado:

* pai sobe
* avô desce
* violação desaparece

---

### Caso 3b — RR (rotação esquerda)

Simétrico:

```text
avo(P)
   \
   pai(V)
      \
      no(V)
```

Código:

```java
pai.setCor(P);
avo.setCor(V);
RSE(avo);
```

---

### Caso 3c — LR

Situação:

```text
      avo
      /
   pai
      \
       no
```

Primeiro:

```java
RSE(pai)
```

Transforma LR em LL.

Depois:

```java
RSD(avo)
```

Seu código:

```java
if (no == pai.getFD()) {
    no = pai;
    RSE(no);
}
```

---

### Caso 3d — RL

Simétrico.

Primeiro:

* RSD(pai)

Depois:

* RSE(avô)

---

## 🔒 Finalização da inserção

No final:

```java
this.raiz.setCor(P);
```

Garantia absoluta da propriedade II.

---

## 🔴 Remoção

A remoção RN é significativamente mais complexa que inserção.

Motivo:

* remover nó preto altera altura negra
* isso quebra propriedade IV

---

## 🧩 Estratégia geral da sua implementação

Fluxo:

1. encontra nó
2. remove como ABB
3. identifica cor removida
4. corrige árvore se necessário

---

## 🟢 Caso simples — nó vermelho removido

Se:

```java
corOriginal == V
```

Nada precisa ser feito.

Motivo:

* nó vermelho não participa da altura negra

Isso corresponde à Situação 1 do slide.

---

## ⚫ Caso simples — preto substituído por vermelho

Situação:

* nó removido era preto
* sucessor é vermelho

Slide:

> pinte sucessor de preto

Na sua implementação isso emerge naturalmente em:

```java
if (no != null) {
    no.setCor(P);
}
```

ao final do rebalanceamento.

---

## 🧠 Conceito central: duplo negro

Quando um preto é removido:

a árvore “perde um preto”.

Sua implementação representa isso implicitamente via:

```java
while (no != raiz && cor(no) == P)
```

Ou seja:

* o nó atual está “faltando um preto”
* precisa absorver/repassar esse déficit

---

## ⚫⚫ Caso 3 — irmão vermelho

Slide:

* irmão vermelho
* pai preto

Seu código:

```java
if (cor(irmaoPai) == V)
```

Ação:

```java
irmaoPai.setCor(P);
pai.setCor(V);
RSE(pai);
```

ou simétrico com `RSD`.

---

## 🧠 Interpretação

Esse caso:

* não resolve o duplo negro
* transforma a árvore em outro caso mais simples

Você converte:

```text
irmão vermelho
```

em:

```text
irmão preto
```

para cair nos próximos casos.

---

## ⚫ Caso 2a — irmão preto com filhos pretos e pai preto

Slide:

> pinta irmão de vermelho e sobe duplo negro

Seu código:

```java
irmaoPai.setCor(V);
no = pai;
pai = no.getPai();
```

---

## 🧠 Interpretação

O irmão “cede” um preto.

Mas agora:

* o pai fica faltando preto

Então o problema sobe.

---

## ⚫ Caso 2b — irmão preto com filhos pretos e pai vermelho

Slide:

> irmão vira vermelho e pai vira preto

Seu código faz isso implicitamente quando:

```java
irmaoPai.setCor(pai.getCor());
pai.setCor(P);
```

O duplo negro é absorvido.

O laço termina.

---

## ⚫ Caso 3 — irmão preto com filho interno vermelho

Exemplo lado esquerdo:

```text
x é filho esquerdo
irmão preto
filho esquerdo do irmão vermelho
filho direito preto
```

Seu código:

```java
if (cor(irmaoPai.getFD()) == P)
```

Ação:

```java
irmaoPai.getFE().setCor(P);
irmaoPai.setCor(V);
RSD(irmaoPai);
```

---

## 🧠 Objetivo

Transformar:

```text
caso interno
```

em:

```text
caso externo
```

para cair no caso final.

---

## ⚫ Caso 4 — irmão preto com filho externo vermelho

Esse é o caso que resolve definitivamente.

Seu código:

```java
irmaoPai.setCor(pai.getCor());
pai.setCor(P);
irmaoPai.getFD().setCor(P);
RSE(pai);
```

ou versão espelhada.

---

## 🧠 Resultado estrutural

A rotação:

* redistribui altura negra
* elimina duplo negro
* restaura todas propriedades RN

Depois:

```java
no = raiz;
```

encerra o laço.

---

## 🔁 Remoção com dois filhos

Trecho:

```java
substituto = NoSubstituto(alvo.getFD());
```

Você utiliza:

* sucessor em ordem
* menor nó da subárvore direita

---

## 🧠 Ideia importante

O nó removido logicamente:

* permanece na árvore

Quem realmente sai:

* sucessor

Isso reduz remoção para:

* folha
  ou
* nó com um filho

Exatamente como BST clássica.

---

## 🧩 Método `substituir`

```java
private void substituir(NoRB antigo, NoRB substituto)
```

Esse método abstrai transplante de subárvores.

Responsabilidades:

* atualizar ponteiro do pai
* atualizar raiz
* atualizar pai do substituto

É equivalente ao `TRANSPLANT` do CLRS.

---

## 📌 Diferença estrutural AVL vs Rubro-Negra

| AVL                   | Rubro-Negra                   |
| --------------------- | ----------------------------- |
| controla altura exata | controla altura negra         |
| mais rotações         | menos rotações                |
| busca mais estável    | inserção/remoção mais rápidas |
| balanceamento rígido  | balanceamento relaxado        |

---

## ✅ Conclusão didática

Sua implementação segue corretamente a lógica clássica de árvores rubro-negras:

* inserção ABB + correção iterativa
* recoloração propagativa
* rotações LL/RR/LR/RL
* remoção via sucessor
* tratamento implícito de duplo negro
* rebalanceamento completo pós-remoção

Os pontos mais fortes estruturalmente são:

1. abstração limpa das rotações
2. uso inteligente de `cor(no)` para tratar NIL preto
3. rebalanceamento de remoção fiel ao algoritmo clássico
4. separação clara entre:

   * remoção física
   * correção estrutural
5. implementação totalmente simétrica entre esquerda/direita

Didaticamente, o núcleo real da implementação está em duas ideias:

* inserção resolve conflito vermelho-vermelho
* remoção resolve déficit de preto (duplo negro)
