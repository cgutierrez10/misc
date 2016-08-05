package com.rezdron.chris.agame;

import android.app.Activity;
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
    Activity owner;

    //public void setHolder(SurfaceHolder holder)
    //{
    //    surfaceHolder = holder;
    //}

    //public GameThread(LoadBusyView view) {
    //    this.view = view;
    //}

    public void resetGame() {
        ContentGen.getInstance().reset();
        TokenHandler.getInstance().reset();
        Player.getInstance().reset();
        // Maybe handle this as part of tokenhandler freeing everything?
        //Player.getInstance().reset();
    }

    public void setRunning(boolean run) { running = run; }

    public void setPause(boolean pause) { paused = pause; }

    public void setOwner(Activity mActivity) {
        Log.d("activity","Passed new owner to mthread");
        owner = mActivity;
    }

    @Override
    public void run() {
        float interval = 0.0f;
        long start = SystemClock.currentThreadTimeMillis();
        while (running) {
            // TODO: Debug end timer can maybe be removed?
            // Pausing will reset the timed end interval and maybe throw off score counter
            // Only used for debug
            /* This if breaks the pause function somehow
            if (paused)
            {
                start = SystemClock.currentThreadTimeMillis();
            }*/
            last = SystemClock.currentThreadTimeMillis();
            // TODO: Pause does not appear to work correctly on phone, works fine on tablet
            if (!paused) {
                // Debug auto-end timer -->
                if ((last - start) > 300)
                {
                    Log.d("thread","Calling quits");
                    //Call out to end the game as if player lost, used for testing at present
                    ((GameActivity) owner).transition("gameover");
                }
                // <-- End of auto-end timer chunk
                if (owner == null)
                {
                    setPause(true);
                    Log.d("Activity","thread paused owner is null");
                }
                tick++;
                ContentGen.getInstance().tick(tick);
                TokenHandler.getInstance().collide();
                TokenHandler.getInstance().tick();
                TokenHandler.getInstance().draw();
                if (!Player.getInstance().getActive())
                {
                    Log.d("thread","Player ded, moving along now.");
                    //Call out to end the game as if player lost, used for testing at present
                    ((GameActivity) owner).transition("gameover");
                }
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
