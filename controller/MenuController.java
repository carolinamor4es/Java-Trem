/*
 * ***************************************************************
 * Autor............: Carolina de Moraes Carneiro
 * Matricula........: 202410077
 * Inicio...........: 12/03/2025
 * Ultima alteracao.: 05/05/2025
 * Nome.............: MenuController
 * Funcao...........: Funciona como o controlador da tela de menu
 ****************************************************************/

package controller; // Pacote que contem o controlador da tela

import java.net.URL; // Importa a classe URL para trabalhar com enderecos de recursos
import java.util.ResourceBundle; // Importa a classe ResourceBundle para trabalhar com arquivos de recursos
import javafx.event.ActionEvent; // Importa a classe ActionEvent para tratar eventos de acao
import javafx.fxml.FXML; // Importa a anotacao FXML para vincular elementos da interface ao controlador
import javafx.fxml.FXMLLoader; // Importa a classe FXMLLoader para carregar arquivos FXML
import javafx.fxml.Initializable; // Importa a interface Initializable para inicializar a interface
import javafx.scene.Parent; // Importa a classe Parent para representar o no raiz da interface
import javafx.scene.Scene; // Importa a classe Scene para definir a interface visual
import javafx.scene.control.Alert; // Importa a classe Alert para exibir caixas de dialogo
import javafx.scene.control.Button; // Importa a classe Button para criar botoes
import javafx.scene.control.ChoiceBox; // Importa a classe ChoiceBox para criar caixas de selecao
import javafx.stage.Stage; // Importa a classe Stage para criar e gerenciar a janela da aplicacao

public class MenuController implements Initializable { // Classe que controla os eventos da tela do menu

  @FXML
  private Button menuButton; // Botao para acessar a tela principal

  @FXML
  private ChoiceBox<String> modoTremChoiceBox; // Caixa de selecao para escolher o modo de trem

  @FXML
  private ChoiceBox<String> problemTremChoiceBox; // Caixa de selecao para escolher o tipo de resolucao de problema

  /*
   * ***************************************************************
   * Metodo: handleMenuButton
   * Funcao: trata o evento de clique no botao do menu
   * Parametros: event - o evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  @FXML
  private void handleMenuButton(ActionEvent event) { // Metodo chamado ao clicar no botao do menu

    if (modoSelecionado == -1) { // Nenhuma opcao foi selecionada
      Alert alert = new Alert(Alert.AlertType.WARNING); // Cria um alerta de aviso
      alert.setTitle("Atencao"); // Define o titulo do alerta
      alert.setHeaderText(null); // Define o cabecalho do alerta como nulo
      alert.setContentText("Selecione um modo antes de iniciar"); // Define o texto do alerta
      alert.showAndWait(); // Exibe o alerta e aguarda o usuario fechar
      return; // Sai do metodo e impede a mudanca de tela
    }
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/views/telaPrincipal.fxml")); // Carrega o arquivo FXML da
                                                                                          // tela principal
      Scene scene = new Scene(root); // Cria a cena com o conteudo carregado
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Obtem a janela atual (stage)
      stage.setScene(scene); // Define a cena na janela
      stage.setTitle("ESTACAO DE TREM DO SONIC"); // Define o titulo da janela
    } catch (Exception e) {
      e.printStackTrace(); // Exibe o erro no console caso haja uma excecao

    }

  }

  private static int modoSelecionado = -1; // Variavel estatica para armazenar a escolha do modo de trem
  private static int problemaSelecionado = -1; // Variavel estatica para armazenar escolha de como resolver o problema

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: inicializa os itens da ChoiceBox com os modos de trem e de resolucao
   * de problema
   * Parametros: location - URL do recurso, resources - bundle de recursos
   * Retorno: void
   ****************************************************************/
  @Override
  public void initialize(URL location, ResourceBundle resources) { // Metodo chamado ao inicializar a tela
    modoTremChoiceBox.getItems().addAll("Trem azul e amarelo em cima", "Trem azul e amarelo embaixo", // Adiciona as
                                                                                                      // opcoes de modos
                                                                                                      // de trem na
                                                                                                      // ChoiceBox
        "Trem azul embaixo e amarelo em cima", "Trem azul em cima e amarelo embaixo");
    problemTremChoiceBox.getItems().addAll("Variavel de travamento", "Estrita Alternancia", // Adiciona as opcoes de
                                                                                            // tipos de resolucao na
                                                                                            // ChoiceBox
        "Solucao de Peterson");
  }

  /*
   * ***************************************************************
   * Metodo: selecionaModoTrem
   * Funcao: mapeia o modo selecionado para um valor
   * Parametros: event - o evento gerado ao selecionar o modo de trem
   * Retorno: void
   ****************************************************************/
  @FXML
  void selecionaModoTrem(ActionEvent event) { // Metodo chamado ao selecionar um modo de trem na ChoiceBox

    String modo = modoTremChoiceBox.getValue(); // Obtem o valor selecionado na ChoiceBox
    // Mapeia cada modo para um valor
    if (modo.equals("Trem azul e amarelo em cima")) {
      modoSelecionado = 1; // Atribui 1 para o modo selecionado
    } else if (modo.equals("Trem azul e amarelo embaixo")) {
      modoSelecionado = 2; // Atribui 2 para o modo selecionado
    } else if (modo.equals("Trem azul embaixo e amarelo em cima")) {
      modoSelecionado = 3; // Atribui 3 para o modo selecionado
    } else if (modo.equals("Trem azul em cima e amarelo embaixo")) {
      modoSelecionado = 4; // Atribui 4 para o modo selecionado
    } else {
      modoSelecionado = -1; // Se nenhum modo for selecionado, atribui -1
    }
  }

  /*
   * ***************************************************************
   * Metodo: selecionaResolucao
   * Funcao: mapeia a solucao selecionada para um valor
   * Parametros: event - o evento gerado ao selecionar a solucao do trem
   * Retorno: void
   ****************************************************************/
  @FXML
  void selecionaResolucao(ActionEvent event) {
    String modo = problemTremChoiceBox.getValue(); // Obtem o valor selecionado na ChoiceBox
    // Mapeia cada modo para um valor
    if (modo.equals("Variavel de travamento")) {
      problemaSelecionado = 1; // Atribui 1 para a solucao selecionada
      System.out.println("testando");
    } else if (modo.equals("Estrita Alternancia")) {
      problemaSelecionado = 2; // Atribui 2 para a solucao selecionada
    } else if (modo.equals("Solucao de Peterson")) {
      problemaSelecionado = 3; // Atribui 3 para a solucao selecionada
    } else {
      problemaSelecionado = -1; // Se nenhum modo for selecionado, atribui -1
    }
  }

  /*
   * ***************************************************************
   * Metodo: getModoSelecionado
   * Funcao: retorna o modo de trem selecionado
   * Parametros: nenhum
   * Retorno: int - valor do modo selecionado
   ****************************************************************/
  public static int getModoSelecionado() {
    return modoSelecionado; // Retorna o valor da variavel modoSelecionado
  }

  /*
   * ***************************************************************
   * Metodo: getProblemaSelecionado
   * Funcao: retorna o tipo de solucao selecionada
   * Parametros: nenhum
   * Retorno: int - valor do modo selecionado
   ****************************************************************/
  public static int getProblemaSelecionado() {
    return problemaSelecionado; // Retorna o valor da variavel problemaSelecionado
  }
}
