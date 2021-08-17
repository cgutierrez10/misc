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
import android.renderscript.Matrix2f;
import android.util.DisplayMetrics;
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
 * TODO: Debug screen resolution, possibly related to screen density, tablet is very off from phone displays
 */
class mglRender implements GLSurfaceView.Renderer {
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    private static float[] shadowvertices;
    private static short[] shadowindices;
    private static float[] shadowuvs;
    private static float vertices[];
    private static short indices[];
    private static float uvs[];
    private static FloatBuffer vertexBuffer;
    private static ShortBuffer drawListBuffer;
    private static FloatBuffer uvBuffer;
    private static int indexcount;

    private static boolean flip;
    private static short lastidx;
    private static short arrayslen;
    //private static ByteBuffer wavevert;
    //private static ByteBuffer waveidx;
    private static float time = SystemClock.uptimeMillis() / 250;


    float mScreenWidth = 1280;
    float mScreenHeight = 768;

    private static mglRender instance = new mglRender();

    public static mglRender getInstance() { return instance; }

    mglRender()
    {
    }

    public void onPause()
    {
    }

    public void onResume()
    {
        time = (SystemClock.uptimeMillis() / 250) - time;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        Render(mtrxProjectionAndView);
    }

    private void Render(float[] m) {
        long last = SystemClock.currentThreadTimeMillis();
        // Flip buffers if needed
        if (flip) {
            ByteBuffer bb = ByteBuffer.allocateDirect((vertices.length * 4)+16);
            bb.order(ByteOrder.nativeOrder());
            vertexBuffer = bb.asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);

            // Vertex draw ordering data (all assumed as triangles, triangle fan may be better?)
            ByteBuffer dlb = ByteBuffer.allocateDirect((indices.length * 2)+8);
            dlb.order(ByteOrder.nativeOrder());
            drawListBuffer = dlb.asShortBuffer();
            drawListBuffer.put(indices);
            drawListBuffer.position(0);

            // Texture coordinate data
            ByteBuffer uvsb = ByteBuffer.allocateDirect((uvs.length * 4)+16);
            uvsb.order(ByteOrder.nativeOrder());
            uvBuffer = uvsb.asFloatBuffer();
            uvBuffer.put(uvs);
            uvBuffer.position(0);

            flip = false;
        }

        // Screen clear
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        if (vertexBuffer != null) {
            // Not sure why uMVPMatrix isn't sticking as a uniform, should be possible to set only
            // at onchange screen orientation and load and not per-frame

            // Draw background waves
            GLES20.glUseProgram(SpriteShader.sp_Wave);
            int mPositionHandle = GLES20.glGetAttribLocation(SpriteShader.sp_Wave, "vPosition");
            GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
            GLES20.glEnableVertexAttribArray(mPositionHandle);
            GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(SpriteShader.sp_Wave, "uMVPMatrix"), 1, false, m, 0);
            // Debugging by setting a constant here
            GLES20.glUniform1f(GLES20.glGetUniformLocation(SpriteShader.sp_Wave, "time"), ((float) ContentGen.getInstance().tickCount) / 25);
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

            // Draw everything else
            vertexBuffer.position(12);
            uvBuffer.position(8);
            drawListBuffer.position(0);
            GLES20.glUseProgram(SpriteShader.sp_Sprite);
            // Get vertex shader pointer
            mPositionHandle = GLES20.glGetAttribLocation(SpriteShader.sp_Sprite, "vPosition");
            GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
            GLES20.glEnableVertexAttribArray(mPositionHandle);

            // Get texture pointer
            int mTexCoordLoc = GLES20.glGetAttribLocation(SpriteShader.sp_Sprite, "a_texCoord");
            GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);
            GLES20.glEnableVertexAttribArray(mTexCoordLoc);

            GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(SpriteShader.sp_Sprite, "uMVPMatrix"), 1, false, m, 0);
            GLES20.glUniform1i(GLES20.glGetUniformLocation(SpriteShader.sp_Sprite, "s_texture"), 0);

            GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexcount * 6, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
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

    public float[] getMVP()
    {
        return mtrxProjectionAndView;
    }

    @Override public void onSurfaceChanged(GL10 gl, int width, int height) {

        mScreenWidth = width;
        mScreenHeight = height;

        // Matrix for orthographic projection
        // Clear our matrices
        for(int i=0;i<16;i++)
        {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        // Need to arrange the gl view and scissors so game renders with deepest possible
        // sea level is full screen on both dimensions then if screen rotation
        // is to shorter then scale down and ensure stretch keeps aspect ratio square
        // Either rotation should end up trimming or adding to the right edge while keeping vertical
        // same
        // Max of mscreenheight, mscreenwidth for both?
        float bigdim = Math.max(mScreenWidth,mScreenHeight);
        float mindim = Math.min(mScreenWidth,mScreenHeight);
        //This may rebreak the orientation changes, trying to work towards proper scaling
        Matrix.orthoM(mtrxProjection,0,0f, mindim*(mScreenWidth/bigdim), 0.0f, mindim*(mScreenHeight/bigdim), 0, 50);
        //Matrix.orthoM(mtrxProjection, 0, 0.0f, 0.05f, 0.0f, 0.05f, -1.0f, 1.0f);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);

        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        SpriteShader.sp_Sprite = GLES20.glCreateProgram();
        SpriteShader.sp_Wave = GLES20.glCreateProgram();

        // These lines need to specify SpriteShader.vs_Sprite and fs_Sprite
        int vertexShader = SpriteShader.loadShader(GLES20.GL_VERTEX_SHADER, SpriteShader.vs_Sprite);
        int fragmentShader = SpriteShader.loadShader(GLES20.GL_FRAGMENT_SHADER, SpriteShader.fs_Sprite);

        GLES20.glAttachShader(SpriteShader.sp_Sprite, vertexShader);
        GLES20.glAttachShader(SpriteShader.sp_Sprite, fragmentShader);

        GLES20.glAttachShader(SpriteShader.sp_Wave, SpriteShader.loadShader(GLES20.GL_VERTEX_SHADER, SpriteShader.vs_Sprite));
        GLES20.glAttachShader(SpriteShader.sp_Wave, SpriteShader.loadShader(GLES20.GL_FRAGMENT_SHADER, SpriteShader.fs_Wave));

        GLES20.glLinkProgram(SpriteShader.sp_Sprite);
        GLES20.glLinkProgram(SpriteShader.sp_Wave);

        GLES20.glUseProgram(SpriteShader.sp_Wave);

        // Additional uniforms for wave shader
        GLES20.glUniform2f(GLES20.glGetUniformLocation(SpriteShader.sp_Wave,"resolution"), 1920,1080); // mScreenWidth, mScreenHeight);
        float sealevel = 512 * 0.8f;
        GLES20.glUniform1f(GLES20.glGetUniformLocation(SpriteShader.sp_Wave,"sealevel"), sealevel);
        // Variable setup for wave functions
        // Should only have to push these values once
        float p1 = 19.0f;
        float p2 = 24.0f;
        GLES20.glUniform1f(GLES20.glGetUniformLocation(SpriteShader.sp_Wave,"phase1"), p1);
        GLES20.glUniform1f(GLES20.glGetUniformLocation(SpriteShader.sp_Wave,"phase2"), p2);
        GLES20.glUniform1f(GLES20.glGetUniformLocation(SpriteShader.sp_Wave,"amp1"), (p2/(p1 + p2) * 10));
        GLES20.glUniform1f(GLES20.glGetUniformLocation(SpriteShader.sp_Wave,"amp2"), (p1/(p1 + p2) * 10));
        GLES20.glUniform1f(GLES20.glGetUniformLocation(SpriteShader.sp_Wave,"norm"), (float) (1.0/Math.sqrt(p1 * p1 + p2 * p2)));

        GLES20.glUseProgram(SpriteShader.sp_Sprite);

        // Shapes first
        // Image/textures second
        SetupImage();
        arrayslen = 10;
        lastidx = 0;
        shadowvertices = new float[120];
        shadowindices = new short[60];
        shadowuvs = new float[80];

        // Clip screen bounds losing bottom range and keeping stuck to left edge (where player is)

        //GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
        //GLES20.glScissor((int) (1921 - mScreenHeight), 0, (int) mScreenHeight, (int) mScreenWidth);

    }

    void SetupImage()
    {
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
        // Images are going to be compiled in gfxrsxhandler and provided via a separate call
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, GfxResourceHandler.getInstance().getRsx("sheep"), 0);
    }

    static void setFlip() {
        // If lastidx = 0 then no spriteblits were called since last flip
        // Ideally this would do a blank spriteblit then allow screen clears
            vertices = shadowvertices;
            indices = shadowindices;
            uvs = shadowuvs;
            flip = true;
            indexcount = lastidx;
            lastidx = 0;
            arrayslen = 10;
            shadowvertices = new float[120];
            shadowindices = new short[60];
            shadowuvs = new float[80];
    }

    // x,y are drawat points
    // width, height are drawsize
    // gfx_x are texture cooridinates
    // gfx_frame is a offset for animated frames, multiply by gfx_height to get vertical offset if needed
    void spriteBlit(int x, int y, int width, int height, float gfx_x, float gfx_height, int gfx_frame, int gfx_max)
    {
        // Each shadow buffer is expected to be allocated in render (when flip = yes)
        // And reallocated in render
        // Add pair of triangles to vertexbuffer
        if (shadowvertices == null) {
            lastidx = 0;
            arrayslen = 10;
            shadowvertices = new float[120];
            shadowindices = new short[60];
            shadowuvs = new float[80];
        }
        if (arrayslen == lastidx+2) {
            float[] shadvert = new float[arrayslen*12 + 120];
            short[] shadindices = new short[arrayslen*6 + 60];
            float[] shaduvs = new float[arrayslen*8 + 80];
            // Expand the arrays if this happened, arrayslen should be 1 set of values per each sprite
            System.arraycopy(shadowvertices, 0, shadvert, 0, arrayslen * 12);
            System.arraycopy(shadowindices, 0, shadindices, 0, arrayslen * 6);
            System.arraycopy(shadowuvs, 0, shaduvs, 0, arrayslen * 8);
            shadowvertices = shadvert;
            shadowindices = shadindices;
            shadowuvs = shaduvs;
            arrayslen = (short) (arrayslen + 10);
        }

        if (lastidx == 0 && width != mScreenWidth) {
            // Add full screen for wave draw
            spriteBlit(0, ((int) mScreenHeight) * -1, (int) mScreenWidth, (int) mScreenHeight * 2, 1, 0, 0, 0);
            //Log.d("Sprite","Added fullscreen sprite:" + String.valueOf(mScreenWidth));
        }

        // Vertices Ranges 0-11
        // Going to rewrite this to use normal lower left origin instead of trying to correct from upper right
        shadowvertices[0+(lastidx*12)] = x+width;
        shadowvertices[1+(lastidx*12)] = y;
        shadowvertices[2+(lastidx*12)] = 0;
        shadowvertices[3+(lastidx*12)] = x+width;
        shadowvertices[4+(lastidx*12)] = y+height;
        shadowvertices[5+(lastidx*12)] = 0;

        shadowvertices[6+(lastidx*12)] = x;
        shadowvertices[7+(lastidx*12)] = y+height;
        shadowvertices[8+(lastidx*12)] = 0;
        shadowvertices[9+(lastidx*12)] = x;
        shadowvertices[10+(lastidx*12)] = y;
        shadowvertices[11+(lastidx*12)] = 0;

        // Get atlas coords from gfxresourcehandler

        // This isn't even needed
        // Indexes Ranges 0-5
        shadowindices[0+(lastidx*6)] = (short)(0+(lastidx*4));
        shadowindices[1+(lastidx*6)] = (short)(1+(lastidx*4));
        shadowindices[2+(lastidx*6)] = (short)(2+(lastidx*4));
        shadowindices[3+(lastidx*6)] = (short)(0+(lastidx*4));
        shadowindices[4+(lastidx*6)] = (short)(2+(lastidx*4));
        shadowindices[5+(lastidx*6)] = (short)(3+(lastidx*4));

        // UVS Ranges 0-7
        // May also not actually be needed?
        /* 0,0 */
        shadowuvs[0+(lastidx*8)] = gfx_x;
        shadowuvs[1+(lastidx*8)] = 0.25f * gfx_frame;
        /* 0,1 */
        shadowuvs[2+(lastidx*8)] = gfx_x;
        shadowuvs[3+(lastidx*8)] = 0.25f * (gfx_frame+1) ;
        /* 1,1 */
        shadowuvs[4+(lastidx*8)] = gfx_x + 1.0f/3;
        shadowuvs[5+(lastidx*8)] = 0.25f * (gfx_frame+1);
        /* 1,0 */
        shadowuvs[6+(lastidx*8)] = gfx_x + 1.0f/3;
        shadowuvs[7+(lastidx*8)] = 0.25f * gfx_frame;
        lastidx++;
    }
}
