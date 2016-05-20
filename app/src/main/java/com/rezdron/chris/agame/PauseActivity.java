package com.rezdron.chris.agame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PauseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("ActivityMode", "About to create renderer for PauseActivity");

        setContentView(R.layout.activity_pause);
    }


    public void transition(String mode) {
        // Pause can go almost anywhere
        if ((mode == "title") && (GameMode.getInstance().changeMode(GameMode.MODE.TITLE))) {
            // Will need this to call to the view to suspend
            //glSurfaceView.onPause();
            startActivity(new Intent(this, MainActivity.class));
        }
        else if ((mode == "gameplay") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEPLAY))) {
            // Should always be able to go to gameactivity.onresume ?
        }
        else if ((mode == "gameover") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEOVER))) {
            // No gameover activity yet
            // Should always be able to go to gameactivity.onresume ?
            //startActivity(new Intent(this, PauseActivity.class));
        }
        else if ((mode == "exit") && (GameMode.getInstance().changeMode(GameMode.MODE.EXIT))) {
            // Exiting function may not be another activity here
        }

        /*
        TITLE,
        LOADING,
        GAMEPLAY,
        PAUSE,
        GAMEOVER,
        EXIT
        */
        // No transition to loading, pause
    }
}
