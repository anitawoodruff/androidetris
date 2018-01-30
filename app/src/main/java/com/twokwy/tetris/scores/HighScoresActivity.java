package com.twokwy.tetris.scores;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.R;

/**
 * Created by anita on 16/09/2015.
 */
public class HighScoresActivity extends Activity {

    public static final String HIGH_SCORE_SHARED_PREFS = "scores";

    private static final ImmutableList<Integer> TEXT_VIEW_IDS = ImmutableList.of(
            R.id.high_score_1,
            R.id.high_score_2,
            R.id.high_score_3,
            R.id.high_score_4,
            R.id.high_score_5
    );

    /**
     * These are the pref keys which store the names of the top five high scorers, in order of
     * score from highest to lowest. Must be kept in sync with SCORE_PREF_KEYS.
      */
    public static final ImmutableList<String> NAME_PREF_KEYS = ImmutableList.of(
            "high_score_1_name",
            "high_score_2_name",
            "high_score_3_name",
            "high_score_4_name",
            "high_score_5_name"
    );

    /**
     * These are the pref keys which store the scores of the top five high scorers, from highest to
     * lowest. Must be kept in sync with NAME_PREF_KEYS.
     */
    public static final ImmutableList<String> SCORE_PREF_KEYS = ImmutableList.of(
            "high_score_1_score",
            "high_score_2_score",
            "high_score_3_score",
            "high_score_4_score",
            "high_score_5_score"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        populateHighScores();
    }

    private void populateHighScores() {
        for (int i = 0; i < 5; i++) {
            final TextView slot = (TextView) findViewById(TEXT_VIEW_IDS.get(i));
            final SharedPreferences prefs = getSharedPreferences(HIGH_SCORE_SHARED_PREFS, 0);
            String name = prefs.getString(NAME_PREF_KEYS.get(i), "");
            final int score = prefs.getInt(SCORE_PREF_KEYS.get(i), 0);
            slot.setText(String.format("%d. %s \t %d", i + 1, name, score));
        }
    }
}
