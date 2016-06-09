package com.rezdron.chris.agame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    Bundle passedvals;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passedvals = getIntent().getExtras();
        setContentView(R.layout.activity_gameover);
        ((TextView) findViewById(R.id.scoreVal)).setText(passedvals.getString("score"));
        ((TextView) findViewById(R.id.timeVal)).setText(passedvals.getString("time"));
    }

    // If this activity goes to background then it
    // should resume directly from the original menu
    @Override
    protected void onPause() {
        transition("title");
        super.onPause();
    }

    public void goButtons(View v)
    {
        if (v.getId() == R.id.overRestart)
        {
            transition("gameplay");
        } else if (v.getId() == R.id.overTitle)
        {
            transition("title");
        }
    }

    public void transition(String mode) {
        if ((mode == "title") && (GameMode.getInstance().changeMode(GameMode.MODE.TITLE))) {
            // Back to title, can be called manually or will default if backgrounded here
            Intent title = new Intent(this,MainActivity.class);
            title.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(title);
        }
        if ((mode == "gameplay") && (GameMode.getInstance().changeMode(GameMode.MODE.GAMEPLAY))) {
            // Start a new game, shouldn't need to go back to loading for performance issues
            startActivity(new Intent(this, GameActivity.class));
        }
    }

}
