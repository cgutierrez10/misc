package com.rezdron.chris.agame;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Chris on 3/21/2016.
 */
public class SpriteShader {
    public static int sp_Sprite;

    public static String vs_Sprite = "void main()"
            + "{"
            + "gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;"
            + "texture_coordinate = vec2(gl_MultiTexCoord0);"
            + "}";

    public static String fs_Sprite = "void main()"
            + "{"
            + "gl_FragColor = texture2D(my_color_texture, texture_coordinate);"
            + "}";

    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] success = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, success, 0);
        if (success[0] == 0)
        {   // Error out and handle
            Log.e("Shader", "Could not compile fragment shader :");
            Log.e("Shader", GLES20.glGetShaderInfoLog(shader));
        }
        return shader;
    }
}
