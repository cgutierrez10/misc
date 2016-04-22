package com.rezdron.chris.agame;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Chris on 3/9/2016.
 * Building whole shader pipeline?
 */
public class mglRender implements GLSurfaceView.Renderer {
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    public static float vertices[];
    public static short indices[];
    public static float uvs[];
    public FloatBuffer vertexBuffer;
    public ShortBuffer drawListBuffer;
    public FloatBuffer uvBuffer;

    float mScreenWidth = 1280;
    float mScreenHeight = 768;

    Context mContext;

    long mLastTime;
    int mProgram;

    public mglRender(Context c)
    {
        mContext = c;
        mLastTime = System.currentTimeMillis() + 100;
    }

    public void onPause()
    {
    }

    public void onResume()
    {
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        long now = System.currentTimeMillis();

        //if (mLastTime > now) return;

        long elapsed = now - mLastTime;

        Render(mtrxProjectionAndView);
        mLastTime = now;
    }

    public void Render(float[] m) {
        // Screen clear
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Get vertex shader pointer
        int mPositionHandle = GLES20.glGetAttribLocation(SpriteShader.sp_Sprite, "vPosition");
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        int mTexCoordLoc = GLES20.glGetAttribLocation(SpriteShader.sp_Sprite, "a_texCoord");
        GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);
        GLES20.glEnableVertexAttribArray(mTexCoordLoc);

        // Get handle to shape's transformation matrix
        int mtrxhandle = GLES20.glGetUniformLocation(SpriteShader.sp_Sprite, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);
        int mSamplerLoc = GLES20.glGetUniformLocation(SpriteShader.sp_Sprite, "s_texture" );

        GLES20.glUniform1i(mSamplerLoc, 0);

        if (indices == null)
        {
            Log.d("Render","Indices is null");
        }
        if (drawListBuffer == null)
        {
            Log.d("Render","drawListBuffer is null");
        }

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);

        Log.d("Render", "OnDrawFrame completed");

    }

    @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;

        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, (int)mScreenWidth, (int)mScreenHeight);

        // Clear our matrices
        for(int i=0;i<16;i++)
        {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, mScreenWidth, 0.0f, mScreenHeight, 0, 50);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        int shader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        if (shader == 0) {
            Log.d("Render", "155 Shader handle assign failed, onsurfacechanged");
        } else {
            Log.d("Render", "155 Shader handle assign success");
        }
        // These lines need to specify SpriteShader.vs_Sprite and fs_Sprite

        int vertexShader = SpriteShader.loadShader(GLES20.GL_VERTEX_SHADER, SpriteShader.vs_Sprite);
        int fragmentShader = SpriteShader.loadShader(GLES20.GL_FRAGMENT_SHADER, SpriteShader.fs_Sprite);

        SpriteShader.sp_Sprite = GLES20.glCreateProgram();
        GLES20.glAttachShader(SpriteShader.sp_Sprite, vertexShader);
        GLES20.glAttachShader(SpriteShader.sp_Sprite, fragmentShader);
        GLES20.glLinkProgram(SpriteShader.sp_Sprite);

        GLES20.glUseProgram(SpriteShader.sp_Sprite);
        // Shapes first
        RenderQuads();
        // Image/textures second
        SetupImage();
    }

    public void SetupImage()
    {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0); //spritesheet);
        uvs = new float[]{0.5f, 0.5f, 0.5f, 1.5f, 1.5f, 1.5f, 1.5f, 0.5f,};

        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        //Textures, load one in
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GfxResourceHandler.getInstance().getRsx("sheep"), 0);
    }

    void RenderQuads()
    {
        Log.d("Render","Quad render entered");
        // 32x32 triangles quad
        vertices = new float[]
                {       10.0f,   400f, 0.0f,
                        10.0f, 10.0f, 0.0f,
                        400f,   10.0f, 0.0f,
                        400f,     400f, 0.0f,
                };

        indices = new short[] {0,1,2,0,2,3};


        /* This should be passed in as parameter */
        /* Also including extra 4 points indicating atlas frame? */
        /* Possibly 6 ints, <x,y> drawat, <x,y> drawfrom, height/width ? */
        // Below is fuller example above is minimum to test functionality
        /*
        vertices = new float[12];
        for(int i=0;i<1;i++)
        {
            vertices[(i*12) + 0] = 1;
            vertices[(i*12) + 1] = 33;
            vertices[(i*12) + 2] = 0;

            vertices[(i*12) + 3] = 1;
            vertices[(i*12) + 4] = 1;
            vertices[(i*12) + 5] = 0;

            vertices[(i*12) + 6] = 33;
            vertices[(i*12) + 7] = 1;
            vertices[(i*12) + 8] = 0;

            vertices[(i*12) + 9] = 33;
            vertices[(i*12) + 10] = 33;
            vertices[(i*12) + 11] = 0;
        }


        indices = new short[6];
        int last = 0;
        for(int i=0;i<6;i++)
        {
            indices[(i*6) + 0] = (short) (last + 0);
            indices[(i*6) + 1] = (short) (last + 1);
            indices[(i*6) + 2] = (short) (last + 2);
            indices[(i*6) + 3] = (short) (last + 0);
            indices[(i*6) + 4] = (short) (last + 2);
            indices[(i*6) + 5] = (short) (last + 3);
            last = last + 4;
        }
        */

        // vertices is triangle pair forming quad
        // Vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        // Init byte buffer draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
    }
}
