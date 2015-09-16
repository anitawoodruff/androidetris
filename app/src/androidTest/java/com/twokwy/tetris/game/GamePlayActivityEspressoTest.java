package com.twokwy.tetris.game;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.twokwy.tetris.TopMenuActivity;
import com.twokwy.tetris.scores.HighScoresActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GamePlayActivityEspressoTest {

    @Rule
    public IntentsTestRule<GamePlayActivity> mActivityRule = new IntentsTestRule(GamePlayActivity.class);

    @Test
    public void showsContinueAndEndGameButtonsOnlyWhenUserClicksPause() {
        // Initially buttons should not be there
        onView(withText("Continue")).check(doesNotExist());
        onView(withText("End Game")).check(doesNotExist());

        // user clicks the pause button
        onView(withText("||")).perform(click());

        // Buttons should now be visible
        onView(withText("Continue")).check(matches(isDisplayed()));
        onView(withText("End Game")).check(matches(isDisplayed()));

        // User clicks continue
        onView(withText("Continue")).perform(click());

        // buttons should no longer be there
        onView(withText("Continue")).check(doesNotExist());
        onView(withText("End Game")).check(doesNotExist());
    }

}