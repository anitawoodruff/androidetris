package com.twokwy.tetris.scores;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HighScoresActivityEspressoTest {

    @Rule
    public IntentsTestRule<HighScoresActivity> mActivityRule = new IntentsTestRule(HighScoresActivity.class);

    @Test
    public void displaysHighScoresTitle() {
        onView(withText("Tetris High Scores")).check(matches(isDisplayed()));
    }

}