package Cena;

//Imports OpenGL
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;

//Imports Java
import java.awt.*;

public class EventListener implements GLEventListener {
    //Pontos de referência ortogonal da tela
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    //Delimitações X-Y do contorno quadrado ao redor do jogo em si
    private float xMinTela = -100.0f;  //-90.0f
    private float xMaxTela = 100.0f;   //90.0f
    private float yMinTela = -100.0f;  //-65.0f
    private float yMaxTela = 100.0f;   //95.0f
    //Delimitações do eixo X da barra
    public float posicaoBarraXFrontal;
    public float posicaoBarraXTraseira;
    //Bloqueio ou liberação do movimento da barra
    public boolean liberarMovimentoBarra;
    //Delimitações do eixo X-Y do centro da esfera
    private float centroEsferaX;
    private float centroEsferaY;
    //Delimitações do raio total da esfera nos eixos X-Y
    private float raioX = 10.0f;
    private float raioY = 1.75f * raioX;
    //Velocidade de translação da esfera nos eixos X-Y
    private float velocidadeY;
    private float velocidadeX;
    //Valores referentes à velocidade adquirida pela esfera antes de pausas, paradas ou alterações no estado do jogo
    private float reservaVelocidadeY;
    private float reservaVelocidadeX;
    //Delimitações do posicionamento da primeira textura da "vida" nos eixos X-Y
    private float vidasXMin = 35.0f;
    private float vidasXMax = 45.0f;
    //Tonalização de iluminação
    public int tonalizacao = GL2.GL_SMOOTH; 
    //Definição do número de vidas
    private int numVidas;
    //Definição da pontuação no placar
    private int numPlacar;

    //NIVEL 2
    //Variavel para iniciar o nivel 2
    private int nivel;

    //Variaveis para a criação do poligono
    private float vpoligonoX1 = 10f;
    private float vpoligonoY1 = 40f;
    private float vpoligonoX2 = 25f;
    private float vpoligonoY2 = 40f;
    private float vpoligonoX3 = 25f;
    private float vpoligonoY3 = 10f;
    private float vpoligonoX4 = -25f;
    private float vpoligonoY4 = 10f;
    private float vpoligonoX5 = -25f;
    private float vpoligonoY5 = 40f;
    private float vpoligonoX6 = -10f;
    private float vpoligonoY6 = 40f;
    private float vpoligonoX7 = -10f;
    private float vpoligonoY7 = 60f;
    private float vpoligonoX8 = 10f;
    private float vpoligonoY8 = 60f;
    private float vpoligonoX9 = 10f;
    private float vpoligonoY9 = 40f;


    //
    public boolean sairTelaInicial = false;
    private Textura textura;
    private Textura texturaBarra;
    private Textura texturaEsfera;
    private Textura texturaTela;
    private Textura texturaBackGround;
    private Textura texturaObstaculo;
    private TextRenderer textRenderer;
    public static GL2 gl = null;
    GLU glu;

    public void inicializarVariaveis() {
        //Atribuição dos valores iniciais no começo do jogo às variáveis correspondentes
        nivel=1;
        posicaoBarraXFrontal = 50.0f;
        posicaoBarraXTraseira = -50.0f;
        liberarMovimentoBarra = false;
        centroEsferaX = 0.0f;
        centroEsferaY = 50.0f;
        velocidadeY = 0.0f;
        velocidadeX = 0.0f;
        reservaVelocidadeY = 0.0f;
        reservaVelocidadeX = 0.0f;
        numVidas = 5;
        numPlacar = 0;
    }

    //Cria o Poligono do Nivel 2
    private void poligonoNivel2(GL2 gl) {
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(vpoligonoX1, vpoligonoY1);
        gl.glVertex2f(vpoligonoX2, vpoligonoY2);
        gl.glVertex2f(vpoligonoX3, vpoligonoY3);
        gl.glVertex2f(vpoligonoX4, vpoligonoY4);
        gl.glVertex2f(vpoligonoX5, vpoligonoY5);
        gl.glVertex2f(vpoligonoX6, vpoligonoY6);
        gl.glVertex2f(vpoligonoX7, vpoligonoY7);
        gl.glVertex2f(vpoligonoX8, vpoligonoY8);
        gl.glVertex2f(vpoligonoX9, vpoligonoY9);
        gl.glEnd();
    }

    private void colisaoPoligono(){
        if (centroEsferaY - raioY <= vpoligonoY1 && centroEsferaX - raioX+(raioX/3) <= vpoligonoX2 && centroEsferaX+ raioX-(raioX/3) >= vpoligonoX1 && centroEsferaY >= vpoligonoY1 ||
                centroEsferaY - raioY <= vpoligonoY5 && centroEsferaX - raioX+(raioX/3)<= vpoligonoX6 && centroEsferaX + raioX-(raioX/3)>= vpoligonoX5 && centroEsferaY >= vpoligonoY5 ||
                centroEsferaY - raioY  <= vpoligonoY7 && centroEsferaX - raioX+(raioX/3)<= vpoligonoX8 && centroEsferaX + raioX-(raioX/3)>= vpoligonoX7 && centroEsferaY >= vpoligonoY7) {
            centroEsferaY+= 0.2;
            velocidadeY = -velocidadeY;
        }
        if (centroEsferaY <= vpoligonoY3 && centroEsferaX - raioX+(raioX/3)<= vpoligonoX3 && centroEsferaX+ raioX-(raioX/3)>= vpoligonoX4 && centroEsferaY + raioY >= vpoligonoY3) {
            centroEsferaY+= -0.2f;
            velocidadeY = -velocidadeY;
        }

        if (centroEsferaY - raioY + (raioX/3)<= vpoligonoY2 && centroEsferaX-raioX<= vpoligonoX2 && centroEsferaX >= vpoligonoX2 && centroEsferaY+ raioY-(raioX/3)>= vpoligonoY3 ||
                centroEsferaY- raioY+(raioX/3)<= vpoligonoY8 && centroEsferaX-raioX<= vpoligonoX8 && centroEsferaX >= vpoligonoX8 && centroEsferaY+ raioY-(raioX/3)>= vpoligonoY1) {
            centroEsferaX += 0.2;
            velocidadeX = -velocidadeX;
        }
        if (centroEsferaY - raioY+(raioX/3)<= vpoligonoY5&& centroEsferaX <= vpoligonoX4 && centroEsferaX + raioX >= vpoligonoX4 && centroEsferaY+ raioY-(raioX/3)>= vpoligonoY4 ||
                centroEsferaY- raioY+(raioX/3)<= vpoligonoY7 && centroEsferaX <= vpoligonoX6 && centroEsferaX + raioX >= vpoligonoX6 && centroEsferaY+ raioY-(raioX/3)>= vpoligonoY6) {
            centroEsferaX += -0.2f;
            velocidadeX = -velocidadeX;
        }
    }

    // Altera a velocidade da esfera e muda a variavel nivel para iniciar o nivel 2
    private void iniciaFase2() {
        nivel = 2;
        velocidadeX = velocidadeX * 0.7f;
        velocidadeY = velocidadeY * 0.7f;
    }

    private void esfera(GL2 gl) {     
        //Definição do limite e tamanho da esfera
        double limite = 2*Math.PI;
        double i;

        gl.glBegin(GL2.GL_POLYGON);
        for(i = 0; i < limite; i+= 0.01) {
            gl.glVertex2d(centroEsferaX + raioX * Math.cos(i), centroEsferaY + raioY * Math.sin(i));
        }
        gl.glEnd();
        
    }

    private void barra(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(posicaoBarraXTraseira, -35.0f);
        gl.glVertex2f(posicaoBarraXFrontal, -35.0f);
        gl.glVertex2f(posicaoBarraXFrontal, -55.0f);
        gl.glVertex2f(posicaoBarraXTraseira, -55.0f);
        gl.glEnd();
    }

    private void vidas(GL2 gl, float auxMin, float auxMax, int tentativas) {
        for (int i = 0; i < tentativas; i++){
            gl.glBegin(GL2.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex2f(auxMin, -75.0f); //80.0f
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex2f(auxMax, -75.0f); //90.0f
            gl.glTexCoord2f(1, 1);
            gl.glVertex2f(auxMax, -90.0f); //90.0f
            gl.glTexCoord2f(0, 1);
            gl.glVertex2f(auxMin, -90.0f); //80.0f
            gl.glEnd();

            auxMin = auxMin + 11.0f;
            auxMax = auxMax + 11.0f;
        }
    }

    private void telaApresentacao(GL2 gl){
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(xMinTela, yMinTela); //80.0f
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(xMaxTela, yMinTela); //90.0f
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(xMaxTela, yMaxTela); //90.0f
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(xMinTela, yMaxTela); //80.0f
        gl.glEnd();
    }

    public void desenhaTexto(String texto, int x, int y, Color cor) {
        //Conversão de coordenadas relativas (X-Y) para coordenadas absolutas para projeção ortogonal na tela (X-Y)
        int xAbs = (int) ((x - xMin) / (xMax - xMin) * Renderer.larguraJanela);
        int yAbs = (int) ((y - yMin) / (yMax - yMin) * Renderer.alturaJanela);

        textRenderer.beginRendering(Renderer.larguraJanela, Renderer.alturaJanela);
        textRenderer.setColor(cor);
        textRenderer.draw(texto, xAbs, yAbs);
        textRenderer.endRendering();
    }

    public void moverBarraEsquerda(){
        System.out.println("Moveu para a esquerda!");
        if(posicaoBarraXTraseira - 3 <= xMinTela){
            posicaoBarraXTraseira = xMinTela;
            posicaoBarraXFrontal = posicaoBarraXTraseira + 100;
        }
        else{
            posicaoBarraXFrontal -= 3;
            posicaoBarraXTraseira -= 3;
            System.out.println(posicaoBarraXFrontal + " / " + posicaoBarraXTraseira);
        }
    }

    public void moverBarraDireita(){
        System.out.println("Moveu para a direita!");
        if(posicaoBarraXFrontal + 3 >= xMaxTela){
            posicaoBarraXFrontal = xMaxTela;
            posicaoBarraXTraseira = posicaoBarraXFrontal - 100;
        }
        else{
            posicaoBarraXFrontal += 3;
            posicaoBarraXTraseira += 3;
            System.out.println(posicaoBarraXFrontal + " / " + posicaoBarraXTraseira);
        }
    }

    public void marcarPonto() {
        System.out.println("Ponto contabilizado.");
        numVidas -= 1;
        centroEsferaX = 0.0f;
        centroEsferaY = 50.0f;
        System.out.println(numVidas);
    }

    public void exibirTelaApresentacao() {
        //"Limpa" a tela para deixar o fundo preto
        gl.glClearColor(0, 0, 0, 1);

        //Cria uma instância da classe Textura
        texturaTela = new Textura();
        //Carrega a textura das vidas
        texturaTela.carregarTextura("../Imgs/telaInicial.png");

        texturaTela.configurarTela(gl);

        telaApresentacao(gl);

        texturaTela.desabilitarTextura(gl);

        gl.glFlush();
    }

    public void exibirTela() {
        //"Limpa" a tela para deixar o fundo preto
        gl.glClearColor(0, 0, 0, 1);

        //Cria uma instância da classe Textura
        texturaBackGround = new Textura();
        //Carrega a textura das vidas
        texturaBackGround.carregarTextura("../Imgs/space.png");

        texturaBackGround.configurarTela(gl);

        telaApresentacao(gl);

        texturaBackGround.desabilitarTextura(gl);
        

        gl.glFlush();
    }

    public void começarJogo() {
        if(reservaVelocidadeX == 0 && reservaVelocidadeY == 0){
            velocidadeX = 1.0f;
            velocidadeY = 1.0f;
        }
        else {
            velocidadeY = reservaVelocidadeY;
            velocidadeX = reservaVelocidadeX;
        }

        liberarMovimentoBarra = true;
    }

    public void pausarJogo() {
        reservaVelocidadeY = velocidadeY;
        reservaVelocidadeX = velocidadeX;
        velocidadeX = 0.0f;
        velocidadeY = 0.0f;
        liberarMovimentoBarra = false;
    }

    public void pararJogo() {
        inicializarVariaveis();
    }

    //Iluminação
    public void iluminacaoAmbiente(GL2 gl) {
        float luzAmbiente[] = {0.1f, 0.1f, 0.1f, 1f}; //cor
        float posicaoLuz[] = {centroEsferaX, centroEsferaY, 0f, 0f}; //pontual

        // define parametros de luz de n�mero 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    
    public void ligaLuz(GL2 gl) {
        // habilita a defini��o da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da ilumina��o na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de n�mero 0
        gl.glEnable(GL2.GL_LIGHT0);
        //Especifica o Modelo de tonalizacao a ser utilizado      
        gl.glShadeModel(tonalizacao);
    }

    public void desligaluz(GL2 gl) {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
        gl.glDisable(GL2.GL_LIGHT1);
        gl.glDisable(GL2.GL_LIGHT2);
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        //Dados iniciais da cena
        glu = new GLU();

        //Criação de texturas
        textura = new Textura();

        texturaBarra = new Textura();

        texturaEsfera = new Textura();

        texturaObstaculo = new Textura();

        texturaBackGround = new Textura();

        //Carrega as texturas
        textura.carregarTextura("../Imgs/Cora.png");

        texturaBarra.carregarTextura("../Imgs/escudo.png");

        texturaEsfera.carregarTextura("../Imgs/asteroide.png");

        texturaObstaculo.carregarTextura("../Imgs/pedra.png");

        //Define a fonte, seus parâmetros e instancia passando esses mesmos parâmetros para o objeto
        Font fonte = new Font("Comic Sans MS Negrito", Font.PLAIN, 20);
        textRenderer = new TextRenderer(fonte, true, true);

        //Atribui o valor das variáveis essenciais para o funcionamento do jogo
        inicializarVariaveis();

        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -100;
        xMax = yMax = zMax = 100;


    }


    @Override
    public void dispose(GLAutoDrawable drawable) {
        //
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        GL2 gl = drawable.getGL().getGL2();
        
        exibirTela();

            gl.glPushMatrix();
            iluminacaoAmbiente(gl);
            if(centroEsferaY > 10.0f){
            ligaLuz(gl);
            }
            texturaEsfera.configurar(gl);
            esfera(gl);
            texturaEsfera.desabilitarTextura(gl);
            desligaluz(gl);
            gl.glFlush();
            gl.glPopMatrix();
            gl.glLineWidth(3.0f);
            gl.glPopMatrix();

            gl.glPushMatrix();
            desenhaTexto("Vidas:", 20, -75, Color.white);
            desenhaTexto("Pontuação:", -90, -75, Color.white);
            desenhaTexto("" + numPlacar, -70, -85, Color.white);
            gl.glFlush();
            gl.glPopMatrix();

            
            gl.glPushMatrix();
            textura.configurarCoracao(gl);
            vidas(gl, vidasXMin, vidasXMax, numVidas);
            textura.desabilitarTextura(gl);
            gl.glFlush();
            gl.glPopMatrix();

            gl.glPushMatrix();
            texturaBarra.configurar(gl);
            barra(gl);
            texturaBarra.desabilitarTextura(gl);
            gl.glFlush();
            gl.glPopMatrix();
            
        if (sairTelaInicial) {

            if (numPlacar == 200 && nivel == 1) {
                iniciaFase2();
            }
            // Inseri o Poligono e a colisão no nível 2
            if (nivel == 2) {
                texturaObstaculo.configurar(gl);
                iluminacaoAmbiente(gl);
                ligaLuz(gl);
                desligaluz(gl);
                poligonoNivel2(gl);
                texturaObstaculo.desabilitarTextura(gl);
                colisaoPoligono();
            }

            centroEsferaY += velocidadeY;
            centroEsferaX += velocidadeX;

            //Colisão horizontal da esfera com os limites da tela
            if (centroEsferaX + raioX >= xMaxTela || centroEsferaX - raioX <= xMinTela) {
                velocidadeX = -velocidadeX;
            }

            //Colisão vertical da esfera com os limites da tela
            if (centroEsferaY + raioY >= yMaxTela || centroEsferaY - raioY <= yMinTela) {
                velocidadeY = -velocidadeY;
            }

            //Colisão vertical da barra com a esfera
            if (centroEsferaY - raioY <= -35.0f && centroEsferaX >= posicaoBarraXTraseira && centroEsferaX <= posicaoBarraXFrontal) {
                float centroBarra = (posicaoBarraXTraseira + posicaoBarraXFrontal) / 2.0f;
                float distanciaVertical = Math.abs(centroEsferaY - (-35.0f)); // Distância vertical entre o centro da esfera e a parte superior da barra
                float distanciaHorizontal = Math.abs(centroEsferaX - centroBarra); // Distância horizontal entre o centro da esfera e o centro da barra

                if (distanciaVertical < raioY && distanciaHorizontal < (posicaoBarraXFrontal - posicaoBarraXTraseira) / 2.0f) {
                    // Ajusta a posição da esfera para fora da barra na direção vertical
                    centroEsferaY = -35.0f + raioY + 0.1f;
                    velocidadeY = -velocidadeY;
                    numPlacar += 10;

                    if (centroEsferaX <= centroBarra){
                        velocidadeX *= 2.0f;
                        velocidadeY *= 2.0f;
                    }
                    else {
                        velocidadeX /= 2.0f;
                        velocidadeY /= 2.0f;
                    }
                }
            /*
            centroEsferaY = -60.0f + raioY + 0.1f;
            velocidadeY = -velocidadeY;
            TST
            */
            }

            if (centroEsferaY <= -35.0f && centroEsferaY >= -55.0f) {

                if (centroEsferaX - raioX <= posicaoBarraXFrontal && centroEsferaX >= posicaoBarraXTraseira) {
                    centroEsferaX = posicaoBarraXFrontal + raioX + 0.1f;
                    velocidadeX = -velocidadeX;
                } else if (centroEsferaX + raioX >= posicaoBarraXTraseira && centroEsferaX <= posicaoBarraXFrontal) {
                    centroEsferaX = posicaoBarraXTraseira - raioX - 0.1f;
                    velocidadeX = -velocidadeX;
                }
            }

            //Retira a vida do jogador caso a bala ultrapasse a barra
            if (centroEsferaY - raioY <= -65.0f) {
                marcarPonto();
                if(numVidas <= 0){
                    inicializarVariaveis();
                }
            }
        }
        else {
            exibirTelaApresentacao();
        }
        
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}
