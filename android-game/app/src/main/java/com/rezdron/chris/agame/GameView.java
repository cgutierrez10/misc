package com.rezdron.chris.agame;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Loading animation, not technically a spinner
 */
public class GameView
        extends GLSurfaceView {
    protected mglRender mRenderer;
    private GameThread mThread;
    DisplayMetrics dm = new DisplayMetrics();

    public GameView(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        //mRenderer = new mglRender(context);
        mRenderer = new mglRender();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        //this.getHolder().setFixedSize(1024,1024);


        mThread = new GameThread();
        mThread.setRunning(true);
        mThread.start();
        //init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context,attrs);
        dm = context.getResources().getDisplayMetrics();
        Log.d("Renderer","Display metrics configured");
        setEGLContextClientVersion(2);

        //mRenderer = new mglRender(context);
        mRenderer = new mglRender();
        //mRenderer.setResolution(dm.heightPixels,dm.widthPixels); //,dm.heightPixels);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        mThread = new GameThread();
        mThread.setRunning(true);
        mThread.start();
        //init(context);
    }

        /* Not in use anymore */
    public void loadComplete() {
        if (GameMode.getInstance().getMode() != GameMode.MODE.LOADING) {
            mThread.setRunning(false);
            mThread = null;
        }
    }

    public void Pause() {
        mThread.setPause(true);
    }
    public void unPause() { Log.d("transition","Unpausing"); mThread.setPause(false); }
    public void newStart() {
        mThread.resetGame();

        TokenHandler.getInstance().addToken(new TokenBobber(0,496,410));
        TokenHandler.getInstance().addToken(new TokenBobber(0,255,410));

        /*
        TokenHandler.getInstance().addToken(new TokenBobber(0,70,450));
        TokenHandler.getInstance().addToken(new TokenBobber(0,140,450));
        TokenHandler.getInstance().addToken(new TokenBobber(0,210,450));
        TokenHandler.getInstance().addToken(new TokenBobber(0,280,450));
        TokenHandler.getInstance().addToken(new TokenBobber(0,350,450));
        TokenHandler.getInstance().addToken(new TokenBobber(0,420,450));
        */
        Log.d("transition","Unpausing");
        mThread.setPause(false); }

    public void setOwner(Activity mActivity) {
        mThread.setOwner(mActivity);
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
        if (mThread != null) {
            mThread.setRunning(false);
            mThread = null;
        }
    }

    /*
    public void init(Context context)
    {
        mRenderer = new mglRender(context);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        mThread = new GameThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
        mThread = new GameThread(this);
        Log.d("Render", "Init called for glsurfaceview");
    } */

    public int getActiveTime() {
        return mThread.tick;
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

    public void rotate(boolean orientation)
    {
        // True is landscape
        // False is portrait
    }
}
