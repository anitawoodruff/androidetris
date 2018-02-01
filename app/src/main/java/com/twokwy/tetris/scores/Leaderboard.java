package com.twokwy.tetris.scores;

import android.content.SharedPreferences;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by anita on 29/01/18.
 */

public class Leaderboard {
    private static final int MAX_SIZE = 5;
    private static final String HIGH_SCORE_KEY_PREFIX = "high-score-number-";
    private final SharedPreferences mSharedPreferences;
    /**
     * This is sorted from worst to best.
     */
    private List<Integer> mScores;

    Leaderboard(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        mScores = getScores();
    }

    /**
     * @return true if the score would be a new high score.
     */
    boolean qualifies(int score) {
        return !isFull() || score > lowestHighScore();
    }

    int maxSize() {
        return MAX_SIZE;
    }

    int size() {
        return getScores().size();
    }

    /**
     * Inserts the score into the leaderboard if it qualifies as a new high score.
     * Scores added earlier register higher in the leaderboard than equal scores added later.
     * @return true if this was a new high score.
     */
    boolean addScore(int score) {
        if (!qualifies(score)) {
            return false;
        }
        mScores = getScores();
        mScores.add(score);
        Collections.sort(mScores);
        mScores.remove(0);
        writeScoresToPrefs(ImmutableList.copyOf(mScores), mSharedPreferences);
        return true;
    }

    private void writeScoresToPrefs(ImmutableList<Integer> scores,
                                    SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < scores.size(); i++) {
            editor.putInt(HIGH_SCORE_KEY_PREFIX + i, scores.get(i));
        }
        for (int i = scores.size(); i < MAX_SIZE; i++) {
            editor.remove(HIGH_SCORE_KEY_PREFIX + i);
        }
        editor.commit();
    }

    private Integer lowestHighScore() {
        return getScores().get(0);
    }

    private boolean isFull() {
        return getScores().size() == MAX_SIZE;
    }

    /**
     * Gets any high scores in order of highest to lowest.
     */
    List<Integer> getHighScores() {
        List<Integer> scoresToReturn = getScores();
        Collections.reverse(scoresToReturn);
        return scoresToReturn;
    }

    private List<Integer> getScores() {
        List<Integer> scoresToReturn = new ArrayList<>();
        for (int i = 0; i < MAX_SIZE; i++) {
            if (!mSharedPreferences.contains(HIGH_SCORE_KEY_PREFIX + i)) {
                break;
            }
            scoresToReturn.add(mSharedPreferences.getInt(HIGH_SCORE_KEY_PREFIX + i, -1));
        }
        return scoresToReturn;
    }
}
