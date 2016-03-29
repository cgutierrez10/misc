package com.rezdron.chris.agame;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.Matrix;

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
public class GLRenderer implements Renderer {
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

    public GLRenderer(Context c)
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

        if (mLastTime > now) return;

        long elapsed = now - mLastTime;

        Render(mtrxProjectionAndView);
        mLastTime = now;
    }

    private void Render(float[] m) {
        // Screen clear
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Get vertex shader pointer
        int mPositionHandle = GLES20.glGetAttribLocation(SpriteShader.sp_Sprite, "vPosition");

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        int mTexCoordLoc = GLES20.glGetAttribLocation(SpriteShader.sp_Sprite, "a_texCoord");
        GLES20.glEnableVertexAttribArray(mTexCoordLoc);
        GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

        // Get handle to shape's transformation matrix
        int mtrxhandle = GLES20.glGetUniformLocation(SpriteShader.sp_Sprite, "uMVPMatrix");

        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);
        int mSamplerLoc = GLES20.glGetUniformLocation(SpriteShader.sp_Sprite, "s_texture" );

        GLES20.glUniform1i(mSamplerLoc, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);

    }

    @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;

        /* Fragment added to setup renderer for full screen 2d mode */
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0.0f, width, 0.0f, height, 0.0f, 1.0f);
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4x(0x10000, 0x10000, 0x10000, 0x10000);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        /* End fragment added */
        // Ensure viewport is fullscreen
        GLES20.glViewport(0, 0, (int) mScreenWidth, (int) mScreenHeight);

        for(int i=0;i<16;i++)
        {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        Matrix.orthoM(mtrxProjection, 0, 0.0f, mScreenWidth, 0.0f, mScreenHeight, 0, 50);
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        SetupTriangle();
        GLES20.glClearColor(0.0f,0.0f,0.0f,1);

        // These lines need to specify SpriteShader.vs_Sprite and fs_Sprite
        int vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER, SpriteShader.vs_Sprite);
        int fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER, SpriteShader.fs_Sprite);

        SpriteShader.sp_Sprite = GLES20.glCreateProgram();
        GLES20.glAttachShader(SpriteShader.sp_Sprite, vertexShader);
        GLES20.glAttachShader(SpriteShader.sp_Sprite, fragmentShader);
        GLES20.glLinkProgram(SpriteShader.sp_Sprite);

        GLES20.glUseProgram(SpriteShader.sp_Sprite);
    }

    public void SetupImage()
    {
        uvs = new float[]{0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,};

        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(uvs);
        vertexBuffer.position(0);

        //Textures, load one in
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GfxResourceHandler.getInstance().getRsx("spider"), 0);
    }

    public void SetupTriangle()
    {
        vertices = new float[] {10.0f, 200f, 0.0f, 10.0f, 100f, 0.0f, 100f, 100f, 0.0f,};

        indices = new short[] {0, 1, 2};

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
    }

    void RenderSprite(int spritesheet, int spritex, int spritey, int texturew, int textureh, int x, int y, int w, int h )
    {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,spritesheet);
        // Texturing, select texels from atlas
        uvs = new float[8];
        uvs[0] = 0;
        uvs[1] = 0;
        uvs[2] = 0;
        uvs[3] = 1;
        uvs[4] = 1;
        uvs[5] = 1;
        uvs[6] = 1;
        uvs[7] = 0;

        // Texture Buffer
        /* 4 bytes per float? Always true on android? */
        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        // Texture gen
        int[] texturenames = new int[1];
        GLES20.glGenTextures(1, texturenames, 0);

        // Retrieve image from rsx, probably need to modify this to get lowerleft + h/w
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);

        // Set Filter
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GfxResourceHandler.getInstance().getRsx("sheep"), 0);

        /*
        glColor4ub(255, 255, 255, 255);
        glBindTexture(GL_TEXTURE_2D, spritesheet);
        glBegin(GL_QUADS);
        glTexCoord2d(spritex / (double) texturew, spritey / (double) textureh);
        glVertex2f(x, y);
        glTexCoord2d((spritex + w) / (double) texturew, spritey / (double) textureh);
        glVertex2f(x + w, y);
        glTexCoord2d((spritex + w) / (double) texturew, (spritey + h) / (double) textureh);
        glVertex2f(x + w, y + h);
        glTexCoord2d(spritex / (double) texturew, (spritey + h) / (double) textureh);
        glVertex2f(x, y + h);
        gl.glEnd();
        */
    }

    void RenderQuads()
    {
        // 32x32 triangles quad
        vertices = new float[]
                {  10.0f, 42f, 0.0f,
                        10.0f, 10.0f, 0.0f,
                        42f, 10.0f, 0.0f,
                        42f, 42f, 0.0f,
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
