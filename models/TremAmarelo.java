/*
 * ***************************************************************
 * Autor............: Carolina de Moraes Carneiro
 * Matricula........: 202410077
 * Inicio...........: 12/03/2025
 * Ultima alteracao.: 05/05/2025
 * Nome.............: TremAmarelo
 * Funcao...........: Controla o movimento do trem amarelo na tela
 ****************************************************************/

package models; // Pacote que contem o modelo do trem amarelo

import controller.MenuController; // Importa o controlador do menu
import controller.TelaController; // Importa o controlador da tela principal
import javafx.application.Platform; // Importa a classe Platform para atualizar a interface na thread correta
import javafx.scene.image.ImageView; // Importa a classe ImageView para manipular imagens na interface

public class TremAmarelo extends Thread {
    private ImageView tremAmarelo; // Imagem do trem amarelo
    private int eixoX = 0; // Posicao inicial no eixo X
    private int eixoY = 0; // Posicao inicial no eixo Y
    private int velocidade; // Velocidade de movimento do trem
    private final int inicialX; // Posicao inicial no eixo X
    private final int inicialY; // Posicao inicial no eixo Y

    private TelaController controller; // Controlador da tela
    private MenuController controller2; // Controlador do menu

    /*
     * ***************************************************************
     * Metodo: TremAmarelo
     * Funcao: Construtor da classe TremAmarelo
     * Parametros: controller - Controlador da tela
     * controller2 - Controlador do menu
     * eixoX - Posicao inicial no eixo X
     * eixoY - Posicao inicial no eixo Y
     * tremAmarelo - ImageView do trem amarelo
     * Retorno: void
     ****************************************************************/
    public TremAmarelo(TelaController controller, MenuController controller2, int eixoX, int eixoY,
            ImageView tremAmarelo) {
        this.controller = controller;
        this.controller2 = controller2;
        this.eixoX = eixoX;
        this.eixoY = eixoY;
        this.tremAmarelo = tremAmarelo;
        velocidade = 20;
        this.inicialX = eixoX;
        this.inicialY = eixoY;

        // Define a posicao inicial do ImageView
        Platform.runLater(() -> {
            this.tremAmarelo.setLayoutX(eixoX);
            this.tremAmarelo.setLayoutY(eixoY);
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
                Platform.runLater(() -> controller.getTremAmarelo().setLayoutY(eixoY));
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
                Platform.runLater(() -> controller.getTremAmarelo().setLayoutY(eixoY));
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
                tremAmarelo.setLayoutX(layoutX);
                tremAmarelo.setLayoutY(layoutY);
            });

            try {
                sleep(velocidade);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        // Garante que a posicao final seja exatamente o destino
        eixoX = destinoX;
        eixoY = destinoY;
        Platform.runLater(() -> {
            tremAmarelo.setLayoutX(eixoX);
            tremAmarelo.setLayoutY(eixoY);
        });
    }

    /*
     * ***************************************************************
     * Metodo: descerPelaDireita
     * Funcao: Faz o trem descer pela direita, movimentando-se em
     * varias etapas de rotacao e movimentos diagonais
     * Parametros: tremAmarelo - ImageView do trem
     * Retorno: void
     ****************************************************************/
    public void descerPelaDireita(ImageView tremAmarelo) {
        descerPraBaixo(40);
        Platform.runLater(() -> tremAmarelo.setRotate(-7));
        descerPraBaixo(50);

        entrarRegiaoCritica1(); // inicio da concorrencia 1

        Platform.runLater(() -> tremAmarelo.setRotate(-60));
        moverDiagonal(366, 75);
        Platform.runLater(() -> tremAmarelo.setRotate(0));
        descerPraBaixo(155);
        Platform.runLater(() -> tremAmarelo.setRotate(65));
        moverDiagonal(322, 185);

        sairRegiaoCritica1(); // fim da concorrencia 1

        Platform.runLater(() -> tremAmarelo.setRotate(0));
        descerPraBaixo(280);

        entrarRegiaoCritica2(); // inicio da concorrencia 2

        Platform.runLater(() -> tremAmarelo.setRotate(-65));
        moverDiagonal(369, 303);
        Platform.runLater(() -> tremAmarelo.setRotate(0));
        descerPraBaixo(385);
        Platform.runLater(() -> tremAmarelo.setRotate(65));
        moverDiagonal(325, 407);

        sairRegiaoCritica2(); // fim da concorrencia 2

        Platform.runLater(() -> tremAmarelo.setRotate(0));
        descerPraBaixo(500);

        eixoX = inicialX;
        eixoY = inicialY;
        // Atualiza a UI para a posicao inicial de forma instantanea
        Platform.runLater(() -> {
            tremAmarelo.setLayoutX(inicialX);
            tremAmarelo.setLayoutY(inicialY);
        });
    }

    /*
     * ***************************************************************
     * Metodo: subirPelaDireita
     * Funcao: Faz o trem subir pela direita, movimentando-se em
     * varias etapas de rotacao e movimentos diagonais
     * Parametros: tremAmarelo - ImageView do trem
     * Retorno: void
     ****************************************************************/
    public void subirPelaDireita(ImageView tremAmarelo) {
        subirPraCima(410);

        entrarRegiaoCritica2(); // inicio da concorrencia 1

        Platform.runLater(() -> tremAmarelo.setRotate(-130));
        moverDiagonal(369, 380);
        Platform.runLater(() -> tremAmarelo.setRotate(180));
        subirPraCima(305);
        Platform.runLater(() -> tremAmarelo.setRotate(125));
        moverDiagonal(322, 275);

        sairRegiaoCritica2(); // fim da concorrencia 1

        Platform.runLater(() -> tremAmarelo.setRotate(180));
        subirPraCima(185);

        entrarRegiaoCritica1(); // inicio da concorrencia 2

        Platform.runLater(() -> tremAmarelo.setRotate(-130));
        moverDiagonal(366, 150);
        Platform.runLater(() -> tremAmarelo.setRotate(180));
        subirPraCima(81);
        Platform.runLater(() -> tremAmarelo.setRotate(125));
        moverDiagonal(318, 50);

        sairRegiaoCritica1(); // fim da concorrencia 2

        Platform.runLater(() -> tremAmarelo.setRotate(180));
        subirPraCima(-35);

        eixoX = inicialX;
        eixoY = inicialY;
        // Atualiza a UI para a posicao inicial de forma instantanea
        Platform.runLater(() -> {
            tremAmarelo.setLayoutX(inicialX);
            tremAmarelo.setLayoutY(inicialY);
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
                descerPelaDireita(controller.getTremAmarelo());
                break;
            case 2:
                subirPelaDireita(controller.getTremAmarelo());
                break;
            case 3:
                descerPelaDireita(controller.getTremAmarelo());
                break;
            case 4:
                subirPelaDireita(controller.getTremAmarelo());
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

       /*
     * ***************************************************************
     * Metodo: entrarRegiaoCritica1
     * Funcao: Controla a entrada na regiao critica 1 de acordo com a solucao escolhida
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void entrarRegiaoCritica1() {
        if (controller2.getProblemaSelecionado() == 1) {
            entrarSolucaoVT(); // usa variavel de travamento
        } else if (controller2.getProblemaSelecionado() == 2) {
            entrarSolucaoEA(); // usa estrita alternancia
        } else if (controller2.getProblemaSelecionado() == 3) {
            entrarSolucaoPeterson(); // usa solucao de peterson
        }
    }

    /*
     * ***************************************************************
     * Metodo: sairRegiaoCritica1
     * Funcao: Libera a regiao critica 1 conforme a solucao de exclusao mutua utilizada
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void sairRegiaoCritica1() {
        if (controller2.getProblemaSelecionado() == 1) {
            sairSolucaoVT(); // usa variavel de travamento
        } else if (controller2.getProblemaSelecionado() == 2) {
            sairSolucaoEA(); // usa estrita alternancia
        } else if (controller2.getProblemaSelecionado() == 3) {
            sairSolucaoPeterson(); // usa solucao de peterson
        }
    }

    /*
     * ***************************************************************
     * Metodo: entrarRegiaoCritica2
     * Funcao: Controla a entrada na regiao critica 2 de acordo com a solucao escolhida
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void entrarRegiaoCritica2() {
        if (controller2.getProblemaSelecionado() == 1) {
            entrarSolucaoVT2(); // usa variavel de travamento
        } else if (controller2.getProblemaSelecionado() == 2) {
            entrarSolucaoEA2(); // usa estrita alternancia
        } else if (controller2.getProblemaSelecionado() == 3) {
            entrarSolucaoPeterson2(); // usa solucao de peterson
        }
    }

    /*
     * ***************************************************************
     * Metodo: sairRegiaoCritica2
     * Funcao: Libera a regiao critica 2 conforme a solucao de exclusao mutua utilizada
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void sairRegiaoCritica2() {
        if (controller2.getProblemaSelecionado() == 1) {
            sairSolucaoVT2(); // usa variavel de travamento
        } else if (controller2.getProblemaSelecionado() == 2) {
            sairSolucaoEA2(); // usa estrita alternancia
        } else if (controller2.getProblemaSelecionado() == 3) {
            sairSolucaoPeterson2(); // usa solucao de peterson
        }
    }

    /*
     * ***************************************************************
     * Metodo: entrarSolucaoVT
     * Funcao: Controla a entrada na regiao critica 1 usando variavel de travamento
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void entrarSolucaoVT() {
        while (controller.getVariavelTravamento1() == 1) {
            try {
                TremAmarelo.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.setVariavelTravamento1(1);
    }

    /*
     * ***************************************************************
     * Metodo: sairSolucaoVT
     * Funcao: Libera a regiao critica 1 usando variavel de travamento
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void sairSolucaoVT() {
        controller.setVariavelTravamento1(0);
    }

    /*
     * ***************************************************************
     * Metodo: entrarSolucaoVT2
     * Funcao: Controla a entrada na regiao critica 2 usando variavel de travamento
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void entrarSolucaoVT2() {
        while (controller.getVariavelTravamento2() == 1) {
            try {
                TremAmarelo.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controller.setVariavelTravamento2(1);
    }

    /*
     * ***************************************************************
     * Metodo: sairSolucaoVT2
     * Funcao: Libera a regiao critica 2 usando variavel de travamento
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void sairSolucaoVT2() {
        controller.setVariavelTravamento2(0);
    }

    /*
     * ***************************************************************
     * Metodo: entrarSolucaoEA
     * Funcao: Controla a entrada na regiao critica 1 usando Estrita Alternancia
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void entrarSolucaoEA() {
        while (controller.getVezRegiao1() == 1) {
            try {
                TremAmarelo.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * ***************************************************************
     * Metodo: sairSolucaoEA
     * Funcao: Libera a regiao critica 1 usando Estrita Alternancia
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void sairSolucaoEA() {
        controller.setVezRegiao1(1);
    }

    /*
     * ***************************************************************
     * Metodo: entrarSolucaoEA2
     * Funcao: Controla a entrada na regiao critica 2 usando Estrita Alternancia
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void entrarSolucaoEA2() {
        while (controller.getVezRegiao2() == 1) {
            try {
                TremAmarelo.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * ***************************************************************
     * Metodo: sairSolucaoEA2
     * Funcao: Libera a regiao critica 2 usando Estrita Alternancia
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void sairSolucaoEA2() {
        controller.setVezRegiao2(1);
    }

    /*
     * ***************************************************************
     * Metodo: entrarSolucaoPeterson
     * Funcao: Controla a entrada na regiao critica 1 usando o algoritmo de Peterson
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void entrarSolucaoPeterson() {
        int outro_processo = 0, processo_atual = 1;
        controller.setInteresseRegiao1(processo_atual, true);
        controller.setUltimoRegiao1(processo_atual);
        while (controller.getUltimoRegiao1() == processo_atual &&
               controller.getInteresseRegiao1(outro_processo) == true) {
            try {
                TremAmarelo.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * ***************************************************************
     * Metodo: sairSolucaoPeterson
     * Funcao: Libera a regiao critica 1 usando o algoritmo de Peterson
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void sairSolucaoPeterson() {
        int processo_atual = 1;
        controller.setInteresseRegiao1(processo_atual, false);
    }

    /*
     * ***************************************************************
     * Metodo: entrarSolucaoPeterson2
     * Funcao: Controla a entrada na regiao critica 2 usando o algoritmo de Peterson
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void entrarSolucaoPeterson2() {
        int outro_processo = 0, processo_atual = 1;
        controller.setInteresseRegiao2(processo_atual, true);
        controller.setUltimoRegiao2(processo_atual);
        while (controller.getUltimoRegiao2() == processo_atual &&
               controller.getInteresseRegiao2(outro_processo) == true) {
            try {
                TremAmarelo.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * ***************************************************************
     * Metodo: sairSolucaoPeterson2
     * Funcao: Libera a regiao critica 2 usando o algoritmo de Peterson
     * Parametros: nenhum
     * Retorno: void
     ****************************************************************/
    public void sairSolucaoPeterson2() {
        int processo_atual = 1;
        controller.setInteresseRegiao2(processo_atual, false);
    }


    /*
     * ***************************************************************
     * Metodo: run
     * Funcao: Inicia o movimento do trem em loop
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
