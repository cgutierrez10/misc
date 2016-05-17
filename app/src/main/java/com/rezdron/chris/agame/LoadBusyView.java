package com.rezdron.chris.agame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Loading animation, not technically a spinner
 */
public class LoadBusyView
        extends GLSurfaceView {
    protected mglRender mRenderer;
    private LoadLoopThread mThread;


    public LoadBusyView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        Log.d("Render", "About to create renderer");
        //mRenderer = new mglRender(context);
        mRenderer = new mglRender();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);


        mThread = new LoadLoopThread();
        mThread.setRunning(true);
        mThread.start();
        //init(context);
    }

    public void loadComplete() {
        if (GameMode.getInstance().getMode() != GameMode.MODE.LOADING) {
            mThread.setRunning(false);
            mThread = null;
        }
    }

    /*
    @Override
    public void onPause()
    {
        mThread.setRunning(false);
    }

    @Override
    public void onResume()
    {
        mThread.start();
    }
    */

    @Override
    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mThread.setRunning(false);
        mThread = null;
    }

    /*
    public void init(Context context)
    {
        mRenderer = new mglRender(context);
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
