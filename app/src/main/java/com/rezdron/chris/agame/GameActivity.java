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
    }

    public void PauseButton(View v)
    {
        ((GameView) findViewById(R.id.GameView)).Pause();
        //transition("pause");
        Log.d("transition", "Popup tried to start");
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        helpBuilder.setView(inflater.inflate(R.layout.activity_pause, null));
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        Log.d("transition", "Popup started?");
    }

    public void unpause(View v)
    {
        transition("gameplay");
    }

    @Override
    protected void onPause() {
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
        if ((mode == "gameplay") && (GameMode.getInstance().changeMode(GameMode.MODE.PAUSE))) {
            // Will need this to call to the view to suspend
            //glSurfaceView.onPause();
            startActivity(new Intent(this, PauseActivity.class));
        }
        else if ((mode == "pause") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEPLAY))) {
            // Should always be able to go to gameactivity.onresume ?
            ((GameView) findViewById(R.id.GameView)).unPause();
            this.finish();
        }
        else if ((mode == "gameplay") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEOVER))) {
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
