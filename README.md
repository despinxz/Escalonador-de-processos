# Escalonador de processos
Este repositório contém a implementação de um escalonador de processos em Java. 

## Autores
- Ana Clara das Neves Barreto
- Eloisa Antero Guisse
- Jamyle Gonçalves Rodrigues Silva
- Rafael Varago de Castro
- Sarah Klock Mauricio

## Classes
- **BCP:** Representa um processo. Armazena os atributos de program counter, dos registradores, comandos a serem executados, tempo de espera na fila de bloqueio etc;
- **Escalonador:**  * A classe Escalonador representa um escalonador de processos que executa programas com base em um quantum definido. Ela gerencia uma tabela de processos, uma fila de processos prontos e uma fila de processos bloqueados. Esta classe permite ler programas a partir de arquivos de texto, executá-los de acordo com o quantum e gerar logs de execução.
- **FilaBloq:** A classe `FilaBloq` representa uma fila de processos bloqueados em um sistema de escalonamento. Ela é responsável por gerenciar os processos que estão no estado "Bloqueado" e determinar quando um processo deve ser movido de volta para o estado "Pronto".
- **FilaProntos:** A classe `FilaProntos` representa uma fila de processos prontos em um sistema de escalonamento. Ela é responsável por gerenciar os processos que estão no estado "Pronto" e determinar qual processo deve ser executado em seguida. A classe fornece métodos para manipular a fila de prontos e transições de estados dos processos.
- **TabelaDeProcessos:** A classe `TabelaDeProcessos` representa uma tabela que mantém o controle e informações sobre processos em um ambiente de escalonamento. Ela armazena uma lista de BCPs (Blocos de Controle de Processo) que contêm detalhes sobre cada processo, como seu estado, número de instruções executadas e outros atributos relacionados.

## Como executar
Para executar o programa, execute os seguintes comandos no terminal:
  javac Escalonador.java
  java Escalonador.java
**Obs.:** Para o funcionamento correto do programa, deve haver uma pasta `programas` no mesmo diretório do aplicativo, que deve conter os devidos processos a serem gerenciados.
