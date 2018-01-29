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
        assertThat(mLeaderboard.maxSize(), is(5));
    }

    @Test
    public void sizeIsInitiallyZero() throws Exception {
        assertThat(mLeaderboard.size(), is(0));
    }

}