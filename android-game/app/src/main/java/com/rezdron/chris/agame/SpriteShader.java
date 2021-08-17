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
    // This is the best wave function I can come up with for now
    // http://www.glslsandbox.com/e#34138.0

    // Better method
    // phase1 is prime, phase2 is 1-1.5 larger minimum up to 1.5 times phase1
    // Higher end of 1.5 times is more evenly choppy, lower end flattens and rewidens
    // Amplitudes calculated off phase contributes to choppy/flattening shapes
    // Looks fairly even for any set of primes, larger phase1 prime shorter wavelengths
    // Primes from 11 to 29 seems nice
    //
    // Pass these in after compiling/linking shader, send once and should persist
    // "uniform float phase1 = 17.0;
    // "uniform float phase2 = 18.0;
    // "uniform float amp1 = (phase2/((phase1 + phase2) * 10.0));
    // "uniform float amp2 = (phase1/((phase1 + phase2) * 10.0));
    //
    //+ "norm = 1.0/sqrt(phase1*phase1 + phase2*phase2);"
    //+ "vec2 position = ( gl_FragCoord.xy / resolution.xy ) - 0.40;"
    //+ "float waves = amp2*sin((position.x*phase1 + time)*phase1*norm)"
    //+ "        + amp1*sin((position.x*phase2 - time)*phase2*norm)"
    //+ "        + 0.035*sin(time*2.5 + position.x);"
    //
    public static String fs_Wave = "precision mediump float;"
    + "uniform float time;"
    + "uniform vec2 resolution;"
    + "uniform float sealevel;"
    + "uniform float phase1;"
    + "uniform float phase2;"
    + "uniform float amp1;"
    + "uniform float amp2;"
    + "uniform float norm;"
    + "void main( void ) {"
    //+ "norm = 1.0/sqrt(phase1*phase1 + phase2*phase2);"
    //+ "   vec2 position = (gl_FragCoord.xy);"
    + "   vec2 position = ( gl_FragCoord.xy );"
    + "   position.x = gl_FragCoord.x / resolution.x;" // Range [0,1]
    + "   position.y = gl_FragCoord.y;" // / 5.0;"          // Range appox [0,200] ? maybe let range == and adjust wave multiplier?
    //+ "   float waves = amp2*sin((gl_FragCoord.x*phase1 + time)*phase1*norm)"
    //+ "           + amp1*sin((gl_FragCoord.x*phase2 - time)*phase2*norm)"
    //+ "           - 0.035*sin(time*2.5 + gl_FragCoord.x);"
    + "   float waves = amp2*sin((position.x*phase1 + time)*phase1*norm)"
    + "           + amp1*sin((position.x*phase2 - time)*phase2*norm)"
    + "           - 0.035*sin(time*2.5 + position.x);"
    //+ "   float waves = 0.065*sin(time*3.0 + position.x*7.0)"
    //+ "               + 0.015*sin(position.x*17.0 - time*2.0)"
    //+ "               + 0.035*sin(time*2.5 + position.x);"
    //+ "   waves = waves + (resolution.y - 400.0);" // Sea level ~200 y +/- waves?
    + "   waves = (waves * 5.0) + sealevel;" // Sea level ~200 y +/- waves?
    + "   float color = position.y < waves ? (waves - position.y)*20.0 : 0.0;"
    + "   color = min(pow(color,0.5),1.0);"
    + "   gl_FragColor = vec4( position.y < waves ? mix(vec3(0.59,0.63,0.86),vec3(0.1,0.5,50),color) : vec3(0.3,0.5,0.5), 1.0 );"
    //+ "   gl_FragColor = vec4( gl_FragCoord.y < waves ? mix(vec3(0.59,0.63,0.86),vec3(0.1,0.5,50),color) : vec3(0.3,0.5,0.5), 1.0 );"
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
