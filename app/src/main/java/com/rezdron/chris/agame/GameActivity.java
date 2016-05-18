package com.rezdron.chris.agame;

import android.app.Fragment;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Player.getInstance();

        glSurfaceView = new GameView(this);
        setContentView(glSurfaceView);

        //May need to replace, and reactivate the player token on game start/ends
        //Player.revive(); // Set player active to true
        //Player.place();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();

        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.getApplicationContext().LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.activity_pause, (ViewGroup) findViewById(R.id.pause_pop));
            // create a 300px width and 470px height PopupWindow
            PopupWindow pw = new PopupWindow(layout, 300, 470, true);
            // display the popup in the center
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    public void transition(String mode) {
        if ((mode == "pause") && (GameMode.getInstance().changeMode(GameMode.MODE.PAUSE))) {
            // Will need this to call to the view to suspend
            //glSurfaceView.onPause();
            startActivity(new Intent(this, PauseActivity.class));
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
