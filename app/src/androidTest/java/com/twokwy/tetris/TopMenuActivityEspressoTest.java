package com.twokwy.tetris;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.twokwy.tetris.game.GamePlayActivity;
import com.twokwy.tetris.scores.HighScoresActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TopMenuActivityEspressoTest {

    @Rule
    public IntentsTestRule<TopMenuActivity> mActivityRule = new IntentsTestRule(TopMenuActivity.class);

    @Test
    public void playButtonStartsGamePlayActivity() {
        onView(withText("Play")).perform(click());
        intended(hasComponent(GamePlayActivity.class.getName()));
    }

    @Test
    public void highScoresButtonStartsHighScoresActivity() {
        onView(withText("High Scores")).perform(click());
        intended(hasComponent(HighScoresActivity.class.getName()));
    }
}