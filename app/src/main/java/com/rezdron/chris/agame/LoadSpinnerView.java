package com.rezdron.chris.agame;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Loading animation, not technically a spinner
 */
public class LoadSpinnerView
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
        Log.d("surface",String.valueOf(format) + " " + String.valueOf(width) + " " + String.valueOf(height));
        //TokenHandler.getInstance().cullAt(Math.max(width, height));
    }

    public LoadSpinnerView(Context context) {
        super(context);
        mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
    }

    public LoadSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
    }

    public LoadSpinnerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mThread = new LoadLoopThread(this);
        mThread.setHolder(this.getHolder());
        getHolder().addCallback(this);
    }

    public void init()
    {
        mThread = new LoadLoopThread(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas render)
    {
        super.draw(render);
        TokenHandler.getInstance().draw(render);
    }
}
