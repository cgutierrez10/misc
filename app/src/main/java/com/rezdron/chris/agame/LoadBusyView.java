package com.rezdron.chris.agame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Loading animation, not technically a spinner
 */
public class LoadBusyView
        extends GLSurfaceView {
    private myGLRenderer mRenderer;
    private LoadLoopThread mThread;


    public LoadBusyView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        Log.d("Render","About to create renderer");
        mRenderer = new myGLRenderer(context);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        /*
        mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
        */
        //init(context);
    }

    /*
    public void init(Context context)
    {
        mRenderer = new myGLRenderer(context);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
        mThread = new LoadLoopThread(this);
        Log.d("Render", "Init called for glsurfaceview");
    } */

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
}
