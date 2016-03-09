package com.rezdron.chris.agame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Loading animation, not technically a spinner
 */
public class LoadBusyView_bak
        extends SurfaceView
        implements SurfaceHolder.Callback {
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

    public LoadBusyView_bak(Context context) {
        super(context);
        //mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
    }

    public LoadBusyView_bak(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
    }

    public LoadBusyView_bak(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
    }

    public void init() {} // mThread = new LoadLoopThread(this); }

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
}
