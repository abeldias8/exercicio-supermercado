# Requirements Document

## Introduction

Este projeto é um exercício académico em Java 21 puro (sem frameworks) que implementa um sistema de carrinho de compras de supermercado via console. O objetivo é consolidar os conceitos de Arrays, Loops, Scanner, métodos estáticos e estruturas de controlo de fluxo. O sistema permite que um utilizador navegue por sessões de compras, adicione itens ao carrinho, visualize o total da conta, aplique descontos e consulte informações sobre o inventário e preços do catálogo.

## Glossary

- **Sistema**: A aplicação Java de console do supermercado, ponto de entrada `main`.
- **Catálogo**: O array de Strings com os nomes dos itens disponíveis no supermercado.
- **Tabela_de_Precos**: O array de `float` com os preços unitários, alinhado por índice com o Catálogo.
- **Inventário**: O array de `int` com as quantidades disponíveis em stock, alinhado por índice com o Catálogo.
- **Carrinho**: A estrutura de dados temporária (acumulador) que regista itens, quantidades e totais da sessão atual.
- **Sessão**: Um ciclo de compra que começa com o loop interno e termina quando o utilizador digita "Complete".
- **Scanner**: O objeto `java.util.Scanner` usado para ler entrada do utilizador a partir de `System.in`.
- **Indice**: A posição numérica de um item nos arrays paralelos (Catálogo, Tabela_de_Precos e Inventário).

---

## Requirements

### Requirement 1: Catálogo de Itens e Tabela de Preços

**User Story:** Como estudante, quero definir um catálogo fixo de itens e seus preços, para que o sistema tenha uma base de dados de produtos consultável.

#### Acceptance Criteria

1. THE Sistema SHALL declarar um array de Strings chamado `catalogoItens` com no mínimo 10 e no máximo 25 itens de supermercado.
2. IF o array `catalogoItens` for declarado com menos de 10 ou mais de 25 elementos, THEN THE Sistema SHALL rejeitar o catálogo inteiramente e encerrar a execução com uma mensagem de erro indicando o motivo.
3. THE Sistema SHALL declarar um array de `float` chamado `tabelaPrecos` com o mesmo número de elementos que `catalogoItens`.
4. THE Sistema SHALL garantir que cada posição de índice em `tabelaPrecos` corresponde ao preço unitário do item na mesma posição em `catalogoItens`.
5. THE Sistema SHALL declarar todos os preços em `tabelaPrecos` com valores maiores que `0.0` e no máximo `999999.99`; valores não finitos (NaN, Infinity) não são permitidos.
6. THE Sistema SHALL garantir que cada nome em `catalogoItens` seja não vazio e tenha no máximo 100 caracteres.

---

### Requirement 2: Leitura de Dados via Console

**User Story:** Como utilizador, quero interagir com o sistema via teclado, para que eu possa inserir nomes de itens e quantidades durante a sessão de compras.

#### Acceptance Criteria

1. WHEN o programa é iniciado, THE Sistema SHALL criar um único objeto `Scanner` associado a `System.in`, usando `java.util.Scanner`, antes do início de qualquer loop.
2. THE Sistema SHALL utilizar o mesmo objeto `Scanner` durante toda a execução do programa, sem criar instâncias adicionais.
3. IF o utilizador introduzir uma quantidade não numérica onde se espera um número inteiro, THEN THE Sistema SHALL exibir uma mensagem de erro e solicitar a entrada novamente, até um máximo de 3 tentativas consecutivas; se excedido, o item é descartado.
4. IF o utilizador introduzir um nome de item vazio ou em branco, THEN THE Sistema SHALL exibir uma mensagem de erro e solicitar uma nova entrada sem avançar no fluxo de compra.

---

### Requirement 3: Loop Externo de Sessões (Controlo por "Exit")

**User Story:** Como utilizador, quero poder realizar múltiplas sessões de compras consecutivas, para que eu possa simular várias idas ao supermercado numa única execução do programa.

#### Acceptance Criteria

1. WHEN o programa é iniciado, THE Sistema SHALL entrar imediatamente num loop externo de sessões de compras que se repete até o utilizador solicitar saída.
2. WHEN o utilizador digitar "Exit" (sem distinção de maiúsculas/minúsculas) no início de uma nova sessão, THE Sistema SHALL encerrar o loop externo e terminar a execução sem exibir mensagem de despedida.
3. WHEN o loop externo inicia uma nova iteração, THE Sistema SHALL exibir uma mensagem de boas-vindas ao utilizador.
4. IF o utilizador introduzir uma entrada vazia ou em branco no prompt de início de sessão, THEN THE Sistema SHALL exibir uma mensagem de erro e repetir o prompt sem avançar.

---

### Requirement 4: Loop Interno de Adição de Itens (Controlo por "Complete")

**User Story:** Como utilizador, quero adicionar múltiplos itens ao meu carrinho durante uma sessão, para que eu possa compor a minha lista de compras antes de ver o total.

#### Acceptance Criteria

1. WHEN uma sessão de compras é iniciada, THE Sistema SHALL aceitar entradas de itens continuamente até o utilizador digitar "Complete" (sem distinção de maiúsculas/minúsculas).
2. WHEN o utilizador digitar "Complete" durante o loop interno, THE Sistema SHALL encerrar o loop interno e calcular o total da sessão.
3. WHILE o loop interno estiver ativo, THE Sistema SHALL solicitar ao utilizador o nome do item que deseja adicionar, e após item válido, solicitar a quantidade (inteiro ≥ 1) e o preço unitário (numérico > 0, até 2 casas decimais).
4. IF o utilizador introduzir uma quantidade menor que 1 ou um preço unitário inválido (≤ 0), THEN THE Sistema SHALL exibir uma mensagem de erro, rejeitar o item e solicitar novamente o nome do próximo item.
5. WHEN o loop interno encerrar, THE Sistema SHALL exibir imediatamente o valor total acumulado da sessão, calculado como a soma de (quantidade × preço unitário) de todos os itens válidos adicionados.

---

### Requirement 5: Busca de Item no Catálogo

**User Story:** Como utilizador, quero que o sistema valide se o item que digitei existe no catálogo, para que eu não adicione produtos inexistentes ao carrinho.

#### Acceptance Criteria

1. THE Sistema SHALL disponibilizar um método estático chamado `buscarItem` que recebe uma String não nula e não vazia (nome do item) e o array `catalogoItens` como parâmetros, e retorna o índice (`int`) do item no array.
2. WHEN o método `buscarItem` é chamado com um nome não nulo e não vazio que existe no `catalogoItens` (sem distinção de maiúsculas/minúsculas), THE Sistema SHALL retornar o índice correspondente.
3. IF o nome fornecido ao método `buscarItem` não corresponder a nenhum item em `catalogoItens`, ou for nulo ou vazio, THEN THE Sistema SHALL retornar `-1`.
4. WHEN o utilizador digitar um nome cujo resultado de `buscarItem` seja `-1`, THE Sistema SHALL exibir uma mensagem informando que o item não foi encontrado e solicitar uma nova entrada, até um máximo de 3 tentativas consecutivas; após excedido, a operação é cancelada sem adicionar ao carrinho.
5. WHEN um item válido é encontrado, THE Sistema SHALL armazenar o nome do item e o seu índice antes de solicitar qualquer nova entrada ao utilizador.

---

### Requirement 6: Busca de Preço e Acumulação do Total

**User Story:** Como utilizador, quero que o sistema calcule automaticamente o custo por item e o acumule no total da sessão, para que eu saiba quanto estou gastando.

#### Acceptance Criteria

1. WHEN um item válido é selecionado, THE Sistema SHALL recuperar o preço unitário do item acedendo à posição do índice correspondente em `tabelaPrecos` e exibi-lo ao utilizador.
2. THE Sistema SHALL solicitar ao utilizador a quantidade desejada do item como um número inteiro no intervalo de 1 a 999.
3. THE Sistema SHALL calcular o valor total do item multiplicando o preço unitário pela quantidade informada e exibir o resultado ao utilizador.
4. THE Sistema SHALL acumular o valor total de cada item numa variável `totalSessao` do tipo `float`.
5. IF a quantidade informada pelo utilizador for menor que 1 ou maior que 999, ou não for um número inteiro válido, THEN THE Sistema SHALL exibir uma mensagem de erro e solicitar a quantidade novamente, até um máximo de 3 tentativas; se excedido, o item é descartado.

---

### Requirement 7: Cálculo da Média de Preços

**User Story:** Como utilizador, quero consultar a média de preços dos itens do catálogo, para que eu tenha uma referência do nível de preços do supermercado.

#### Acceptance Criteria

1. THE Sistema SHALL disponibilizar um método estático chamado `calcularMediaPrecos` que recebe o array `tabelaPrecos` como parâmetro e retorna um `float`.
2. WHEN o método `calcularMediaPrecos` é chamado com um array não vazio, THE Sistema SHALL produzir e retornar a média aritmética dos valores do array.
3. IF o array `tabelaPrecos` estiver vazio, THEN THE Sistema SHALL retornar `0.0`.
4. WHEN o método `calcularMediaPrecos` retorna um valor, THE Sistema SHALL exibir esse resultado no console formatado com duas casas decimais usando arredondamento HALF_UP.

---

### Requirement 8: Filtrar Itens por Preço Máximo

**User Story:** Como utilizador, quero filtrar e visualizar apenas os itens com preço abaixo de um valor que eu especificar, para que eu possa identificar produtos dentro do meu orçamento.

#### Acceptance Criteria

1. THE Sistema SHALL disponibilizar um método estático chamado `filtrarItensPorPreco` que recebe o array `catalogoItens`, o array `tabelaPrecos` e um valor `float` de limite máximo como parâmetros.
2. WHEN o método `filtrarItensPorPreco` é chamado com um limite maior que `0.0`, THE Sistema SHALL percorrer todos os itens e exibir apenas aqueles cujo preço unitário seja estritamente menor que o valor limite, mostrando o nome e o preço unitário de cada item, ordenados de forma crescente por preço.
3. IF, após comparar todos os preços com o valor limite, nenhum item tiver preço abaixo do valor limite, THEN THE Sistema SHALL exibir uma mensagem informando que nenhum item foi encontrado dentro do critério.
4. IF o valor limite fornecido for menor ou igual a `0.0`, THEN THE Sistema SHALL exibir uma mensagem de erro e não executar a filtragem.
5. IF um item do catálogo não possuir entrada correspondente em `tabelaPrecos`, THEN THE Sistema SHALL ignorar esse item silenciosamente, sem incluí-lo nos resultados.

---

### Requirement 9: Aplicação de Descontos Condicionais

**User Story:** Como utilizador, quero receber descontos automáticos quando a minha compra atingir certas condições, para que eu seja recompensado por compras de maior valor ou volume.

#### Acceptance Criteria

1. THE Sistema SHALL disponibilizar um método estático chamado `aplicarDesconto` que recebe o `totalSessao` (`float`) e o número total de itens adicionados (`int`) e retorna o valor final com desconto (`float`), arredondado para duas casas decimais.
2. IF o `totalSessao` for maior ou igual a `100.00`, THEN THE Sistema SHALL aplicar um desconto de 10% calculado sobre o `totalSessao` original.
3. IF o número total de itens adicionados na sessão for maior ou igual a `5`, THEN THE Sistema SHALL aplicar um desconto adicional de 5% calculado sobre o `totalSessao` original (acumulável com o desconto por valor, resultando em no máximo 15% de desconto total).
4. IF nenhuma condição de desconto for atingida, THEN THE Sistema SHALL retornar o `totalSessao` sem alteração.
5. WHEN o método `aplicarDesconto` é executado, THE Sistema SHALL exibir no console o valor original, o percentual de desconto aplicado (0%, 5%, 10% ou 15%) e o valor final com desconto.
6. IF o `totalSessao` for negativo ou o número de itens for negativo, THEN THE Sistema SHALL exibir uma mensagem de erro e retornar `0.0` sem aplicar descontos.

---

### Requirement 10: Gestão de Inventário (Controlo de Stock)

**User Story:** Como utilizador, quero que o sistema controle o stock disponível de cada item, para que eu não possa comprar mais do que existe em inventário.

#### Acceptance Criteria

1. THE Sistema SHALL declarar um array de `int` chamado `inventario` com o mesmo número de elementos que `catalogoItens`, representando a quantidade disponível de cada item em stock; todos os valores iniciais devem ser maiores ou iguais a `0`.
2. WHEN um item válido é selecionado, THE Sistema SHALL verificar se a quantidade solicitada pelo utilizador é menor ou igual à quantidade disponível no `inventario` para aquele índice, antes de aceitar a compra.
3. IF a quantidade solicitada exceder a quantidade disponível no `inventario`, THEN THE Sistema SHALL exibir uma mensagem indicando o stock disponível e solicitar uma nova quantidade.
4. WHEN a compra de um item é confirmada, THE Sistema SHALL decrementar a quantidade correspondente no array `inventario` pelo valor da quantidade comprada.
5. WHEN o utilizador solicita um item válido, THE Sistema SHALL exibir a quantidade em stock antes de pedir a quantidade ao utilizador.
