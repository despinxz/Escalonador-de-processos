import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe `FilaBloq` representa uma fila de processos bloqueados em um sistema de escalonamento.
 * Ela é responsável por gerenciar os processos que estão no estado "Bloqueado" e determinar quando
 * um processo deve ser movido de volta para o estado "Pronto".
 */

public class FilaBloq {
  // Atributos

  /**
   * Fila de processos que estão bloqueados.
   */
  Queue<BCP> fila_bloq = new LinkedList<BCP> ();

  // Métodos

  /**
   * Insere um processo na fila de bloqueados e altera o estado do processo para "Bloqueado".
   * Este método é utilizado na classe `FilaProntos` quando um processo precisa ser bloqueado.
   *
   * @param processo O BCP (Bloco de Controle de Processo) a ser inserido na fila de bloqueados.
   */
  public void insereBloq(BCP processo) {
    processo.setVezesFilaBloq(2);  // Contador para verificar se dois processos passaram pelo estado 'Executando'
    processo.setEstado("Bloqueado");
    fila_bloq.add(processo);
  }

  /**
   * Verifica se algum processo na fila de bloqueados zerou seu tempo de espera e o move de volta
   * para a fila de prontos. Isso ocorre quando um processo está bloqueado temporariamente e agora
   * está pronto para ser executado novamente.
   *
   * @param fila_prontos A fila de processos prontos onde o processo deve ser inserido.
   */
  public void verificarUltimoElemento(FilaProntos fila_prontos) {
    BCP ultimoElemento = fila_bloq.peek(); 
    if (ultimoElemento != null && ultimoElemento.getVezesFilaBloq() == 0) {
        fila_bloq.poll(); // Remove o último elemento da fila
        fila_prontos.inserePronto(ultimoElemento); // Adiciona o último elemento à fila de prontos
        ultimoElemento.setEstado("Pronto");
        ultimoElemento.addUmPC(); // Avança PC
    }
  }

  /**
   * Decrementa o tempo de espera de todos os processos na fila de bloqueados. Isso é usado para
   * acompanhar o tempo de espera restante para cada processo na fila de bloqueados.
   */
  public void decrementaTempoEspera(){
    for(BCP x : fila_bloq) x.setVezesFilaBloq(x.getVezesFilaBloq()-1);
  }

  /**
   * Exibe informações detalhadas sobre todos os BCPs na tabela, incluindo seus estados,
   * Program Counters (PCs), valores de X e Y e programas associados.
   */
  public void printaFila(){
   System.out.println("Fila Bloqueados:");
   for(BCP x : fila_bloq)
     System.out.println(x.getNome() + "vezes:" + x.getVezesFilaBloq());
  } 
}