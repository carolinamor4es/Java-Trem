/*
 * ***************************************************************
 * Autor............: Carolina de Moraes Carneiro
 * Matricula........: 202410077
 * Inicio...........: 12/03/2025
 * Ultima alteracao.: 23/03/2025
 * Nome.............: Principal
 * Funcao...........: Classe principal que inicializa a aplicacao
 ****************************************************************/

import java.io.IOException; // Importa a classe IOException para tratar erros de entrada e saida

import controller.MenuController;
import controller.TelaController;
import javafx.application.Application; // Importa a classe Application do JavaFX para criar a aplicacao
import javafx.fxml.FXMLLoader; // Importa a classe FXMLLoader para carregar arquivos FXML
import javafx.scene.Parent; // Importa a classe Parent para representar o no raiz da interface
import javafx.scene.Scene; // Importa a classe Scene para definir a interface visual
import javafx.stage.Stage; // Importa a classe Stage para criar e gerenciar a janela da aplicacao

/* ***************************************************************
* Classe principal - Responsavel por inicializar a aplicacao e carregar
* o arquivo FXML da tela principal
*************************************************************** */

public class Principal extends Application {
  /*
   * ***************************************************************
   * Metodo: main
   * Funcao: ponto de entrada do programa, onde a aplicacao e iniciada
   * Parametros: args - recebe dados linha de comando para configuracoes
   * Retorno: void
   ****************************************************************/
  public static void main(String[] args) {
    launch(args); // Chama o metodo launch do JavaFX para iniciar a aplicacao
  }

  /*
   * ***************************************************************
   * Metodo: start
   * Funcao: metodo que inicializa a janela principal da aplicacao
   * Parametros: Stage stage - recebe o objeto que representa a janela
   * Retorno: void
   ****************************************************************/

  @Override
  public void start(Stage stage) {
    try {
      MenuController menu = new MenuController();
      TelaController tela = new TelaController();
      Parent root = FXMLLoader.load(getClass().getResource("/views/menuPrincipal.fxml")); // Carrega o arquivo FXML que
                                                                                          // define a interface grafica
                                                                                          // da tela principal

      Scene scene = new Scene(root); // Cria uma nova cena a partir do arquivo FXML carregado

      stage.setTitle("ESTACAO DE TREM DO SONIC"); // Define o titulo da janela (barra de titulo)
      stage.setScene(scene); // Associa a cena criada a janela (stage)
      stage.setResizable(false); // Impede que o usuario redimensione a janela
      stage.show(); // Exibe a janela na tela

    } catch (IOException ex) {
      System.out.println(ex.getMessage()); // Exibe a mensagem de erro caso falhe ao carregar o FXML
    }
  }
}
