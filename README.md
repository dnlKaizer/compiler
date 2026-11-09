# COMCET — Compilador MiniPascal para Bytecode JVM

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?logo=openjdk&logoColor=white)
![Bytecode Target](https://img.shields.io/badge/JVM_Bytecode-Java_11_(V11)-007396)
![ANTLR4](https://img.shields.io/badge/ANTLR-4.13.1-D32F2F)
![OW2 ASM](https://img.shields.io/badge/OW2_ASM-9.6-5C6BC0)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit-5.10.1-25A162?logo=junit5&logoColor=white)

Compilador completo para um subconjunto estruturado da linguagem **MiniPascal**, desenvolvido em Java com arquitetura modular dividida em *front-end* (ANTLR4, AST tipada e análise semântica baseada em *Visitors*) e *back-end* (síntese direta de bytecode via OW2 ASM).
O compilador traduz programas-fonte `.pas` diretamente para arquivos de classe nativos da Máquina Virtual Java (`.class`), prontos para execução via comando `java`.

---

## 1. Pipeline do Compilador (Arquitetura)

O processo de compilação é orquestrado pela classe `br.com.comcet.Compiler` em cinco estágios sequenciais. A detecção de erros léxicos, sintáticos ou semânticos interrompe imediatamente a transição para as fases seguintes.

```mermaid
flowchart LR
    A["Código-Fonte (.pas)"] --> B["1. Lexer & Parser (ANTLR4)"]
    B -->|ParseTree| C["2. Construtor de AST (Visitor)"]
    C -->|Program AST| D["3. Analisador Semântico"]
    D -->|AST Anotada & Validada| E["4. Gerador de Bytecode (ASM)"]
    E --> F["Bytecode JVM (.class)"]
```

### Estágios e Componentes

| Fase | Estágio | Classes Principais | Ferramentas / Bibliotecas | Responsabilidade Técnica |
| :--- | :--- | :--- | :--- | :--- |
| **Front-end** | **1. Análise Léxica e Sintática** | `MiniPascal.g4`<br>`MiniPascalLexer`<br>`MiniPascalParser`<br>`AntlrErrorCollector` | **ANTLR 4.13.1** (`antlr4-maven-plugin`) | Converte o fluxo de caracteres (`CharStream`) em tokens e constrói a árvore de derivação (`ParseTree`). Intercepta falhas léxicas e sintáticas via `BaseErrorListener` customizado. |
| **Front-end** | **2. Construção da AST (RI)** | `MiniPascalAntlrVisitor`<br>`Program`, `AstNode`, `NodeList`<br>`Command`, `Declaration`, `Expression` | Java 17 + Padrão *Visitor* do ANTLR4 | Percorre a `ParseTree` e instancia a Árvore Sintática Abstrata (AST) fortemente tipada como Representação Intermediária (RI), preservando metadados de linha e coluna. |
| **Front-end** | **3. Análise Semântica** | `SemanticAnalyzer`<br>`DeclarationAnalyzer`<br>`CommandAnalyzer`<br>`ExpressionAnalyzer`<br>`SymbolTable`, `SemanticContext` | Padrão *Visitor* (`DeclarationVisitor`, `CommandVisitor`, `ExpressionVisitor`) | Gerencia escopos aninhados em pilha (`Stack<Map<String, ISymbol>>`), verifica declaração prévia de identificadores, infere e valida tipos em expressões, checa assinaturas de sub-rotinas e garante inicialização de retorno em funções. |
| **Back-end** | **4. Geração de Código (Bytecode)** | `CodeGen`<br>`DeclarationCodeGen`<br>`CommandCodeGen`<br>`ExpressionCodeGen`<br>`SubroutineManager`, `VariableManager`, `ScannerManager`<br>`DescriptorMapper`, `OpcodeMapper`, `ScannerMapper` | **OW2 ASM 9.6** (`ClassWriter`, `MethodVisitor`, `Opcodes`) | Traduz a AST validada em instruções de bytecode JVM (versão `V11` / Java 11) com cálculo automático de *stack map frames* e tamanho máximo de pilha/locais (`COMPUTE_FRAMES \| COMPUTE_MAXS`), gerando `<NomeDoPrograma>.class`. |

### Estrutura de Pacotes (`comcet/src/main/java/br/com/comcet`)

```text
br.com.comcet
├── App.java                          # Ponto de entrada CLI
├── Compiler.java                     # Orquestrador do pipeline de compilação
├── frontend
│   ├── parser                        # Gramática MiniPascal.g4 e Visitor de construção da AST
│   ├── ast                           # Nós da AST (program, declaration, command, expression)
│   ├── semantic                      # Analisadores semânticos e Tabela de Símbolos (symbol/*)
│   ├── type                          # Enumerações de tipos (VariableType) e operadores (OperatorType)
│   └── exception                     # Hierarquia de exceções e coletores de erros
└── backend
    ├── bytecode                      # Visitors geradores de bytecode (CodeGen, Command/Declaration/ExpressionCodeGen)
    ├── context                       # Contextos de classe/método e tabela de índices de variáveis locais/globais
    ├── manager                       # Emissão de métodos, variáveis estáticas/locais e leitura via java.util.Scanner
    └── mapper                        # Mapeamento de tipos MiniPascal para descritores e opcodes da JVM
```

---

## 2. Subconjunto MiniPascal Suportado (Gramática & Tipos)

A especificação léxica e sintática está definida em `comcet/src/main/java/br/com/comcet/frontend/parser/MiniPascal.g4`.

### Tipos de Dados Primitivos

| Tipo MiniPascal | Literais Suportados | Descritor JVM | Tipo Java Equivalente | Armazenamento Local (Opcodes) |
| :--- | :--- | :--- | :--- | :--- |
| `integer` | Sequências numéricas `[0-9]+` (ex.: `0`, `42`) | `I` | `int` | `ILOAD` / `ISTORE` / `IRETURN` |
| `boolean` | `true`, `false` | `Z` | `boolean` (`iconst_1` / `iconst_0`) | `ILOAD` / `ISTORE` / `IRETURN` |
| `string` | Aspas simples `'...'` com escapes `\n`, `\t`, `\r`, `\\`, `\"` | `Ljava/lang/String;` | `java.lang.String` | `ALOAD` / `ASTORE` / `ARETURN` |
| *(interno)* `void` | N/A (retorno de `procedure`) | `V` | `void` | `RETURN` |

> **Nota sobre o subconjunto:** Tipos compostos como `array`, registros (`record`), números de ponto flutuante (`real`) e o laço `for` não fazem parte da gramática deste subconjunto.

### Operadores e Precedência

Listados da maior para a menor precedência conforme a gramática:

| Nível | Categoria | Operadores | Tipos de Operandos Aceitos | Tipo Resultante | Instrução JVM Gerada |
| :---: | :--- | :--- | :--- | :--- | :--- |
| **1** | Agrupamento / Unário Lógico | `( expr )`, `not` | `not`: `boolean` | `boolean` | `ICONST_1` + `IXOR` |
| **2** | Multiplicativo / `and` Lógico | `*`, `/` | `integer`, `integer` | `integer` | `IMUL`, `IDIV` |
| **2** | Multiplicativo / `and` Lógico | `and` | `boolean`, `boolean` | `boolean` | `IAND` |
| **3** | Aditivo / Concatenação | `+` | `integer, integer` ou `string, string` | `integer` ou `string` | `IADD` ou `String.concat` (`INVOKEVIRTUAL`) |
| **3** | Aditivo | `-` | `integer`, `integer` | `integer` | `ISUB` |
| **3** | Disjunção Lógica | `or`, `xor` | `boolean`, `boolean` | `boolean` | `IOR`, `IXOR` |
| **4** | Igualdade | `=`, `<>` | `(integer, integer)` ou `(boolean, boolean)` | `boolean` | `IF_ICMPEQ`, `IF_ICMPNE` + salto condicional |
| **4** | Relacional | `<`, `>`, `<=`, `>=` | `integer`, `integer` | `boolean` | `IF_ICMPLT`, `IF_ICMPGT`, `IF_ICMPLE`, `IF_ICMPGE` |

### Comandos e Estruturas de Controle

- **Bloco Composto (`begin ... end`)**: Agrupa múltiplos comandos separados por `;` (ponto e vírgula opcional antes de `end`).
- **Atribuição (`:=`)**: `identificador := expressao`.
- **Condicional (`if / then / else`)**:
  - `if <condicao_booleana> then <comando>`
  - `if <condicao_booleana> then <comando> else <comando>`
- **Laço Pré-testado (`while / do`)**:
  - `while <condicao_booleana> do <comando>` (avalia a condição antes de cada iteração; salta via `IFEQ` para fora do laço).
- **Laço Pós-testado (`repeat / until`)**:
  - `repeat <comando> until <condicao_booleana>` (executa o corpo ao menos uma vez e repete enquanto a condição for falsa via `IFEQ`).
- **Entrada de Dados (`read` / `readln`)**:
  - `read(id)` e `readln(id)`: utilizam uma instância estática de `java.util.Scanner` (`$scanner`) inicializada no método `main`. Valores `integer` e `boolean` são convertidos via `Integer.parseInt` e `Boolean.parseBoolean`. O comando `readln` consome a quebra de linha residual com `Scanner.nextLine()`.
- **Saída de Dados (`write` / `writeln`)**:
  - `write(arg1, arg2, ...)` e `writeln(arg1, arg2, ...)`: aceitam múltiplos argumentos de qualquer tipo primitivo (`integer`, `boolean`, `string`), convertendo automaticamente tipos não-string via `String.valueOf(...)` antes de invocar `System.out.print` / `System.out.println`.

### Declarações, Escopos e Sub-rotinas

- **Variáveis Globais**: Declaradas na seção `var` do programa principal e compiladas como campos estáticos públicos (`public static`) na classe JVM gerada (`GETSTATIC` / `PUTSTATIC`).
- **Sub-rotinas (`function` e `procedure`)**:
  - Suportam lista de parâmetros formais (`a, b: integer; s: string`) e seção `var` própria para variáveis locais.
  - Compiladas como métodos `public static` na classe principal e invocadas via `INVOKESTATIC`.
  - **Escopo e *Shadowing***: Parâmetros e variáveis locais são alocados na tabela de variáveis locais do frame do método na JVM (índices `0..N`), permitindo sombrear (*shadow*) variáveis globais homônimas sem colisão.
  - **Retorno de Função**: Segue a convenção clássica do Pascal, onde o valor de retorno é atribuído a uma variável local implícita com o mesmo nome da `function` (`nomeDaFuncao := valor`). Ao final da execução do corpo, o valor dessa variável é empilhado e retornado (`IRETURN` ou `ARETURN`).
  - **Recursão**: O analisador semântico suporta chamadas recursivas consultando o `MethodSymbol` no escopo pai (`getSymbolFromFatherScope`) quando o identificador no escopo local referencia a variável de retorno da própria função.
  - **Chamada como Comando (`CallCommand`)**: Caso uma `function` seja chamada como instrução isolada (descartando o retorno), o gerador de bytecode emite uma instrução `POP` para manter o equilíbrio da pilha de operandos.
- **Comentários**: Suporta comentários de bloco `{ ... }` e `(* ... *)`.

### Tratamento de Erros e Diagnósticos

Todas as exceções herdam de `CompilerException` e formatam diagnósticos padronizados com localização exata `(linha: X, coluna: Y)`:

1. **Erros Léxicos (`LexicalException`)**: Capturados pelo `AntlrErrorCollector` acoplado ao `MiniPascalLexer` (ex.: caracteres inválidos ou literais de string não fechados).
2. **Erros Sintáticos (`SyntaticException`)**: Capturados pelo `AntlrErrorCollector` acoplado ao `MiniPascalParser` quando a sequência de tokens viola a gramática `MiniPascal.g4`.
3. **Erros Semânticos (`SemanticException`)**: Coletados em fila (`SemanticExceptionQueue`) durante a travessia da AST, permitindo reportar múltiplos erros semânticos em uma única execução sem efeito cascata (graças à propagação do tipo sentinela `VariableType.ERROR`):
   - Uso de variável ou sub-rotina não declarada no escopo visível.
   - Incompatibilidade de tipos em atribuições (`:=`) ou em operações unárias/binárias.
   - Expressão de condição em `if`, `while` ou `repeat` cujo tipo resultante não seja `boolean`.
   - Divergência no número de argumentos (aridade) ou nos tipos dos argumentos passados para `function` / `procedure`.
   - Tentativa de atribuir/ler em identificador que não representa uma variável, ou invocar identificador que não representa uma sub-rotina.
   - Função (`function`) cujo corpo termina sem atribuir um valor de retorno ao seu identificador.

---

## 3. Tecnologias e Pré-requisitos

| Componente | Requisito / Versão | Observação |
| :--- | :--- | :--- |
| **Java Development Kit (JDK)** | **Java 17** ou superior | Definido em `maven.compiler.source` e `maven.compiler.target` (`17`). O pipeline CI utiliza Eclipse Temurin 21. |
| **Apache Maven** | **3.8+** (recomendado `3.9.6`) | Gerenciamento de ciclo de vida, geração de fontes ANTLR4 e empacotamento. |
| **ANTLR4** | `4.13.1` | `org.antlr:antlr4-runtime` e `org.antlr:antlr4-maven-plugin`. |
| **OW2 ASM** | `9.6` | `org.ow2.asm:asm` para geração de bytecode JVM (`Opcodes.V11`). |
| **JUnit Jupiter** | `5.10.1` | `org.junit.jupiter:junit-jupiter` (escopo de teste). |

---

## 4. Instalação, Compilação e Execução

### 1. Clonar o repositório e compilar o projeto

A partir da raiz do repositório:

```bash
git clone <url-do-repositorio>
cd compiler-gitlab
mvn -f comcet/pom.xml clean package
```

> Durante a fase `generate-sources`, o plugin `antlr4-maven-plugin` processa automaticamente a gramática `MiniPascal.g4` e gera as classes do Lexer, Parser e Visitor em `comcet/target/generated-sources/antlr4`.

### 2. Compilar um arquivo-fonte MiniPascal (`.pas`)

O compilador aceita o caminho do arquivo `.pas` via flag `-f`, como argumento posicional direto ou de forma interativa via entrada padrão (`stdin`).

Executando a partir do diretório `comcet/`:

```bash
cd comcet
mvn exec:java -Dexec.mainClass="br.com.comcet.App" -Dexec.args="-f teste.pas"
```

Ou a partir da raiz do repositório:

```bash
mvn -f comcet/pom.xml exec:java -Dexec.mainClass="br.com.comcet.App" -Dexec.args="-f comcet/teste.pas"
```

O arquivo de bytecode gerado recebe exatamente o identificador declarado no cabeçalho `program <Nome>;` (por exemplo, `program Teste;` gera `Teste.class` no diretório de trabalho atual).

### 3. Executar o bytecode gerado na JVM

Após a geração do arquivo `.class`, execute-o diretamente com o binário `java`:

```bash
java Teste
```

*(Caso o arquivo `Teste.class` tenha sido gerado dentro de `comcet/`, execute `java -cp comcet Teste` a partir da raiz ou `java Teste` de dentro de `comcet/`).*

---

## 5. Exemplo Prático

### Código-Fonte MiniPascal (`comcet/teste.pas`)

O arquivo `comcet/teste.pas` exercita variáveis globais e locais, *shadowing* de escopo, funções (`integer`, `string`, `boolean`), procedimentos, expressões aritméticas/lógicas/relacionais, estruturas `if/then/else`, `while`, `repeat/until` e comandos de E/S (`readln`, `write`, `writeln`):

```pascal
program Teste;

var
  s, nome: string;
  n, total, i: integer;
  p, achou: boolean;

function soma(a, b: integer): integer;
begin
  soma := a + b
end;

function saudacao(nome: string; idade: integer): string;
var
  prefixo: string;
begin
  prefixo := 'Ola, ';
  saudacao := prefixo + nome
end;

function ehPar(x: integer): boolean;
begin
  ehPar := (x = (x / 2) * 2)
end;

procedure linha();
begin
  writeln('--------------------')
end;

(* main *)
begin
  linha();

  s := 'Testando o compilador';
  writeln(s);

  total := soma(7, 5);
  write('soma(7, 5) = ');
  writeln(total);

  nome := 'Mundo';
  s := saudacao(nome, 10);
  writeln(s);

  if total > 10 then
    writeln('total e maior que 10')
  else
    writeln('total nao e maior que 10');

  i := 0;
  total := 0;
  while i < 5 do
  begin
    total := total + i;
    i := i + 1
  end;
  write('soma de 0 a 4 (while) = ');
  writeln(total);

  n := 8;
  if ehPar(n) then
    writeln('n e par')
  else
    writeln('n e impar');

  linha();
  writeln('Fim do teste.')
end.
```

### Compilando o Exemplo

```bash
cd comcet
mvn exec:java -Dexec.mainClass="br.com.comcet.App" -Dexec.args="-f teste.pas"
```

**Saída do Compilador:**

```text
LOG: Iniciando compilação...
LOG: Fazendo análise léxica e sintática...
LOG: Criando AST...
LOG: Analisando AST semanticamente...
LOG: Gerando código...
LOG: Código gerado com sucesso.
```

### Inspeção do Bytecode JVM Gerado (`javap -c Teste.class`)

```java
public class Teste {
  public static java.lang.String s;
  public static java.lang.String nome;
  public static int n;
  public static int total;
  public static int i;
  public static boolean p;
  public static boolean achou;

  public static int soma(int, int);
    Code:
       0: iload_0
       1: iload_1
       2: iadd
       3: istore_2
       4: iload_2
       5: ireturn

  public static java.lang.String saudacao(java.lang.String, int);
    Code:
       0: ldc           #20                 // String Ola, 
       2: astore_3
       3: aload_3
       4: aload_0
       5: invokevirtual #26                 // Method java/lang/String.concat:(Ljava/lang/String;)Ljava/lang/String;
       8: astore_2
       9: aload_2
      10: areturn
...
```

### Executando o Programa Compilado (`java Teste`)

```text
--------------------
Testando o compilador
soma(7, 5) = 12
Ola, Mundo
total e maior que 10
total e exatamente 12
p verdadeiro e achou falso
p ou achou e verdadeiro
p xor achou e verdadeiro
total esta entre 12 e 20
total e diferente de zero
soma de 0 a 4 (while) = 10
i = 0
i = 1
i = 2
--------------------
n e par
Resumo: total=10, i=3, p=true
Digite seu nome: Danilo
Digite sua idade (numero inteiro): 21
Voce gosta de Pascal? (true/false): true
Ola Danilo, voce tem 21 anos e gosta de Pascal!
--------------------
Fim do teste.
```

---
