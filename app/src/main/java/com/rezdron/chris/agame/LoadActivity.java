package com.rezdron.chris.agame;

import android.app.Fragment;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chris on 1/20/2016.
 * Activity for the load screen
 */
public class LoadActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);

        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
        */
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
            View rootView = inflater.inflate(R.layout.load,
                    container, false);

            // Always need to do a resetContext for gfxhandler so it doesn't prevent GC of old activities
            GfxResourceHandler.getInstance().resetContext(this.getActivity());

            // Create some tokens and initialize a tick emulate a basic game loop
            TokenHandler mobs = TokenHandler.getInstance();
            mobs.addToken(new TokenSheep(0));

            return rootView;
        }

        public void onDraw(Canvas layer)
        {
            Canvas temp = TokenHandler.getInstance().draw();
        }
    }
}
