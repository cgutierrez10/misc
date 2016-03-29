package com.rezdron.chris.agame;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.opengl.GLSurfaceView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Loading animation, not technically a spinner
 */
public class LoadBusyView
        extends GLSurfaceView
        implements SurfaceHolder.Callback {
    private GLRenderer mRenderer;
    private LoadLoopThread mThread;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.setRunning(true);                     //will make calls to
        mThread.start();                              //onDraw()
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            mThread.setRunning(false);                //Tells thread to stop
            mThread.join();                           //Removes thread from mem.
        } catch (InterruptedException e) {}
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        ContentGen.getInstance().setWidth(width);
        Log.d("surface", String.valueOf(format) + " " + String.valueOf(width) + " " + String.valueOf(height));
        //TokenHandler.getInstance().cullAt(Math.max(width, height));
    }

    public LoadBusyView(Context context) {
        super(context);
        /*
        setEGLContextClientVersion(2);
        mRenderer = new GLRenderer(context);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
        */
        init(context);
    }

    public LoadBusyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context)
    {
        setEGLContextClientVersion(2);
        mRenderer = new GLRenderer(context);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
        mThread = new LoadLoopThread(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        if (e.getAction() == MotionEvent.ACTION_DOWN)
        {
            // Player tap, bounce the player a little bit
            Player.getInstance().setAccel(10);
        }
        return true;
    }

    /*
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas render)
    {
        if (render != null) {
            super.draw(render);
            TokenHandler.getInstance().draw(render);
        } else { Log.d("super.draw", ""); }
    }
    */
}
