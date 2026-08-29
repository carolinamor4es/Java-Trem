/*
 * ***************************************************************
 * Autor............: Carolina de Moraes Carneiro
 * Matricula........: 202410077
 * Inicio...........: 12/03/2025
 * Ultima alteracao.: 05/05/2025
 * Nome.............: TremAzul
 * Funcao...........: Controla o movimento do trem azul na tela
 ****************************************************************/

package models; // Pacote que contem o modelo do trem azul

import controller.MenuController; // Importa o controlador do menu
import controller.TelaController; // Importa o controlador da tela principal
import javafx.application.Platform; // Importa a classe Platform para atualizar a interface na thread correta
import javafx.scene.image.ImageView; // Importa a classe ImageView para manipular imagens na interface

public class TremAzul extends Thread {
    private ImageView tremAzul; // Imagem do trem azul
    private int eixoX = 0; // Posicao inicial no eixo X
    private int eixoY = 0; // Posicao inicial no eixo Y
    private int velocidade; // Velocidade de movimento do trem
    private final int inicialX; // Posicao inicial no eixo X
    private final int inicialY; // Posicao inicial no eixo Y

    private TelaController controller; // Controlador da tela
    private MenuController controller2; // Controlador do menu

    /*
     * ***************************************************************
     * Metodo: TremAzul
     * Funcao: Construtor da classe TremAzul
     * Parametros: controller - Controlador da tela
     * controller2 - Controlador do menu
     * eixoX - Posicao inicial no eixo X
     * eixoY - Posicao inicial no eixo Y
     * tremAzul - ImageView do trem azul
     * Retorno: void
     ****************************************************************/
    public TremAzul(TelaController controller, MenuController controller2, int eixoX, int eixoY, ImageView tremAzul) {
        this.controller = controller;
        this.controller2 = controller2;
        this.eixoX = eixoX;
        this.eixoY = eixoY;
        this.tremAzul = tremAzul;
        velocidade = 20;
        this.inicialX = eixoX;
        this.inicialY = eixoY;

        // Define a posicao inicial do ImageView
        Platform.runLater(() -> {
            this.tremAzul.setLayoutX(eixoX);
            this.tremAzul.setLayoutY(eixoY);
        });
    }

    /*
     * ***************************************************************
     * Metodo: descerPraBaixo
     * Funcao: Faz o trem descer ate a posicao indicada
     * Parametros: parada - Posicao final no eixo Y
     * Retorno: void
     ****************************************************************/
    public void descerPraBaixo(int parada) {
        while (eixoY <= parada) {
            try {
                Platform.runLater(() -> controller.getTremAzul().setLayoutY(eixoY));
                eixoY++;
                sleep(velocidade);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * ***************************************************************
     * Metodo: subirPraCima
     * Funcao: Faz o trem subir ate a posicao indicada
     * Parametros: parada - Posicao final no eixo Y
     * Retorno: void
     ****************************************************************/
    public void subirPraCima(int parada) {
        while (eixoY >= parada) {
            try {
                Platform.runLater(() -> controller.getTremAzul().setLayoutY(eixoY));
                eixoY--;
                sleep(velocidade);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * ***************************************************************
     * Metodo: moverDiagonal
     * Funcao: Move o trem em uma direcao diagonal ate o destino
     * Parametros: destinoX - Posicao final no eixo X
     * destinoY - Posicao final no eixo Y
     * Retorno: void
     ****************************************************************/
    public void moverDiagonal(int destinoX, int destinoY) {
        // Calcula a diferenca total para X e Y
        double deltaX = destinoX - eixoX;
        double deltaY = destinoY - eixoY;

        // Calcula o total de passos como o maior entre as diferencas de X e Y
        int totalPassos = Math.max(Math.abs(destinoX - eixoX), Math.abs(destinoY - eixoY));

        // Determina o incremento para cada passo de forma proporcional
        double passoX = deltaX / totalPassos;
        double passoY = deltaY / totalPassos;

        // Usa variaveis double para manter a precisao do movimento
        double posX = eixoX;
        double posY = eixoY;

        for (int i = 0; i < totalPassos; i++) {
            posX += passoX;
            posY += passoY;

            // Atualiza a posicao na interface convertendo para inteiro (arredondando)
            int layoutX = (int) Math.round(posX);
            int layoutY = (int) Math.round(posY);

            Platform.runLater(() -> {
                tremAzul.setLayoutX(layoutX);
                tremAzul.setLayoutY(layoutY);
            });

            try {
                sleep(velocidade);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        // Ajusta posicao final para garantir que termine exatamente no ponto desejado
        eixoX = destinoX;
        eixoY = destinoY;
        Platform.runLater(() -> {
            tremAzul.setLayoutX(eixoX);
            tremAzul.setLayoutY(eixoY);
        });
    }

    /*
     * ***************************************************************
     * Metodo: descerPelaEsquerda
     * Funcao: Faz o trem descer pela esquerda, movimentando-se em
     * varias etapas de rotacao e movimentos diagonais
     * Parametros: tremAzul - ImageView do trem
     * Retorno: void
     ****************************************************************/
    public void descerPelaEsquerda(ImageView tremAzul) {
        descerPraBaixo(40);
        Platform.runLater(() -> tremAzul.setRotate(7));
        descerPraBaixo(50);

        entrarRegiaoCritica1(); // inicio da concorrencia 1

        Platform.runLater(() -> tremAzul.setRotate(60));
        moverDiagonal(366, 75);
        Platform.runLater(() -> tremAzul.setRotate(0));
        descerPraBaixo(160);
        Platform.runLater(() -> tremAzul.setRotate(-65));
        moverDiagonal(413, 178);

        sairRegiaoCritica1(); // fim da concorrencia 1

        Platform.runLater(() -> tremAzul.setRotate(0));
        descerPraBaixo(280);

        entrarRegiaoCritica2(); // inicio da concorrencia 2
        Platform.runLater(() -> tremAzul.setRotate(65));
        moverDiagonal(369, 303);
        Platform.runLater(() -> tremAzul.setRotate(0));
        descerPraBaixo(385);
        Platform.runLater(() -> tremAzul.setRotate(-65));
        moverDiagonal(415, 405);

        sairRegiaoCritica2(); // fim da concorrencia 2

        Platform.runLater(() -> tremAzul.setRotate(0));
        descerPraBaixo(500);

        eixoX = inicialX;
        eixoY = inicialY;
        // Atualiza a UI para a posicao inicial de forma instantanea
        Platform.runLater(() -> {
            tremAzul.setLayoutX(inicialX);
            tremAzul.setLayoutY(inicialY);
        });
    }

    /*
     * ***************************************************************
     * Metodo: subirPelaEsquerda
     * Funcao: Faz o trem subir pela esquerda, movimentando-se em
     * varias etapas de rotacao e movimentos diagonais
     * Parametros: tremAzul - ImageView do trem
     * Retorno: void
     ****************************************************************/
    public void subirPelaEsquerda(ImageView tremAzul) {
        subirPraCima(410);

        entrarRegiaoCritica2(); // inicio da concorrencia 1

        Platform.runLater(() -> tremAzul.setRotate(130));
        moverDiagonal(369, 380);
        Platform.runLater(() -> tremAzul.setRotate(180));
        subirPraCima(305);
        Platform.runLater(() -> tremAzul.setRotate(-125));
        moverDiagonal(414, 276);

        sairRegiaoCritica2(); // fim da concorrencia 1

        Platform.runLater(() -> tremAzul.setRotate(180));
        subirPraCima(185);

        entrarRegiaoCritica1(); // inicio da concorrencia 2

        Platform.runLater(() -> tremAzul.setRotate(130));
        moverDiagonal(366, 150);
        Platform.runLater(() -> tremAzul.setRotate(180));
        subirPraCima(81);
        Platform.runLater(() -> tremAzul.setRotate(-125));
        moverDiagonal(410, 55);

        sairRegiaoCritica1();// fim da concorrencia 2

        Platform.runLater(() -> tremAzul.setRotate(180));
        subirPraCima(-35);
        eixoX = inicialX;
        eixoY = inicialY;
        // Atualiza a UI para a posicao inicial de forma instantanea
        Platform.runLater(() -> {
            tremAzul.setLayoutX(inicialX);
            tremAzul.setLayoutY(inicialY);
        });
    }

    /*
     * ***************************************************************
     * Metodo: escolherOrientacao
     * Funcao: Escolhe a orientacao do movimento com base na selecao
     * do usuario
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void escolherOrientacao() {
        int modoSelecionado = MenuController.getModoSelecionado();
        switch (modoSelecionado) {
            case 1:
                descerPelaEsquerda(controller.getTremAzul());
                break;
            case 2:
                subirPelaEsquerda(controller.getTremAzul());
                break;
            case 3:
                subirPelaEsquerda(controller.getTremAzul());
                break;
            case 4:
                descerPelaEsquerda(controller.getTremAzul());
            default:
                break;
        }
    }

    /*
     * ***************************************************************
     * Metodo: aumentarVelocidade
     * Funcao: Aumenta a velocidade do trem
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void aumentarVelocidade() {
        if (velocidade > 5) {
            velocidade = velocidade - 5;
        }
    }

    /*
     * ***************************************************************
     * Metodo: diminuirVelocidade
     * Funcao: Diminui a velocidade do trem
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void diminuirVelocidade() {
        if (velocidade < 35) {
            velocidade = velocidade + 5;
        }
    }

    public void entrarRegiaoCritica1() {
        if (controller2.getProblemaSelecionado() == 1) {
            entrarSolucaoVT(); // usa variavel de travamento
        } else if (controller2.getProblemaSelecionado() == 2) {
            entrarSolucaoEA(); // usa estrita alternancia
        } else if (controller2.getProblemaSelecionado()==3) {
            entrarSolucaoPeterson(); // usa solucao de peterson
        }
    }

    public void sairRegiaoCritica1() {
        if (controller2.getProblemaSelecionado() == 1) {
            sairSolucaoVT(); // usa variavel de travamento
        } else if (controller2.getProblemaSelecionado() == 2) {
            sairSolucaoEA(); // usa estrita alternancia
        } else if (controller2.getProblemaSelecionado()==3) {
            sairSolucaoPeterson();// usa solucao de peterson
        }
    }

    public void entrarRegiaoCritica2() {
        if (controller2.getProblemaSelecionado() == 1) {
            entrarSolucaoVT2(); // usa variavel de travamento
        } else if (controller2.getProblemaSelecionado() == 2) {
            entrarSolucaoEA2();// usa estrita alternancia
        } else if (controller2.getProblemaSelecionado()==3) {
            entrarSolucaoPeterson2();// usa solucao de peterson
        }
    }

    public void sairRegiaoCritica2() {
        if (controller2.getProblemaSelecionado() == 1) {
            sairSolucaoVT2(); // usa variavel de travamento
        } else if (controller2.getProblemaSelecionado() == 2) {
            sairSolucaoEA2(); // usa estrita alternancia
        } else if (controller2.getProblemaSelecionado()==3) {
            sairSolucaoPeterson2(); // usa solucao de peterson
        }
    }

    // VARIAVEL DE TRAVAMENTO
    public void entrarSolucaoVT() {
        while (controller.getVariavelTravamento1() == 1) { // analisa se a regiao critica esta ocupada
            // espera desocupar
            try {
                TremAzul.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.setVariavelTravamento1(1); // coloca como ocupada a rc1
    }

    public void sairSolucaoVT() {
        controller.setVariavelTravamento1(0);
    }

    public void entrarSolucaoVT2() {
        while (controller.getVariavelTravamento2() == 1) { // analisa se a regiao critica esta ocupada
            // espera desocupar
            try {
                TremAzul.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.setVariavelTravamento2(1); // coloca como ocupada a rc1
    }

    public void sairSolucaoVT2() {
        controller.setVariavelTravamento2(0);
    }

    // ESTRITA ALTERNANCIA

    public void entrarSolucaoEA() {
        while (controller.getVezRegiao1() == 0) { // vez do outro processo
            try {
                TremAzul.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sairSolucaoEA() {
        controller.setVezRegiao1(0);
    }

    public void entrarSolucaoEA2() {
        while (controller.getVezRegiao2() == 0) { // vez do outro processo
            try {
                TremAzul.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sairSolucaoEA2() {
        controller.setVezRegiao2(0);
    }

    // SOLUCAO PETERSON

    public void entrarSolucaoPeterson() {
        int outro_processo = 1, processo_atual = 0;
        controller.setInteresseRegiao1(processo_atual, true); // seta o interesse do processo atual na regiao 1 como
                                                              // true
        controller.setUltimoRegiao1(processo_atual); // seta que o ultimo que entrou na regiao eh o atual
        while (controller.getUltimoRegiao1() == processo_atual
                && controller.getInteresseRegiao1(outro_processo) == true) {
            try {
                TremAzul.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sairSolucaoPeterson() {
        int processo_atual = 0;
        controller.setInteresseRegiao1(processo_atual, false); // tira o interesse da regiao critica
    }

    public void entrarSolucaoPeterson2() {
        int outro_processo = 1, processo_atual = 0;
        controller.setInteresseRegiao2(processo_atual, true); // seta o interesse do processo atual na regiao 2 como
                                                              // true
        controller.setUltimoRegiao2(processo_atual); // seta que o ultimo que entrou na regiao eh o atual
        while (controller.getUltimoRegiao2() == processo_atual
                && controller.getInteresseRegiao2(outro_processo) == true) {
            try {
                TremAzul.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sairSolucaoPeterson2() {
        int processo_atual = 0;
        controller.setInteresseRegiao2(processo_atual, false); // tira o interesse da regiao critica
    }

    /*
     * ***************************************************************
     * Metodo: run
     * Funcao: Inicia o movimento do trem
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    boolean iniciaThread = true;

    public void matarThread() {
        iniciaThread = false;
    }

    public void run() {
        while (iniciaThread) {
            escolherOrientacao();
        }
    }
}
