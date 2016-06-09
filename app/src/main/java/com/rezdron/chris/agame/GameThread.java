package com.rezdron.chris.agame;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Chris on 1/30/2016.
 * Threading code for load spinner to be threaded updating
 */
public class GameThread extends Thread {
    //private SurfaceHolder surfaceHolder;
    //private LoadBusyView view;
    private boolean running = false;
    private boolean paused = true;
    int tick = 1;
    long last = -1;

    //public void setHolder(SurfaceHolder holder)
    //{
    //    surfaceHolder = holder;
    //}

    //public GameThread(LoadBusyView view) {
    //    this.view = view;
    //}

    public void setRunning(boolean run) { running = run; }

    public void setPause(boolean pause) { paused = pause; }

    @Override
    public void run() {
        float interval = 0.0f;
        long start = SystemClock.currentThreadTimeMillis();
        while (running) {
            if (last - start > 300)
            {
                Log.d("thread","Calling quits");
                //Call out to end the game as if player lost, used for testing at present
                GameActivity.getInstance().transition("gameover");
            }
            last = SystemClock.currentThreadTimeMillis();
            if (!paused) {
                tick++;
                ContentGen.getInstance().tick(tick);
                TokenHandler.getInstance().tick();
                TokenHandler.getInstance().draw();
            }
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
