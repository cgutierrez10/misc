package com.rezdron.chris.agame;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Chris on 1/30/2016.
 * Threading code for load spinner to be threaded updating
 */
public class LoadLoopThread extends Thread {
    //private SurfaceHolder surfaceHolder;
    //private LoadBusyView view;
    private boolean running = false;
    int tick = 1;
    long last = -1;

    //public void setHolder(SurfaceHolder holder)
    //{
    //    surfaceHolder = holder;
    //}

    //public LoadLoopThread(LoadBusyView view) {
    //    this.view = view;
    //}

    public void setRunning(boolean run) {
        Log.d("mThread", "LoadLoop set " + String.valueOf(run));
        running = run;
    }

    @Override
    public void run() {
        float interval = 0;
        while (running) {
            last = SystemClock.currentThreadTimeMillis();
            tick++;
            ContentGen.getInstance().tick(tick);
            TokenHandler.getInstance().tick();
            TokenHandler.getInstance().draw();
            try {
                if (34 - (SystemClock.currentThreadTimeMillis() - last) > 0) {
                    Thread.sleep(34 - (SystemClock.currentThreadTimeMillis() - last));
                } else {
                    Log.d("PerfCrit", "Loadloop tick took too long! Falling behind.");
                }
            } catch (InterruptedException e) {
            }
        }

        interval = SystemClock.currentThreadTimeMillis() - last;
        if (interval >= 30 && interval <= 36) {
            Log.d("PerfDbg", "Fps: " + 1000 / interval);
            Log.d("PerfDbg", "Tick interval " + interval);
            Log.d("PerfDbg", "Tokens " + TokenHandler.getInstance().count());
        }
    }
}
