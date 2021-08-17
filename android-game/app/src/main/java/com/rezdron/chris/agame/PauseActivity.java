package com.rezdron.chris.agame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class PauseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_run);
        Log.d("transition", "PauseActivity tried to start");
        //We need to get the instance of the LayoutInflater, use the context of this activity
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.getApplicationContext().LAYOUT_INFLATER_SERVICE);
        //Inflate the view from a predefined XML layout

        View layout = inflater.inflate(R.layout.activity_pause, (ViewGroup) findViewById(R.id.PausePop));
        // create a 300px width and 470px height PopupWindow
        //PopupWindow pw = new PopupWindow(GameActivity.this);
        PopupWindow pw = new PopupWindow(layout, 300, 470, true);

        pw.setContentView(layout);
        // display the popup in the center
        try {
            pw.showAtLocation(findViewById(R.id.GameRunLayout), Gravity.CENTER, 50, 50);
        }
        catch (Exception e) {
            Log.d("transition",e.getMessage());
        }
        Log.d("transition", "PauseActivity started?");
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
            ((GameView) findViewById(R.id.GameView)).unPause();
            Log.d("transition", "Called to resume in pauseactivity");
            this.finish();
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
