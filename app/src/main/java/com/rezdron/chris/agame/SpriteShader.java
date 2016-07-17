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
    public static int sp_Wave;

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
            + "varying vec2 v_texCoord;"
            + "uniform sampler2D s_texture;"
            //+ "void main() { gl_FragColor = vec4(1.0,1.0,1.0,1.0); }";
            + "void main() { gl_FragColor = texture2D( s_texture, v_texCoord ); }";
            //+ "texture2D(my_color_texture, texture_coordinate); }";

    /* This one will not work right now, note use of mouse position */
    /* Needs to be modified for a tick based position */
    /* Maybe determine a wave function at start time using long long wavelengths? */
    // Second function for varying the amplitudes?
    // Whatever function chosen needs to match the physics wave function
    public static String fs_Wave = "precision mediump float;"
    + "uniform float time;"
    + "uniform vec2 resolution;"
    + "void main( void ) {"
    + "vec2 position = ( gl_FragCoord.xy / resolution.xy ) - 0.30;"
    + "float waves = 0.05*sin((time+position.x)*7.0+time) + 0.02*sin((position.x-time)*3.0);"
    + "float color = position.y < waves ?(waves-position.y)*20.0 : 0.0;"
    + "color = min(pow(color,0.5),1.0);"
    + "gl_FragColor = vec4( position.y < waves ? mix(vec3(0.59,0.63,0.86),vec3(0.1,0.5,50),color) : vec3(0.3,0.5,0.5), 1.0 );"
    + "}";

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
