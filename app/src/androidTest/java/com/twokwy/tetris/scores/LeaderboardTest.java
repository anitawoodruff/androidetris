package com.twokwy.tetris.scores;

import android.content.SharedPreferences;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static com.twokwy.tetris.scores.HighScoresActivity.HIGH_SCORE_SHARED_PREFS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 * This is an instrumentation test so the Leaderboard can be tested with a real SharedPrefs.
 */
@RunWith(AndroidJUnit4.class)
public class LeaderboardTest {
    private static final int MAX_SIZE = 5;

    private Leaderboard mLeaderboard;

    @Before
    public void setUp() throws Exception {
        SharedPreferences sharedPreferences = getInstrumentation().getTargetContext()
                .getSharedPreferences(HIGH_SCORE_SHARED_PREFS, 0);
        sharedPreferences.edit().clear().commit();
        mLeaderboard = new Leaderboard(sharedPreferences);
    }

    @Test
    public void firstScoreAlwaysQualifies() throws Exception {
        assertThat(mLeaderboard.qualifies(5), is(true));
        assertThat(mLeaderboard.qualifies(0), is(true));
        assertThat(mLeaderboard.qualifies(100), is(true));
    }

    @Test
    public void maxSizeIsFive() throws Exception {
        assertThat(mLeaderboard.maxSize(), is(MAX_SIZE));
    }

    @Test
    public void addingScoresIncreasesSizeUpToMax_allZero() throws Exception {
        assertThat(mLeaderboard.size(), is(0));
        for (int i = 1; i <= MAX_SIZE; i++) {
            assertThat(mLeaderboard.addScore(0), is(true));
            assertThat(mLeaderboard.size(), is(i));
        }
        assertThat(mLeaderboard.addScore(0), is(false));
        assertThat(mLeaderboard.size(), is(MAX_SIZE));
        assertThat(mLeaderboard.getHighScores(), contains(0, 0, 0, 0, 0));
    }

    @Test
    public void addingScoresIncreasesSizeUpToMax_incrementingScores() throws Exception {
        assertThat(mLeaderboard.size(), is(0));
        for (int i = 1; i <= MAX_SIZE; i++) {
            assertThat(mLeaderboard.addScore(i), is(true));
            assertThat(mLeaderboard.size(), is(i));
        }
        boolean scoreWasInserted = mLeaderboard.addScore(MAX_SIZE + 1);
        assertThat(scoreWasInserted, is(true));
        assertThat(mLeaderboard.size(), is(MAX_SIZE));
        assertThat(mLeaderboard.getHighScores(), contains(6, 5, 4, 3, 2));
    }

    @Test
    public void qualifyDistinguishesScoresWhenLeaderboardFull() throws Exception {
        for (int i = 1; i <= MAX_SIZE; i++) {
            assertThat(mLeaderboard.addScore(0), is(true));
            assertThat(mLeaderboard.size(), is(i));
        }
        assertThat(mLeaderboard.qualifies(1), is(true));
        assertThat(mLeaderboard.qualifies(0), is(false));
    }

    @Test
    public void multipleInstancesWithSamePrefsShareScores() throws Exception {
        SharedPreferences sharedPreferences = getInstrumentation().getTargetContext()
                .getSharedPreferences("shared", 0);
        sharedPreferences.edit().clear().commit();
        Leaderboard firstLeaderboard = new Leaderboard(sharedPreferences);
        Leaderboard secondLeaderboard = new Leaderboard(sharedPreferences);
        firstLeaderboard.addScore(1);
        assertThat(secondLeaderboard.getHighScores(), contains(1));
        assertThat(secondLeaderboard.size(), is(1));
        firstLeaderboard.addScore(2);
        firstLeaderboard.addScore(3);
        firstLeaderboard.addScore(4);
        firstLeaderboard.addScore(5);
        assertThat(firstLeaderboard.getHighScores(), contains(5, 4, 3, 2, 1));
        assertThat(secondLeaderboard.getHighScores(), contains(5, 4, 3, 2, 1));

        assertThat(secondLeaderboard.qualifies(0), is(false));
        assertThat(secondLeaderboard.addScore(0), is(false));

        assertThat(secondLeaderboard.qualifies(6), is(true));
        assertThat(secondLeaderboard.addScore(6), is(true));

        assertThat(secondLeaderboard.getHighScores(), contains(6, 5, 4, 3, 2));
        assertThat(firstLeaderboard.getHighScores(), contains(6, 5, 4, 3, 2));

    }
}