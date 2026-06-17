# Implementation Plan: Supermercado Carrinho de Compras

## Overview

Implementação de um sistema de carrinho de compras de supermercado via console em **Java 21 puro**, organizado numa única classe `Supermercado.java` com métodos estáticos, arrays paralelos e um único `Scanner`. A implementação segue a ordem: estrutura do projeto → dados e validação → métodos de lógica → loops de interação → testes.

---

## Tasks

- [ ] 1. Configurar o projeto Maven e a estrutura de ficheiros
  - Criar `pom.xml` com Java 21, JUnit 5 (`junit-jupiter:5.10.2`) e jqwik (`jqwik:1.8.5`) como dependências de teste
  - Criar estrutura de pastas: `src/main/java/`, `src/test/java/`
  - Criar ficheiro vazio `src/main/java/Supermercado.java` com declaração da classe pública
  - Criar ficheiros vazios `src/test/java/SupermercadoTest.java` e `src/test/java/SupermercadoPropertyTest.java`
  - _Requirements: 1.1, 2.1_

- [ ] 2. Declarar arrays paralelos e implementar validação do catálogo
  - [ ] 2.1 Declarar os três arrays estáticos (`catalogoItens`, `tabelaPrecos`, `inventario`) com no mínimo 10 itens de supermercado, preços > 0.0 e stock inicial ≥ 0
    - Usar `static final` para `catalogoItens` e `tabelaPrecos`; `static` (sem `final`) para `inventario`
    - _Requirements: 1.1, 1.3, 1.4, 1.5, 1.6, 10.1_
  - [ ] 2.2 Implementar o método estático `validarCatalogo(String[] catalogoItens, float[] tabelaPrecos, int[] inventario)` que retorna `boolean`
    - Verificar: `catalogoItens.length` entre 10 e 25; comprimentos iguais entre os três arrays; nomes não nulos, não vazios e com no máximo 100 caracteres; preços > 0.0, ≤ 999999.99, não NaN e não Infinity; valores de `inventario` ≥ 0
    - _Requirements: 1.1, 1.2, 1.3, 1.5, 1.6, 10.1_
  - [ ]* 2.3 Escrever teste de propriedade para a Property 2: Validação rejeita tamanhos inválidos
    - **Property 2: Validação do catálogo rejeita tamanhos inválidos**
    - **Validates: Requirements 1.1, 1.2**
    - Usar `@Property` do jqwik para gerar arrays de comprimento fora de [10, 25] e verificar que `validarCatalogo` retorna `false`
  - [ ]* 2.4 Escrever teste de propriedade para a Property 3: Validação rejeita nomes e preços inválidos
    - **Property 3: Validação rejeita nomes e preços inválidos**
    - **Validates: Requirements 1.5, 1.6**
    - Gerar arrays com nomes vazios/nulos/> 100 chars ou preços ≤ 0, NaN, Infinity e verificar rejeição
  - [ ]* 2.5 Escrever teste de unidade para o alinhamento dos arrays (Property 1)
    - **Property 1: Alinhamento dos arrays paralelos**
    - **Validates: Requirements 1.3, 1.4, 10.1**
    - Verificar que `catalogoItens.length == tabelaPrecos.length == inventario.length` e que todos os valores de `inventario` são ≥ 0

- [ ] 3. Implementar `buscarItem` e os seus testes
  - [ ] 3.1 Implementar `buscarItem(String nomeItem, String[] catalogoItens)` retornando `int`
    - Retornar -1 imediatamente se `nomeItem` for nulo ou vazio (após `.trim()`)
    - Percorrer o array com loop `for` e comparar com `equalsIgnoreCase`
    - _Requirements: 5.1, 5.2, 5.3_
  - [ ]* 3.2 Escrever teste de propriedade para a Property 4: `buscarItem` localiza itens corretamente (case-insensitive)
    - **Property 4: buscarItem localiza itens corretamente (case-insensitive)**
    - **Validates: Requirements 5.1, 5.2**
    - Gerar catálogos arbitrários e variações de capitalização dos nomes; verificar que o índice retornado é o correto
  - [ ]* 3.3 Escrever teste de propriedade para a Property 5: `buscarItem` retorna -1 para itens ausentes
    - **Property 5: buscarItem retorna -1 para itens ausentes**
    - **Validates: Requirements 5.3**
    - Gerar strings que não estão no catálogo (incluindo nulas e vazias) e verificar retorno -1
  - [ ]* 3.4 Escrever testes de unidade para `buscarItem` com casos extremos
    - `buscarItem(null, catalogo)` → -1
    - `buscarItem("", catalogo)` → -1
    - `buscarItem("  ", catalogo)` → -1 (só espaços)
    - _Requirements: 5.3_

- [ ] 4. Implementar `calcularMediaPrecos` e os seus testes
  - [ ] 4.1 Implementar `calcularMediaPrecos(float[] tabelaPrecos)` retornando `float`
    - Retornar `0.0f` imediatamente se o array estiver vazio
    - Somar todos os elementos com loop `for-each` e dividir pelo comprimento
    - _Requirements: 7.1, 7.2, 7.3_
  - [ ]* 4.2 Escrever teste de propriedade para a Property 6: `calcularMediaPrecos` respeita a definição de média aritmética
    - **Property 6: calcularMediaPrecos respeita a definição de média aritmética**
    - **Validates: Requirements 7.2**
    - Para arrays com valores > 0.0, verificar que o resultado == soma/comprimento e está em [min, max] do array
  - [ ]* 4.3 Escrever teste de unidade para `calcularMediaPrecos` com array vazio
    - `calcularMediaPrecos(new float[]{})` → `0.0f`
    - _Requirements: 7.3_

- [ ] 5. Implementar `filtrarItensPorPreco` e os seus testes
  - [ ] 5.1 Implementar `filtrarItensPorPreco(String[] catalogoItens, float[] tabelaPrecos, float limiteMaximo)` com retorno `void`
    - Se `limiteMaximo <= 0.0`, exibir mensagem de erro e retornar sem filtrar
    - Usar arrays temporários para coletar itens filtrados (preço < limiteMaximo)
    - Ordenar os arrays temporários crescentemente por preço (bubble sort ou insertion sort com arrays primitivos)
    - Exibir cada item filtrado com nome e preço; se nenhum for encontrado, exibir mensagem informativa
    - Ignorar silenciosamente itens sem entrada em `tabelaPrecos` (índice fora dos limites)
    - _Requirements: 8.1, 8.2, 8.3, 8.4, 8.5_
  - [ ]* 5.2 Escrever teste de propriedade para a Property 7: `filtrarItensPorPreco` retorna apenas itens abaixo do limite, ordenados
    - **Property 7: filtrarItensPorPreco retorna apenas itens abaixo do limite, ordenados**
    - **Validates: Requirements 8.2**
    - Redirecionar `System.out` para `ByteArrayOutputStream`, capturar output e verificar: todos os preços exibidos < limite e ordenados crescentemente
  - [ ]* 5.3 Escrever teste de unidade para `filtrarItensPorPreco` com limite inválido
    - `filtrarItensPorPreco(c, p, -1.0f)` → exibe mensagem de erro sem listar itens
    - _Requirements: 8.4_

- [ ] 6. Implementar `aplicarDesconto` e os seus testes
  - [ ] 6.1 Implementar `aplicarDesconto(float totalSessao, int totalItens)` retornando `float`
    - Se `totalSessao < 0` ou `totalItens < 0`, exibir mensagem de erro e retornar `0.0f`
    - Calcular percentual de desconto: +10% se `totalSessao >= 100.0`; +5% se `totalItens >= 5`; máximo 15%
    - Calcular desconto sobre o `totalSessao` original, arredondar para 2 casas decimais (HALF_UP via `Math.round`)
    - Exibir: valor original, percentual aplicado (0%, 5%, 10% ou 15%) e valor final
    - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5, 9.6_
  - [ ]* 6.2 Escrever teste de propriedade para a Property 8: `aplicarDesconto` nunca excede 15% e respeita as regras acumuladas
    - **Property 8: aplicarDesconto nunca excede 15% de desconto e respeita as regras acumuladas**
    - **Validates: Requirements 9.2, 9.3, 9.4**
    - Para `totalSessao >= 0` e `totalItens >= 0`, verificar: desconto ≤ 15%; se totalSessao ≥ 100, desconto ≥ 10%; se totalItens ≥ 5, desconto ≥ 5%; retorno ≤ totalSessao
  - [ ]* 6.3 Escrever testes de unidade para `aplicarDesconto` com casos específicos
    - `aplicarDesconto(-1.0f, 3)` → `0.0f`
    - `aplicarDesconto(50.0f, 2)` → `50.0f` (sem desconto, 0%)
    - `aplicarDesconto(100.0f, 2)` → `90.0f` (10%)
    - `aplicarDesconto(50.0f, 5)` → `47.5f` (5%)
    - `aplicarDesconto(100.0f, 5)` → `85.0f` (15%)
    - _Requirements: 9.2, 9.3, 9.4, 9.6_

- [ ] 7. Checkpoint — Verificar que todos os testes passam até este ponto
  - Garantir que todos os testes passam, pedir ao utilizador que confirme antes de continuar.

- [ ] 8. Implementar os métodos auxiliares de leitura (`lerInteiroComRetry`, `lerNomeItem`)
  - [ ] 8.1 Implementar `lerInteiroComRetry(Scanner scanner, String prompt, int min, int max)` retornando `int`
    - Usar `scanner.nextLine().trim()` e `Integer.parseInt()` dentro de `try-catch (NumberFormatException)`
    - Repetir até 3 tentativas; retornar -1 se esgotado
    - Validar que o inteiro está no intervalo [min, max]
    - _Requirements: 2.3, 6.2, 6.5_
  - [ ] 8.2 Implementar `lerNomeItem(Scanner scanner)` retornando `String`
    - Ler com `scanner.nextLine().trim()`
    - Se vazio/branco, exibir mensagem de erro e repetir indefinidamente (sem limite)
    - Se input for "Complete" (case-insensitive), retornar "Complete"
    - _Requirements: 2.4, 4.1_
  - [ ]* 8.3 Escrever teste de unidade para retry de quantidade inválida esgotado
    - Simular 3 entradas não numéricas consecutivas e verificar que `lerInteiroComRetry` retorna -1
    - _Requirements: 2.3, 6.5_

- [ ] 9. Implementar o loop interno de adição de itens e a gestão de inventário
  - [ ] 9.1 Implementar o loop interno dentro do `main` (ou num método auxiliar `executarSessao`)
    - Inicializar `totalSessao = 0.0f` e `totalItens = 0` no início de cada sessão
    - Chamar `lerNomeItem` para obter o nome; se "Complete", sair do loop
    - Chamar `buscarItem` com retry (máx 3 tentativas por item não encontrado); cancelar se esgotado
    - Exibir preço unitário e stock disponível (`inventario[indice]`) antes de pedir quantidade
    - Chamar `lerInteiroComRetry` para quantidade no intervalo [1, 999]
    - Verificar `quantidade <= inventario[indice]`; se não, exibir stock disponível e repetir pedido (máx 3 tentativas)
    - Calcular `totalItem = precoUnitario * quantidade`, decrementar `inventario[indice]`, acumular `totalSessao`, incrementar `totalItens`
    - _Requirements: 4.1, 4.2, 4.3, 4.4, 5.4, 5.5, 6.1, 6.2, 6.3, 6.4, 10.2, 10.3, 10.4, 10.5_
  - [ ]* 9.2 Escrever teste de propriedade para a Property 9: Acumulação correta do total da sessão
    - **Property 9: Acumulação correta do total da sessão**
    - **Validates: Requirements 4.5, 6.3, 6.4**
    - Gerar listas arbitrárias de pares (quantidade, preço) e verificar que a soma acumulada é exatamente igual à soma de `quantidade_i * preço_i`
  - [ ]* 9.3 Escrever teste de propriedade para a Property 10: Decremento correto do inventário
    - **Property 10: Decremento correto do inventário após compra**
    - **Validates: Requirements 10.2, 10.4**
    - Para um índice e quantidade válida (q ≤ inventario[i]), verificar que após a compra `inventario[i]` == valor_anterior - q
  - [ ]* 9.4 Escrever teste de propriedade para a Property 11: Stock nunca fica negativo
    - **Property 11: Stock nunca fica negativo**
    - **Validates: Requirements 10.2, 10.4**
    - Para qualquer sequência de compras válidas aceites pelo sistema, verificar que nenhum elemento de `inventario` fica negativo

- [ ] 10. Implementar o loop externo de sessões e o `main` completo
  - [ ] 10.1 Implementar o loop externo no método `main`
    - Chamar `validarCatalogo` antes de qualquer loop; se inválido, `System.err.println` + `System.exit(1)`
    - Criar o `Scanner` único associado a `System.in`
    - Loop externo: ler input inicial; se vazio/branco, exibir erro e repetir; se "Exit" (case-insensitive), encerrar; caso contrário, exibir boas-vindas e entrar no loop interno
    - No fim do loop interno, chamar `aplicarDesconto(totalSessao, totalItens)` e exibir o resultado
    - Chamar `calcularMediaPrecos` e `filtrarItensPorPreco` no momento adequado (ex: ao início de cada sessão ou como opção de menu)
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 4.5, 7.4_

- [ ] 11. Checkpoint final — Garantir que todos os testes passam e o fluxo console funciona
  - Garantir que todos os testes passam, pedir ao utilizador que confirme antes de concluir.

---

## Notes

- Tarefas marcadas com `*` são opcionais e podem ser ignoradas para um MVP mais rápido
- Cada tarefa referencia requisitos específicos para rastreabilidade
- Os testes de propriedade usam **jqwik** com `@Property` e mínimo de 100 iterações (`@Property(tries = 100)`)
- Cada teste de propriedade deve incluir um comentário: `// Feature: supermercado-carrinho-compras, Property N: <título>`
- A estratégia de leitura usa **exclusivamente `scanner.nextLine()`** para evitar o bug do `\n` residual do `nextInt()`
- Os checkpoints nos itens 7 e 11 garantem validação incremental antes de avançar

---

## Task Dependency Graph

```json
{
  "waves": [
    { "id": 0, "tasks": ["1"] },
    { "id": 1, "tasks": ["2.1", "2.2"] },
    { "id": 2, "tasks": ["2.3", "2.4", "2.5", "3.1", "4.1", "5.1", "6.1"] },
    { "id": 3, "tasks": ["3.2", "3.3", "3.4", "4.2", "4.3", "5.2", "5.3", "6.2", "6.3"] },
    { "id": 4, "tasks": ["8.1", "8.2"] },
    { "id": 5, "tasks": ["8.3", "9.1"] },
    { "id": 6, "tasks": ["9.2", "9.3", "9.4"] },
    { "id": 7, "tasks": ["10.1"] }
  ]
}
```
