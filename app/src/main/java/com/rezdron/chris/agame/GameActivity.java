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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        Log.d("ActivityMode", "About to create renderer for GameActivity");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Player.getInstance();
        //glSurfaceView = new GameView(this);

        //setContentView(glSurfaceView);
        setContentView(R.layout.activity_game_run);

        //May need to replace, and reactivate the player token on game start/ends
        //Player.revive(); // Set player active to true
        //Player.place();
        ((GameView) findViewById(R.id.GameView)).unPause();
    }

    /* Toggles between pausing and unpausing mode to handle double back-button presses */
    public void PauseButton(View v) {
        if (pause) {
            Log.d("transition", "Unpausing");
            unPause(new View(null));
        } else {
            transition("pause");
            Log.d("transition", "Popup tried to start");
            if (pw == null) {
                AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                helpBuilder.setView(inflater.inflate(R.layout.activity_pause, null));
                pw = helpBuilder.create();
                pw.setCancelable(false);
            }
            pw.show();
            Log.d("transition", "Popup started?");
        }
    }


    /* Never use the view v here, it may be null */
    public void unPause(View v) { transition("gameplay"); }

    @Override
    protected void onPause() {
        transition("pause");
        Log.d("transition", "Popup tried to start");
        if (pw == null) {
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            helpBuilder.setView(inflater.inflate(R.layout.activity_pause, null));
            pw = helpBuilder.create();
            pw.setCancelable(false);
        }
        pw.show();
        Log.d("transition", "Popup started?");
        super.onPause();
        //glSurfaceView.onPause();
        //((GameView) findViewById(R.id.GameView)).onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //glSurfaceView.onResume();
    }

    public void transition(String mode) {
        Log.d("transition",GameMode.getInstance().getMode().toString());
        if ((mode == "pause") && (GameMode.getInstance().changeMode(GameMode.MODE.PAUSE))) {
            // Will need this to call to the view to suspend
            ((GameView) findViewById(R.id.GameView)).Pause();
            pause = true;
        }
        else if ((mode == "gameplay") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEPLAY))) {
            ((GameView) findViewById(R.id.GameView)).unPause();
            if (pw != null) { pw.hide();}
            pause = false;
            Log.d("transition","Unpaused");
        }
        else if ((mode == "gameover") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEOVER))) {
            // No gameover activity yet
            //startActivity(new Intent(this, PauseActivity.class));
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
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_load,
                    container, false);
            return rootView;
        }
    }
}
