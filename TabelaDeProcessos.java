import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A classe `TabelaDeProcessos` representa uma tabela que mantém o controle e informações sobre
 * processos em um ambiente de escalonamento. Ela armazena uma lista de BCPs (Blocos de Controle de Processo)
 * que contêm detalhes sobre cada processo, como seu estado, número de instruções executadas e outros
 * atributos relacionados.
 */

public class TabelaDeProcessos{
  protected int n_trocas;  // Número de troca de processos
  protected int n_instrucoes;  // Número de instruções executadas

  /**
   * Lista de BCPs que representam os processos mantidos na tabela.
   */
  public List<BCP> processos;

  // Construtores

  /**
   * Construtor padrão para criar uma tabela de processos vazia.
   */
  public TabelaDeProcessos() {
    this.processos = new ArrayList<>();
  }

  /**
   * Construtor que permite inicializar a tabela com uma lista de processos existentes.
   *
   * @param processos Uma lista de BCPs para inicializar a tabela.
   */
  public TabelaDeProcessos(List<BCP> processos) {
    this.processos = processos;
  }

  // Métodos de Acesso

  /**
   * Obtém o tamanho da lista de processos na tabela.
   *
   * @return O tamanho da lista de processos.
   */
  public int getTamanhoLista(){
    return this.processos.size();
  } 

  /**
   * Adiciona um novo BCP à lista de processos na tabela.
   *
   * @param bcp O BCP a ser adicionado.
   */
  public void addBCP(BCP bcp) {
    this.processos.add(bcp);
  }

  /**
   * Exclui um processo da lista de processos que já foi executado por completo.
   *
   * @param bcp O BCP a ser excluído.
   */
  public void excluiBCP(BCP bcp){ 
    this.processos.remove(bcp);
  }

  // Outros Métodos

  /**
   * Exibe informações detalhadas sobre todos os BCPs na tabela, incluindo seus estados,
   * Program Counters (PCs), valores de X e Y e programas associados.
   */
  public void printTabela(){
    System.out.println("***TABELA***");
    System.out.println("BCPs:");
    for (int i = 0; i < this.processos.size(); i++) {
      this.processos.get(i).printBCP();
    }
  }
}
