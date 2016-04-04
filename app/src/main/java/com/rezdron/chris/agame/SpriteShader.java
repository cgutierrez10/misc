package com.rezdron.chris.agame;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;


/**
 * Created by Chris on 3/21/2016.
 */
public class SpriteShader {
    public static int sp_Sprite;

    /*public static String vs_Sprite = "void main()"
            + "{"
            + "gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;"
            + "texture_coordinate = vec2(gl_MultiTexCoord0);"
            + "}";*/

    public static final String vs_Sprite =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec2 a_texCoord;" +
                    "varying vec2 v_texCoord;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  v_texCoord = a_texCoord;" +
                    "}";

    public static String fs_Sprite = "precision mediump float; "
            + "precision mediump float; "
            + "void main() { gl_FragColor = vec4(1.0,1.0,1.0,1.0); }";
            // texture2D(my_color_texture, texture_coordinate); }";

    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        if (shader == 0)
        {
            Log.d("Render","Shader handle assign failed loadShader");
        } else {
            Log.d("Render","Shader handle assign success loadShader: " + type);
        }
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] success = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, success, 0);
        if (success[0] == GLES20.GL_FALSE)
        {   // Error out and handle
            Log.e("Render", "Could not compile fragment shader : " + type);
            // Broken ass android default shader debug info
            Log.e("Render", "Error compiling shader: " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            shader = 0;
        } else {
            Log.e("Shader", "No errors");
        }
        return shader;
    }
}
