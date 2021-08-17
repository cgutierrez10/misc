package com.rezdron.chris.agame;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Chris on 1/20/2016.
 * Activity for the activity_load screen
 */
public class LoadActivity extends AppCompatActivity {
    //private GLSurfaceView glSurfaceView;
    //private GameView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Some changes for GL support */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        Log.d("ActivityMode", "About to create renderer for LoadActivity");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Ensure player is created
        //Player.getInstance();


        loadComplete();
    }

    /*
    // Check if the system supports OpenGL ES 2.0.
    final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
    final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

    if (!(supportsEs2))
    {
        // This is where you could create an OpenGL ES 1.x compatible
        // renderer if you wanted to support both ES 1 and ES 2.
        Log.d("Render","OPEN GL ES 2.0 failed");
    }
    */

    public void loadComplete() {
        // Load only proceeds forward -> gameplay
        if (GameMode.getInstance().changeMode(GameMode.MODE.GAMEPLAY)) {
            startActivity(new Intent(this, GameActivity.class));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            //View rootView = inflater.inflate(R.layout.activity_load,
            //        container, false);
            return inflater.inflate(R.layout.activity_load,
                    container, false);

            //return rootView;
        }
    }
}
