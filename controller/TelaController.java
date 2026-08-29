/*
 * ***************************************************************
 * Autor............: Carolina de Moraes Carneiro
 * Matricula........: 202410077
 * Inicio...........: 12/03/2025
 * Ultima alteracao.: 27/03/2025
 * Nome.............: TelaController
 * Funcao...........:  Funciona como o controlador da tela principal
 ****************************************************************/

package controller; // Pacote que contem o controlador da tela

import java.io.IOException; // Importa a classe IOException para lidar com erros de entrada/saida
import java.net.URL; // Importa a classe URL para trabalhar com enderecos de recursos
import java.util.ResourceBundle; // Importa a classe ResourceBundle para trabalhar com arquivos de recursos
import javafx.event.ActionEvent; // Importa a classe ActionEvent para tratar eventos de acao
import javafx.fxml.FXML; // Importa a anotacao FXML para vincular elementos da interface ao controlador
import javafx.fxml.FXMLLoader; // Importa a classe FXMLLoader para carregar arquivos FXML
import javafx.fxml.Initializable; // Importa a interface Initializable para inicializar a interface
import javafx.scene.Parent; // Importa a classe Parent para representar o no raiz da interface
import javafx.scene.Scene; // Importa a classe Scene para definir a interface visual
import javafx.scene.control.Button; // Importa a classe Button para criar botoes
import javafx.stage.Stage; // Importa a classe Stage para criar e gerenciar a janela da aplicacao
import models.TremAmarelo; // Importa a classe TremAmarelo do pacote models
import models.TremAzul; // Importa a classe TremAzul do pacote models
import javafx.scene.image.ImageView; // Importa a classe ImageView para trabalhar com imagens

public class TelaController implements Initializable { // Classe que controla os eventos da tela

  @FXML
  private Button backMenuButton; // Botao para voltar ao menu principal
  @FXML
  private ImageView tremAmarelo; // Imagem do trem amarelo
  @FXML
  private ImageView tremAzul; // Imagem do trem azul

  TremAmarelo tremAmarelo2; // Objeto para o trem amarelo
  TremAzul tremAzul2; // Objeto para o trem azul
  MenuController mController; // Controlador do menu principal

  // variaveis de travamento
  int variavelTravamento1 = 0;
  int variavelTravamento2 = 0;

  // variaveis solucao de estrita alternancia
  int vezRegiao1 = 0;
  int vezRegiao2 = 0;

  // variaveis solucao de peterson
  boolean interesseRegiao1[] = { false, false };
  boolean interesseRegiao2[] = { false, false };
  int ultimoRegiao1, ultimoRegiao2;

  /*
   * ***************************************************************
   * Metodo: backActionButton
   * Funcao: trata o evento de clicar no botao de voltar ao menu
   * Parametros: event - o evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  @FXML
  void backActionButton(ActionEvent event) { // Metodo chamado ao clicar no botao de voltar ao menu
    try {

      if (tremAmarelo2 != null)
        tremAmarelo2.matarThread(); // Para o trem amarelo se estiver em movimento
      if (tremAzul2 != null)
        tremAzul2.matarThread(); // Para o trem azul se estiver em movimento

      Parent root = FXMLLoader.load(getClass().getResource("/views/menuPrincipal.fxml")); // Carrega o arquivo FXML do
                                                                                          // menu principal
      Scene scene = new Scene(root); // Cria a cena com o conteudo carregado
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Obtem a janela atual (stage)
      stage.setScene(scene); // Define a cena na janela
    } catch (Exception e) {
      e.printStackTrace(); // Exibe o erro no console caso haja uma excecao
    }
  }

  /*
   * ***************************************************************
   * Metodo: aumentaTrem1
   * Funcao: aumenta a velocidade do trem amarelo
   * Parametros: event - o evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  @FXML
  void aumentaTrem1(ActionEvent event) { // Metodo chamado ao clicar no botao para aumentar a velocidade do trem amarelo
    tremAmarelo2.aumentarVelocidade(); // Aumenta a velocidade do trem amarelo

  }

  /*
   * ***************************************************************
   * Metodo: aumentaTrem2
   * Funcao: aumenta a velocidade do trem azul
   * Parametros: event - o evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  @FXML
  void aumentaTrem2(ActionEvent event) { // Metodo chamado ao clicar no botao para aumentar a velocidade do trem azul
    tremAzul2.aumentarVelocidade(); // Aumenta a velocidade do trem azul

  }

  /*
   * ***************************************************************
   * Metodo: diminuiTrem1
   * Funcao: diminui a velocidade do trem amarelo
   * Parametros: event - o evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  @FXML
  void diminuiTrem1(ActionEvent event) { // Metodo chamado ao clicar no botao para diminuir a velocidade do trem amarelo
    tremAmarelo2.diminuirVelocidade(); // Diminui a velocidade do trem amarelo

  }

  /*
   * ***************************************************************
   * Metodo: diminuiTrem2
   * Funcao: diminui a velocidade do trem azul
   * Parametros: event - o evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  @FXML
  void diminuiTrem2(ActionEvent event) { // Metodo chamado ao clicar no botao para diminuir a velocidade do trem azul
    tremAzul2.diminuirVelocidade(); // Diminui a velocidade do trem azul

  }

  /*
   * ***************************************************************
   * Metodo: resetaTrens
   * Funcao: reinicia os trens e carrega a tela principal
   * Parametros: event - o evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  @FXML
  void resetaTrens(ActionEvent event) throws IOException { // Metodo chamado ao clicar no botao para resetar os trens

    if (tremAmarelo2 != null)
      tremAmarelo2.matarThread();// Para o trem amarelo se estiver em movimento
    if (tremAzul2 != null)
      tremAzul2.matarThread(); // Para o trem azul se estiver em movimento

    Parent root = FXMLLoader.load(getClass().getResource("/views/telaPrincipal.fxml")); // Carrega o arquivo FXML dos
                                                                                        // trilhos
    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Obtem o stage do qual veio a request
                                                                               // do botao
    stage.setScene(new Scene(root)); // Muda a cena
    stage.show(); // Mostra a tela

  }

  /*
   * ***************************************************************
   * Metodo: resetaTrens2
   * Funcao: reinicia os trens e carrega a tela principal
   * Parametros: event - o evento gerado ao clicar no botao
   * Retorno: void
   ****************************************************************/
  @FXML
  void resetaTrens2(ActionEvent event) throws IOException { // Metodo chamado ao clicar no botao para resetar os trens

    if (tremAmarelo2 != null)
      tremAmarelo2.matarThread(); // Para o trem amarelo se estiver em movimento
    if (tremAzul2 != null)
      tremAzul2.matarThread(); // Para o trem azul se estiver em movimento

    Parent root = FXMLLoader.load(getClass().getResource("/views/telaPrincipal.fxml")); // Carrega o arquivo FXML dos
                                                                                        // trilhos
    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Obtem o stage do qual veio a request
                                                                               // do botao
    stage.setScene(new Scene(root)); // Muda a cena
    stage.show(); // Mostra a tela

  }

  /*
   * ***************************************************************
   * Metodo: selecionaModo
   * Funcao: seleciona o modo de trem baseado na escolha do usuario
   * Parametros: nenhum
   * Retorno: void
   ****************************************************************/
  public void selecionaModo() { // Metodo que seleciona o modo de trem
    int modoSelecionado = MenuController.getModoSelecionado(); // Obtem o modo selecionado a partir do controlador do
                                                               // menu

    switch (modoSelecionado) { // Switch case para definir os trens baseados no modo selecionado
      case 1:
        tremAmarelo2 = new TremAmarelo(this, mController, 319, 0, tremAmarelo); // Cria o trem amarelo para o modo 1
        tremAmarelo.setRotate(0); // Define a rotacao do trem amarelo
        tremAzul2 = new TremAzul(this, mController, 412, 0, tremAzul); // Cria o trem azul para o modo 1
        tremAzul.setRotate(0); // Define a rotacao do trem azul
        break;

      case 2:
        tremAmarelo2 = new TremAmarelo(this, mController, 324, 461, tremAmarelo); // Cria o trem amarelo para o modo 2
        tremAzul2 = new TremAzul(this, mController, 416, 461, tremAzul); // Cria o trem azul para o modo 2
        break;

      case 3:
        tremAmarelo2 = new TremAmarelo(this, mController, 319, 0, tremAmarelo); // Cria o trem amarelo para o modo 3
        tremAmarelo.setRotate(0); // Define a rotacao do trem amarelo
        tremAzul2 = new TremAzul(this, mController, 416, 461, tremAzul); // Cria o trem azul para o modo 3
        break;

      case 4:
        tremAmarelo2 = new TremAmarelo(this, mController, 324, 461, tremAmarelo); // Cria o trem amarelo para o modo 4
        tremAzul2 = new TremAzul(this, mController, 412, 0, tremAzul); // Cria o trem azul para o modo 4
        tremAzul.setRotate(0); // Define a rotacao do trem azul
        break;

      default:
        System.out.println("Modo invalido!"); // Exibe no console se o modo selecionado for invalido
        break;
    }
  }

  public ImageView getTremAzul() { // Metodo para obter o trem azul
    return tremAzul; // Retorna o trem azul
  }

  public ImageView getTremAmarelo() { // Metodo para obter o trem amarelo
    return tremAmarelo; // Retorna o trem amarelo
  }

  // Getters e Setters

  // variavelTravamento1
  public int getVariavelTravamento1() {
    return variavelTravamento1;
  }

  public void setVariavelTravamento1(int ocupado) {
    this.variavelTravamento1 = ocupado;
  }

  // variavelTravamento2
  public int getVariavelTravamento2() {
    return variavelTravamento2;
  }

  public void setVariavelTravamento2(int ocupado) {
    this.variavelTravamento2 = ocupado;
  }

  // vezRegiao1
  public int getVezRegiao1() {
    return vezRegiao1;
  }

  public void setVezRegiao1(int vez) {
    this.vezRegiao1 = vez;
  }

  // vezRegiao2
  public int getVezRegiao2() {
    return vezRegiao2;
  }

  public void setVezRegiao2(int vez) {
    this.vezRegiao2 = vez;
  }

  // interesseRegiao1
  public boolean getInteresseRegiao1(int posicao) {
    return interesseRegiao1[posicao];
  }

  public void setInteresseRegiao1(int posicao, boolean valor) {
    this.interesseRegiao1[posicao] = valor;
  }

  // interesseRegiao2
  public boolean getInteresseRegiao2(int posicao) {
    return interesseRegiao2[posicao];
  }

  public void setInteresseRegiao2(int posicao, boolean valor) {
    this.interesseRegiao2[posicao] = valor;
  }

  // ultimoRegiao1
  public int getUltimoRegiao1() {
    return ultimoRegiao1;
  }

  public void setUltimoRegiao1(int processo) {
    this.ultimoRegiao1 = processo;
  }

  // ultimoRegiao2
  public int getUltimoRegiao2() {
    return ultimoRegiao2;
  }

  public void setUltimoRegiao2(int processo) {
    this.ultimoRegiao2 = processo;
  }

  /*
   * ***************************************************************
   * Metodo: initialize
   * Funcao: inicializa a tela com os trens
   * Parametros: location - URL do recurso, resources - bundle de recursos
   * Retorno: void
   ****************************************************************/
  @Override
  public void initialize(URL location, ResourceBundle resources) { // Metodo chamado ao inicializar a tela
    selecionaModo(); // Chama o metodo para selecionar o modo de trem
    tremAmarelo2.start(); // Inicia o trem amarelo
    tremAzul2.start(); // Inicia o trem azul
  }
}
