import java.util.LinkedList;
import java.util.Queue;

/**
 * A classe `FilaProntos` representa uma fila de processos prontos em um sistema de escalonamento.
 * Ela é responsável por gerenciar os processos que estão no estado "Pronto" e determinar qual processo
 * deve ser executado em seguida. A classe fornece métodos para manipular a fila de prontos e transições
 * de estados dos processos.
 */

public class FilaProntos {
  // Atributos

  /**
   * Fila de processos que estão prontos para serem executados.
   */
  Queue<BCP> fila_prontos = new LinkedList<BCP> ();

  // Métodos

  /**
   * Obtém a fila de processos prontos.
   *
   * @return A fila de processos prontos.
   */
  public Queue<BCP> getFilaProntos() {
    return fila_prontos;
  }
  
  /**
   * Cria uma nova fila de processos prontos inicialmente preenchida com os processos da tabela especificada.
   *
   * @param tabela A tabela de processos que fornece os processos para preencher a fila de prontos.
   */
  public void novaFila(TabelaDeProcessos tabela){
    for(BCP x : tabela.processos)
      fila_prontos.add(x);
  }
  
  /**
   * Insere um processo na fila de prontos e altera o estado do processo para "Pronto".
   * Este método é utilizado na classe `FilaBloq` quando um processo precisa ser desbloqueado.
   *
   * @param processo O BCP (Bloco de Controle de Processo) a ser inserido na fila de prontos.
   */
  public void inserePronto(BCP processo){
    processo.setEstado("Pronto");
    fila_prontos.add(processo);
  }

  /**
   * Remove o processo pronto que será executado a seguir da fila de prontos.
   */
  public void removePronto(){
    fila_prontos.remove();
  }

  /**
   * Obtém o próximo processo pronto que será executado e altera o estado do processo para "Executando".
   *
   * @return O próximo processo pronto a ser executado.
   */
  public BCP pegaPronto(){
    BCP temp = fila_prontos.peek();
    if (temp != null) temp.setEstado("Executando");
    return temp;
  }

  /**
   * Após a execução de um processo, move-o para o final da fila de prontos e altera seu estado de "Executando" para "Pronto".
   */
  public void jogaFimDaFila(){
    BCP temp;
    temp = fila_prontos.peek();
    if (temp != null) {
      fila_prontos.poll();
      inserePronto(temp);
      temp.setEstado("Pronto");
    }
  }

  /**
   * Quando há um comando de E/S, move o processo para a fila de bloqueados e altera seu estado para "Bloqueado".
   *
   * @param fila_bloq A fila de bloqueados onde o processo deve ser inserido.
   */
  public void entradaSaida(FilaBloq fila_bloq){
    BCP temp;
    temp = fila_prontos.peek();
    fila_prontos.poll();
    fila_bloq.insereBloq(temp);
  }

  /**
   * Exibe informações da fila de prontos, incluindo os nomes dos processos prontos. Este método é apenas para fins de visualização.
   */
  public void printaFila(){
   for(BCP x : fila_prontos)
     System.out.println("Carregando " + x.getNome());
  } 
}