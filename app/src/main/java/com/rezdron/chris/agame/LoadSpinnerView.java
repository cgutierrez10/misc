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
 * TODO: document your custom view class.
 */
public class LoadSpinnerView extends SurfaceView {
    private SurfaceHolder surfaceHolder = this.getHolder();

    public LoadSpinnerView(Context context) {
        super(context);
    }

    public LoadSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadSpinnerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        TokenHandler.getInstance().draw(canvas);
        //GfxResourceHandler.getInstance().blitAt("sheep",10,10);

    }

    public void start() {

        new Thread(new Runnable() {
            public void run() {
                while(true) {
                    final Canvas canvas = surfaceHolder.lockCanvas();
                    try {
                        synchronized (surfaceHolder) {
                            TokenHandler.getInstance().tick();
                            TokenHandler.getInstance().draw(canvas);
                        }
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }).start();
    }

}
