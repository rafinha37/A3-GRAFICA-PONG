package Cena;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

public class Renderer {
    //Definição da janela aberta pelo OpenGL
    private static GLWindow janela = null;

    //Definição da largura e tamanho da janela

    public static int larguraJanela = 1080; //640
    public static int alturaJanela = 720; //360

    public static void init() {
        //Inicia o OpenGL e adquire os parâmetros correspondentes
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capacidades = new GLCapabilities(profile);

        //Cria uma janela com base nos parâmetros adquiridos pela iniciação do OpenGL
        janela = GLWindow.create(capacidades);
        //Determina o tamanho da janela com base em "larguraJanela" e "alturaJanela"
        janela.setSize(larguraJanela, alturaJanela);
        //Renomeia a janela
        janela.setTitle("Pong A3 - Computação Gráfica");
        //Inibe a opção de redimensionar o tamanho da janela
        janela.setResizable(true);
        janela.setFullscreen(false);

        EventListener el = new EventListener();
        janela.addGLEventListener(el);

        FPSAnimator animator = new FPSAnimator(janela, 60);
        animator.start();

        janela.addKeyListener(new Keyboard(el));

        //Impede a janela de fechar
        janela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });

        //Habilita a visibilidade da janela
        janela.setVisible(true);
    }

    public static void main(String[] args) {
        //Inicia o programa
        init();
    }
}