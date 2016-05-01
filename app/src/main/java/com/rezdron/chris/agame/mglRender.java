package com.rezdron.chris.agame;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Chris on 3/9/2016.
 * Building whole shader pipeline?
 */
public class mglRender implements GLSurfaceView.Renderer {
    private final int intsize = (Integer.SIZE/Byte.SIZE);
    private final int shortsize = (Short.SIZE/Byte.SIZE);
    private final int floatsize = (Float.SIZE/Byte.SIZE);
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    public static float vertices[];
    public static short indices[];
    public static float uvs[];
    public FloatBuffer vertexBuffer;
    public ShortBuffer drawListBuffer;
    public FloatBuffer uvBuffer;
    public int indexcount;
    public int renders = 0;

    private float[] shadowvertices;
    private short[] shadowindices;
    private float[] shadowuvs;
    private boolean flip;
    private short lastidx;
    private short arrayslen;

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
        long last = SystemClock.currentThreadTimeMillis();
        // Try this just once
        if (renders == 0) {
            spriteBlit(300, 300, 128, 128, 0);
            spriteBlit(600, 300, 128, 128, 0);
            spriteBlit(300, 600, 128, 128, 0);
            spriteBlit(100, 100, 128, 128, 0);
            setFlip();
            renders++;
        }

        // Flip buffers if needed
        if (flip) {
            // vertex buffer = vertexshadowbuffer
            // texture buffer = textureshadowbuffer
            // Discard both old buffers if needed
            // allocate out both new buffers
            // Vertex data
            vertices = shadowvertices;
            indices = shadowindices;
            uvs = shadowuvs;

            ByteBuffer bb = ByteBuffer.allocateDirect(shadowvertices.length * 4);
            bb.order(ByteOrder.nativeOrder());
            vertexBuffer = bb.asFloatBuffer();
            vertexBuffer.put(shadowvertices);
            vertexBuffer.position(0);

            // Vertex draw ordering data (all assumed as triangles, triangle fan may be better?)
            ByteBuffer dlb = ByteBuffer.allocateDirect(shadowindices.length * 2);
            dlb.order(ByteOrder.nativeOrder());
            drawListBuffer = dlb.asShortBuffer();
            drawListBuffer.put(shadowindices);
            drawListBuffer.position(0);

            // Texture coordinate data
            ByteBuffer uvsb = ByteBuffer.allocateDirect(shadowuvs.length * 4);
            uvsb.order(ByteOrder.nativeOrder());
            uvBuffer = uvsb.asFloatBuffer();
            uvBuffer.put(shadowuvs);
            uvBuffer.position(0);

            indexcount = lastidx;
            lastidx = 0;
            arrayslen = 0;
            flip = false;
            // Blank out original vertex lists
            // This set breaks the render, seems the bytebuffers above are using these memory locations?
        }


        // Screen clear
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Get vertex shader pointer
        int mPositionHandle = GLES20.glGetAttribLocation(SpriteShader.sp_Sprite, "vPosition");
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Get texture pointer
        int mTexCoordLoc = GLES20.glGetAttribLocation(SpriteShader.sp_Sprite, "a_texCoord");
        GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);
        GLES20.glEnableVertexAttribArray(mTexCoordLoc);

        // Get handle to shape's transformation matrix
        int mtrxhandle = GLES20.glGetUniformLocation(SpriteShader.sp_Sprite, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

        int mSamplerLoc = GLES20.glGetUniformLocation(SpriteShader.sp_Sprite, "s_texture");

        GLES20.glUniform1i(mSamplerLoc, 0);
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        if (indexcount != 0) {
            Log.d("Render","Geometry draw called: " + String.valueOf(indexcount) );
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexcount * 6, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        }
        try {
            // If no geometry then just allow the screen to be clear
            GLES20.glDisableVertexAttribArray(mPositionHandle);
            GLES20.glDisableVertexAttribArray(mTexCoordLoc);
        }
        catch (Exception e)
        {
            Log.d("Render", "empty uvs list: " + String.valueOf(uvBuffer.position()));
            Log.d("Render", "empty vertex list: " + String.valueOf(vertexBuffer.position()));
            Log.d("Render", "indexcount: " + String.valueOf(indexcount));
        }


        try {
            if (34 - (SystemClock.currentThreadTimeMillis() - last) > 0) {
                Thread.sleep(34 - (SystemClock.currentThreadTimeMillis() - last));
            } else {
                Log.d("PerfCrit", "Render took too long! Falling behind.");
            }
        } catch (InterruptedException e) {
        }
    }

    @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;

        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, (int) mScreenWidth, (int) mScreenHeight);

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

        Log.d("Render","Setupimage entered");

        uvs = new float[]{0.0f, 0.0f,
                          0.0f, 1.0f,
                          1.0f, 1.0f,
                          1.0f, 0.0f,};

        // Texture buffer
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

        if (GfxResourceHandler.getInstance().getRsx("sheep") == null)
        {
            Log.d("Render","Sheep texture failed to load");
        }
        // Need to modify gfxresourcehandler to provide a composite all setting
        // And ability to reference index in array
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

        // vertices is triangle pair forming quad
        // Vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * floatsize);
        bb.order(ByteOrder.nativeOrder());
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

    public void setFlip() {
        // If lastidx = 0 then no spriteblits were called since last flip
        // Ideally this would do a blank spriteblit then allow screen clears
        if (lastidx != 0) {

            flip = true;
        }
    }

    public void spriteBlit(int x, int y, int width, int height, int gfx)
    {
        // Each shadow buffer is expected to be allocated in render (when flip = yes)
        // And reallocated in render
        // Add pair of triangles to vertexbuffer
        if (arrayslen == lastidx) {
            float[] shadvert = new float[arrayslen + 120];
            short[] shadindices = new short[arrayslen + 60];
            float[] shaduvs = new float[arrayslen + 80];
            // Expand the arrays if this happened, arrayslen should be 1 set of values per each sprite
            if (shadowvertices != null) {
                System.arraycopy(shadowvertices, 0, shadvert, 0, arrayslen);
                System.arraycopy(shadowindices, 0, shadindices, 0, arrayslen);
                System.arraycopy(shadowuvs, 0, shaduvs, 0, arrayslen);
            }
            shadowvertices = shadvert;
            shadowindices = shadindices;
            shadowuvs = shaduvs;
            arrayslen = (short) (arrayslen + 10);
        }

        // Vertices Ranges 0-11
        shadowvertices[0+(lastidx*12)] = (float) x;
        shadowvertices[1+(lastidx*12)] = (float) y+height;
        shadowvertices[2+(lastidx*12)] = 0.0f;
        shadowvertices[3+(lastidx*12)] = (float) x;
        shadowvertices[4+(lastidx*12)] = (float) y;
        shadowvertices[5+(lastidx*12)] = 0.0f;
        shadowvertices[6+(lastidx*12)] = (float) x+width;
        shadowvertices[7+(lastidx*12)] = (float) y;
        shadowvertices[8+(lastidx*12)] = 0.0f;
        shadowvertices[9+(lastidx*12)] = (float) x+width;
        shadowvertices[10+(lastidx*12)] = (float) y+height;
        shadowvertices[11+(lastidx*12)] = 0.0f;
        // Get atlas coords from gfxresourcehandler

        // This isn't even needed
        // Indexes Ranges 0-5
        shadowindices[0+(lastidx*6)] = (short)(0+(lastidx*6));
        shadowindices[1+(lastidx*6)] = (short)(1+(lastidx*6));
        shadowindices[2+(lastidx*6)] = (short)(2+(lastidx*6));
        shadowindices[3+(lastidx*6)] = (short)(0+(lastidx*6));
        shadowindices[4+(lastidx*6)] = (short)(2+(lastidx*6));
        shadowindices[5+(lastidx*6)] = (short)(3+(lastidx*6));

        // UVS Ranges 0-7
        // May also not actually be needed?
        shadowuvs[0+(lastidx*8)] = 0.0f;
        shadowuvs[1+(lastidx*8)] = 0.0f;
        shadowuvs[2+(lastidx*8)] = 0.0f;
        shadowuvs[3+(lastidx*8)] = 1.0f;
        shadowuvs[4+(lastidx*8)] = 1.0f;
        shadowuvs[5+(lastidx*8)] = 1.0f;
        shadowuvs[6+(lastidx*8)] = 1.0f;
        shadowuvs[7+(lastidx*8)] = 0.0f;
        lastidx++;
    }
}
