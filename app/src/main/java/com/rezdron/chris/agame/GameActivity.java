package com.rezdron.chris.agame;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Chris on 1/20/2016.
 * Activity for the activity_load screen
 */
public class GameActivity extends AppCompatActivity {

    //private GLSurfaceView glSurfaceView;
    private AlertDialog pw;
    boolean pause = false;
    private int score = 0;

    // Added for debugging
    static GameActivity instance = new GameActivity();
    static GameActivity getInstance() { return instance;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        Log.d("ActivityMode", "About to create renderer for GameActivity");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Player.getInstance();
        setContentView(R.layout.activity_game_run);

        //May need to replace, and reactivate the player token on game start/ends
        //Player.revive(); // Set player active to true
        //Player.place();
        ((GameView) findViewById(R.id.GameView)).unPause();
    }

    /* Toggles between pausing and unpausing mode to handle double back-button presses */
    public void PauseButton(View v) {
        if (pause) {
            transition("gameplay");
        } else {
            transition("pause");
            if (pw == null) {
                AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                helpBuilder.setView(inflater.inflate(R.layout.activity_pause, null));
                pw = helpBuilder.create();
                pw.setCancelable(false);
            }
            pw.show();
        }
    }

    public void QuitButton(View v) {
        Log.d("thread","Quit called");
        transition("gameover");
    }

    @Override
    protected void onPause() {
        transition("pause");
        if (pw == null) {
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            helpBuilder.setView(inflater.inflate(R.layout.activity_pause, null));
            pw = helpBuilder.create();
            pw.setCancelable(false);
        }
        pw.show();
        super.onPause();
    }

    public void transition(String mode) {
        if ((mode == "pause") && (GameMode.getInstance().changeMode(GameMode.MODE.PAUSE))) {
            // Will need this to call to the view to suspend
            ((GameView) findViewById(R.id.GameView)).Pause();
            pause = true;
        }
        else if ((mode == "gameplay") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEPLAY))) {
            ((GameView) findViewById(R.id.GameView)).unPause();
            if (pw != null) { pw.hide();}
            pause = false;
        }
        else if ((mode == "gameover") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEOVER))) {
            // No gameover activity yet
            Intent go = new Intent(this, GameOverActivity.class);
            go.putExtra("score",String.valueOf(TokenHandler.getInstance().score));
            go.putExtra("time",String.valueOf(((GameView) findViewById(R.id.GameView)).getActiveTime()));
            startActivity(go);
        }
        // No transition to title, loading, gameplay or exit, only to pause and gameover
        // Does not directly transition to exiting must go through pause -> exit
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        PauseButton(new View(this));
    }
}
