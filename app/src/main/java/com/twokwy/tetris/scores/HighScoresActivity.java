package com.twokwy.tetris.scores;

import android.app.Activity;
import android.os.Bundle;

import com.twokwy.tetris.R;

/**
 * Created by anita on 16/09/2015.
 */
public class HighScoresActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
