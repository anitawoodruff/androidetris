package com.twokwy.tetris.scores;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.twokwy.tetris.TopMenuActivity;
import com.twokwy.tetris.game.GamePlayActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HighScoresActivityEspressoTest {

    @Rule
    public IntentsTestRule<HighScoresActivity> mActivityRule = new IntentsTestRule(HighScoresActivity.class);

    @Test
    public void startsTopMenuActivityWhenUserClicksMainMenuButton() {
        onView(withText("Main Menu")).perform(click());
        intended(hasComponent(TopMenuActivity.class.getName()));
    }

}