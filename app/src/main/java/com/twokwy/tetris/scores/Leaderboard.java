package com.twokwy.tetris.scores;

import android.content.SharedPreferences;

/**
 * Created by anita on 29/01/18.
 */

public class Leaderboard {
    private final SharedPreferences mSharedPreferences;

    Leaderboard(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    boolean qualifies(int score) {
        return true;
    }

    int maxSize() {
        return 5;
    }

    int size() {
        return 0;
    }
}
