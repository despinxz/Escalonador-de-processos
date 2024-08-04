import java.util.List;
import java.util.ArrayList;

/**
 * A classe BCP (Bloco de Controle de Processo) representa o controle e informações
 * de um processo a ser executado em um ambiente de escalonamento de processos.
 * Ela contém detalhes sobre o estado do processo, instruções de programa e o valor dos registradores X e Y, além do Program Counter.
 */

public class BCP{
  // Atributos
  
  /**
   * Program Counter (PC) - A variável que controla a próxima instrução a ser executada
   * no programa associado a este processo.
   */
  protected int PC;

  /**
   * Estado do processo (por exemplo, "Pronto" ou "Bloqueado").
   */
  protected String estado;

  /**
   * Registrador X - Armazena um valor inteiro relacionado ao processo.
   */
  protected int X;

  /**
   * Registrador Y - Armazena um valor inteiro relacionado ao processo.
   */
  protected int Y;

  
  /**
   * Programa - Lista de strings que representam as instruções do programa a ser executado.
   */
  protected List<String> programa;

  /**
   * Nome do processo.
   */
  protected String nome;

  /**
   * Identificador único do arquivo associado a este processo.
   */
  protected int nome_arquivo;

  /**
   * Tempo de espera na fila de bloqueados.
   */
  protected int vezes_fila_bloq;

  // Construtor para abrir um novo processo
  /**
   * Cria um novo processo com o nome, identificador de arquivo e programa especificados.
   *
   * @param nome O nome do processo.
   * @param nome_arquivo O identificador de arquivo associado a este processo.
   * @param programa Uma lista de strings que representam as instruções do programa.
   */
  public BCP(String nome, int nome_arquivo, List<String> programa){
    this.PC = 0;
    this.estado = "Pronto";
    this.X = 0;
    this.Y = 0;
    this.programa = programa;
    this.nome = nome;
    this.nome_arquivo = nome_arquivo;
  }

  // Métodos Getters e Setters
  /**
   * Obtém o nome do processo.
   *
   * @return O nome do processo.
   */
  public String getNome() {
      return this.nome;
  }

  /**
   * Obtém o identificador de arquivo associado a este processo.
   *
   * @return O identificador de arquivo.
   */
  public int getNomeArquivo() {
      return this.nome_arquivo;
  }

  /**
   * Obtém o estado atual do processo.
   *
   * @return O estado do processo.
   */
  public String getEstado() {
      return this.estado;
  }

  /**
   * Obtém o valor atual do Program Counter (PC).
   *
   * @return O valor do Program Counter.
   */
  public int getPC() {
      return this.PC;
  }

  /**
   * Obtém o valor atual do registrador X.
   *
   * @return O valor do registrador X.
   */
  public int getX() {
      return this.X;
  }

  /**
   * Obtém o valor atual do registrador Y.
   *
   * @return O valor do registrador Y.
   */
  public int getY() {
      return this.Y;
  }

  /**
   * Obtém a lista de strings que representam as instruções do programa associado a este processo.
   *
   * @return A lista de instruções do programa.
   */
  public List<String> getPrograma() {
      return this.programa;
  }

  /**
   * Obtém a próxima instrução no programa com base no valor atual do Program Counter (PC).
   *
   * @return A próxima instrução no programa.
   */
  public String getComando() {
      return this.programa.get(this.PC);
  }

  /**
   * Obtém o número total de comandos no programa associado a este processo.
   *
   * @return O número de comandos no programa.
   */
  public int getNumComandos() {
      return this.programa.size();
  }

  /**
   * Obtém o tempo de espera na fila de bloqueados.
   *
   * @return O tempo de espera na fila de bloqueados.
   */
  public int getVezesFilaBloq() {
      return this.vezes_fila_bloq;
  }

  /**
   * Define o estado do processo (por exemplo, "Pronto" ou "Bloqueado").
   *
   * @param estado O novo estado do processo.
   */
  public void setEstado(String estado) {
      this.estado = estado;
  }

  /**
   * Define o valor do registrador X.
   *
   * @param valor O novo valor do registrador X.
   */
  public void setX(int valor) {
      this.X = valor;
  }

  /**
   * Define o valor do registrador Y.
   *
   * @param valor O novo valor do registrador Y.
   */
  public void setY(int valor) {
      this.Y = valor;
  }

  /**
   * Define o tempo de espera que do processo na fila de bloqueados
   *
   * @param n O tempo de espera na fila de bloqueados.
   */
  public void setVezesFilaBloq(int n) {
      this.vezes_fila_bloq = n;
  }

  /**
   * Adiciona 1 ao Program Counter (PC), avançando para a próxima instrução no programa.
   */
  public void addUmPC(){
    this.PC++;
  }

  /**
   * Exibe informações detalhadas sobre o processo, incluindo nome, estado, PC, X, Y e programa.
   */
  public void printBCP() {
    System.out.println("Nome: " + this.nome);
    System.out.println("Estado: " + this.estado);
    System.out.println("PC: " + this.PC);
    System.out.println("X: " + this.X);
    System.out.println("Y: " + this.Y);
    System.out.println("Programa: ");
    for (int i=0; i < this.getNumComandos(); i++) {
      System.out.println(programa.get(i));
    }
    System.out.println();
  }
}
