package com.rezdron.chris.agame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Chris on 1/30/2016.
 * Threading code for load spinner to be threaded updating
 */
public class LoadLoopThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private LoadBusyView view;
    private boolean running = false;
    int tick = 1;


    public void setHolder(SurfaceHolder holder)
    {
        surfaceHolder = holder;
    }

    public LoadLoopThread(LoadBusyView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        Log.d("Thread", "LoadLoop set " + String.valueOf(run));
        running = run;
    }

    @Override
    public void run() {
        TokenSpider bouncy = null;
        while (running) {
            /* Every 50 ticks load a new sheep 50 px over, modulo by screen width */


            /*if (tick % 350 == 0)
            {
                //ContentGen.getInstance().tick(tick);
                //TokenHandler.getInstance().addToken(new TokenSheep(0,tick % 400,10));
            }*/
            /*
            if (tick % 500 == 0)
            {
                if (bouncy != null) {
                    bouncy.deactivate();
                }
                bouncy = new TokenSpider(0,tick % 150,10);
                TokenHandler.getInstance().addToken(bouncy);
            }*/
            ContentGen.getInstance().tick(tick);
            tick++;
            TokenHandler.getInstance().tick();
            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.draw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            try{ Thread.sleep(17);
            } catch(InterruptedException e){ }
        }
    }
}
