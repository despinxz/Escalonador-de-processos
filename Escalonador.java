import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Collections;

/**
 * A classe Escalonador representa um escalonador de processos que executa
 * programas com base em um quantum definido. Ela gerencia uma tabela de
 * processos, uma fila de processos prontos e uma fila de processos bloqueados.
 * 
 * Esta classe permite ler programas a partir de arquivos de texto, executá-los
 * de acordo com o quantum e gerar logs de execução.
 */

public class Escalonador {
  // Atributos

  /**
   * O quantum define o número máximo de instruções que um processo pode executar
   * antes de ser interrompido e escalonado novamente.
   */
  private int quantum;

  /**
   * Tabela de processos que armazena informações sobre os programas a serem
   * executados.
   */
  private TabelaDeProcessos tabela;

  /**
   * Fila de processos prontos para execução.
   */
  private FilaProntos filaProntos;

  /**
   * Fila de processos bloqueados aguardando operações de E/S.
   */
  private FilaBloq filaBloqueados;
  
  // Metodo construtor

  /**
   * Cria uma instância do escalonador com tabelas e filas vazias.
   */
  public Escalonador() {
    this.tabela = new TabelaDeProcessos();
    this.filaProntos = new FilaProntos();
    this.filaBloqueados = new FilaBloq();
  }
  
  // Metodo set do atributo quantum
  /**
   * Define o valor do quantum, que é o número máximo de instruções que um
   * processo pode executar antes de ser interrompido.
   *
   * @param quantum O valor do quantum a ser definido.
   */
  public void setQuantum(int quantum) {
    this.quantum = quantum;
  }
  
  // Metodo get do atributo quantum
  /**
   * Retorna o valor do quantum atual.
   *
   * @return O valor do quantum.
   */
  public int getQuantum() {
    return this.quantum;
  }

  /**
   * O método principal da aplicação que lê programas, gera a fila de processos
   * prontos e gera um log de execução.
   *
   * @param args Os argumentos da linha de comando (não utilizados).
   */
  public static void main (String[] args) {
    Escalonador escalonador = new Escalonador();

    // Tabela lida
    escalonador.lerArquivos();
  
    // Fila gerada
    escalonador.filaProntos.novaFila(escalonador.tabela);
  
    // Geração do log
    escalonador.gerar_log();
  }

  /**
   * Executa um processo da fila de processos prontos de acordo com o quantum.
   *
   * @param writer Um objeto PrintWriter para gravar saída no log.
   */
  public void executar(PrintWriter writer) {
    boolean saida = false;  // Booleano para verificar se o programa terminou
    int contagemQuantum = this.getQuantum();
    BCP bcp = filaProntos.pegaPronto();

    // Caso haja um processo na fila de prontos, o executa
    if (bcp != null) {
      // Printa o nome
      writer.println("Executando " + bcp.getNome());

      // Executa o quantum
      while (contagemQuantum > 0) {

        contagemQuantum--;  // Diminui o numero da contagem atual
        String comando = bcp.getComando();  // Armazena o comando atual do programa

        // Caso o comando seja "X="
        if (comando.contains("X=")) {
          String[] partes = comando.split("=");
          String valorString = partes[1];
          int valor = Integer.parseInt(valorString);
          bcp.setX(valor);
        }

        // Caso o comando seja "Y="
        else if (comando.contains("Y=")) {
          String[] partes = comando.split("=");
          String valorString = partes[1];
          int valor = Integer.parseInt(valorString);
          bcp.setY(valor);
        }

        // Caso o comando seja "COM"
        else if (comando.equals("COM")){
        }

        // Caso o comando seja "E/S"
        else if (comando.equals("E/S")) {
          writer.println("E/S iniciada em " + bcp.getNome());
          filaProntos.entradaSaida(filaBloqueados);  // Adiciona o processo à fila de bloqueados e remove da fila de prontos
          //bcp.addUmPC(); // Avança o Program Counter do processo
          break;  // Interrompe o laço
        }

        // Caso o comando seja "SAIDA" (fim do processo)
        else if (comando.equals("SAIDA")) {
          tabela.excluiBCP(bcp);  // Exclui o processo da tabela
          filaProntos.removePronto();  // Exclui o processo da fila de prontos
          saida = true;
          break;  // Interrompe o laço
        }

        bcp.addUmPC();  // Avança o Program Counter do processo
      }

      int instrucoesRodadas = this.getQuantum() - contagemQuantum;

      // 'if' pra definir se 'instrução' fica no plural ou não
      if (instrucoesRodadas == 1)
        writer.println("Interrompendo " + bcp.getNome() + " após " + instrucoesRodadas + " instrução");
      else
        writer.println("Interrompendo " + bcp.getNome() + " após " + instrucoesRodadas + " instruções");

      if (saida) {  // Se o programa tiver terminado, printa a saída e zera o BCP
        writer.print(bcp.getNome() + " terminado. ");
        writer.print("X=" + bcp.getX() + ". ");
        writer.println("Y=" + bcp.getY());

        bcp = null;
      }

      if (!saida && bcp.getEstado() != "Bloqueado") filaProntos.jogaFimDaFila();  // Se o processo não tiver sido excluído ou bloqueado, o envia para o fim da fila de prontos   

      filaBloqueados.decrementaTempoEspera();

      tabela.n_trocas++;  // +1 ao número de trocas de processos
      tabela.n_instrucoes += instrucoesRodadas;
    }  

    filaBloqueados.verificarUltimoElemento(filaProntos);  // Verifica se há processos para sair da fila de bloqueados
  }

  /**
   * Lê programas de arquivos de texto e preenche a tabela de processos.
   */
  public void lerArquivos() {
    // Criando a pasta que contem os arquivos
    File diretorio = new File("programas");
    // Buscando todos os arquivos na pasta
    File[] arquivos = diretorio.listFiles(); 

    // Variaveis temporarias usadas para criar cada um dos programas
    List<BCP> processos = new ArrayList<>();
    List<String> programa;
    BCP bcp;
    String nome, linha;
    int nome_arquivo;

    // Iterando sobre a lista de arquivos
    for (File arquivo : arquivos) {
      if (arquivo.getName().contains("quantum")) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
          linha = br.readLine();
          // Caso o arquivo esteja correto, determina o quantum
          if (linha != null) this.setQuantum(Integer.parseInt(linha)); 
        }
        // Tratando excecao
        catch (IOException e) {
          e.printStackTrace();
        }     
      }
      else {
        // Identificando o nome do arquivo
        nome_arquivo = Integer.parseInt(arquivo.getName().replace(".txt", ""));
        // Esvaziando ou inicializando o programa
        programa = new ArrayList<>();

        // Leitura do arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
          // Determinando a primeira linha do arquivo como nome do programa
          nome = br.readLine();

          // Criando o programa a partir de todas as outras linhas
          linha = br.readLine();         
          while (linha != null) {     
            programa.add(linha);
            linha = br.readLine();
          }    

          // Criando um BCP e o adicionando na tabela
          processos.add(new BCP(nome, nome_arquivo, programa));
        } 
        // Tratando excecoes
        catch (IOException e) {
          e.printStackTrace();
        }   
      }
    } 

    // Ordenando os processos por ordem alfabetica referente ao nome
    Collections.sort(processos, new Comparator<BCP>() {
            @Override
            public int compare(BCP bcp1, BCP bcp2) {
                return Integer.compare(bcp1.getNomeArquivo(), bcp2.getNomeArquivo());
            }
        });
    
    this.tabela = new TabelaDeProcessos(processos);
  }

  /**
   * Gera um log de execução, incluindo informações sobre a execução de processos,
   * média de trocas e média de instruções.
   */
  public void gerar_log() {
    int num_processos = this.tabela.getTamanhoLista();

    try {
      // Verifica se a pasta "logs" existe e cria se não existir
      File logsDir = new File("logs");
      if (!logsDir.exists()) {
          logsDir.mkdirs(); // Cria a pasta "logs" se não existir
      }

      // Cria log
      String nome_file = "logs/log" + String.format("%02d", this.getQuantum()) + ".txt";
      PrintWriter writer = new PrintWriter(nome_file, "UTF-8");

      // Adiciona a fila de prontos ao log
      for (BCP bcp : filaProntos.getFilaProntos()) writer.println("Carregando " + bcp.getNome());

      // Executa todos os programas na tabela
      while (tabela.getTamanhoLista() > 0) {
        this.executar(writer);
      }

      // Adiciona média de trocas e de instruções ao log
      double media_trocas = this.tabela.n_trocas / num_processos;
      double media_instrucoes = this.tabela.n_instrucoes / tabela.n_trocas;

      writer.println("MÉDIA DE TROCAS: " + media_trocas);
      writer.println("MÉDIA DE INSTRUCOES: " + media_instrucoes);
      writer.println("QUANTUM: " + this.getQuantum());

      // Conclui o log
      writer.close();

    // Tratamento de exceções do writer
    } catch (FileNotFoundException e) {
      System.out.println("Ocorreu um erro ao abrir o arquivo.");
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      System.out.println("Ocorreu um erro com a codificação do arquivo.");
      e.printStackTrace();
    }
  }
}