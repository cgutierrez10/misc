package com.rezdron.chris.agame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameOverActivity extends AppCompatActivity {
    Bundle passedvals = getIntent().getExtras();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }
}
