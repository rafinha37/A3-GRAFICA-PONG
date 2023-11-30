package Cena;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.IOException;
import java.io.InputStream;

public class Textura {
    private Texture textura;
    private int genModo = GL2.GL_OBJECT_LINEAR;

    public void carregarTextura(String path) {
        try {
            InputStream stream = getClass().getResourceAsStream(path); //"./Imgs/Cora.png"
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
            textura = TextureIO.newTexture(data);
        } catch (IOException | GLException e) {
            e.printStackTrace();
        }
    }

    
    public void configurarTela (GL2 gl) {
        //Liga a textura
        gl.glEnable(GL2.GL_TEXTURE_2D);
        textura.bind(gl);
        textura.enable(gl);

        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

    }

    public void configurarCoracao (GL2 gl) {
        //Liga a textura
        gl.glEnable(GL2.GL_TEXTURE_2D);
        textura.bind(gl);

        textura.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

        textura.enable(gl);

        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void configurar(GL2 gl) {
        //Liga a textura
        gl.glEnable(GL2.GL_TEXTURE_2D);
        textura.bind(gl);
        textura.enable(gl);

        // Define os filtros de aumento e reducao
        //GL_NEAREST ou GL_LINEAR                
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

        //GL.GL_REPEAT ou GL.GL_CLAMP        
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        textura.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);

        gl.glEnable(GL2.GL_TEXTURE_GEN_S); // Habilita a geracao da textura
        gl.glEnable(GL2.GL_TEXTURE_GEN_T);
       
        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, genModo);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, genModo);
    }

    public void desabilitarTextura (GL2 gl) {
        //Desliga todos os tipos de textura
        gl.glDisable(GL2.GL_TEXTURE_GEN_S);
        gl.glDisable(GL2.GL_TEXTURE_GEN_T);
        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glFlush();
        textura.disable(gl);
    }
}
