package com.twokwy.tetris.scores;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by anita on 29/01/18.
 */

public class Leaderboard {
    private static final int MAX_SIZE = 5;
    private final SharedPreferences mSharedPreferences;
    /**
     * This is sorted from best to worst.
     */
    private List<Integer> mScores;

    Leaderboard(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        mScores = new ArrayList<>(MAX_SIZE);
    }

    boolean qualifies(int score) {
        return true;
    }

    int maxSize() {
        return MAX_SIZE;
    }

    int size() {
        return mScores.size();
    }

    boolean addScore(int score) {
        boolean newHighScore = false;
        if (mScores.size() < MAX_SIZE || score > mScores.get(mScores.size() - 1)) {
            newHighScore = true;
        }
        mScores.add(score);
        Collections.sort(mScores);
        if (mScores.size() > MAX_SIZE) {
            mScores.subList(MAX_SIZE, mScores.size()).clear();
        }
        return newHighScore;
    }
}
