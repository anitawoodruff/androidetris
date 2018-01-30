package com.twokwy.tetris.scores;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static com.twokwy.tetris.scores.HighScoresActivity.HIGH_SCORE_SHARED_PREFS;
import static org.hamcrest.CoreMatchers.is;
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
        mLeaderboard = new Leaderboard(getInstrumentation().getTargetContext()
                .getSharedPreferences(HIGH_SCORE_SHARED_PREFS, 0));
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
    }

}