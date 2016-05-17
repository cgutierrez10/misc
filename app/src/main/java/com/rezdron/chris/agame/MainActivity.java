package com.rezdron.chris.agame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GameMode state = GameMode.getInstance();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mContext = getApplicationContext();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void stateButton(View v)
    {
        Intent switchmode = null;
        switch ((String)v.getTag())
        {
            case "Loading":
                state.changeMode(GameMode.MODE.LOADING);
                switchmode = new Intent(this, LoadActivity.class);
                break;
            case "Menu":
                state.changeMode(GameMode.MODE.TITLE);
                break;
            case "Game":
                state.changeMode(GameMode.MODE.GAMEPLAY);
                break;
            case "Pause":
                state.changeMode(GameMode.MODE.PAUSE);
                break;
            case "Exit":
                state.changeMode(GameMode.MODE.EXIT);
                break;
            default:
                break;
        }

        if (switchmode != null) {
            GfxResourceHandler.getInstance().resetContext(mContext);
            if (GfxResourceHandler.getInstance().getState() == null) {
                Log.d("Error","Appcontext is null");
            }
            else {
                startActivity(switchmode);
            }
        }
        TextView status = (TextView)findViewById(R.id.textView2);
        status.setText(state.getText());
    }
}
