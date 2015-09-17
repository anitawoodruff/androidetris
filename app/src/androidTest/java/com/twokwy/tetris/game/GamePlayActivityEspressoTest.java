package com.twokwy.tetris.game;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GamePlayActivityEspressoTest {

    @Rule
    public IntentsTestRule<GamePlayActivity> mActivityRule = new IntentsTestRule(GamePlayActivity.class);

    @Test
    public void showsPauseGameDialogOnlyWhenUserClicksPause() {
        // Initially should not be paused
        onView(withText("Game Paused")).check(doesNotExist());

        // user clicks the pause button
        onView(withText("||")).perform(click());

        // Dialog with buttons should now be visible
        onView(withText("Game Paused")).check(matches(isDisplayed()));
        onView(withText("Continue")).check(matches(isDisplayed()));
        onView(withText("End Game")).check(matches(isDisplayed()));

        // User clicks continue
        onView(withText("Continue")).perform(click());

        // game no longer paused
        onView(withText("Game Paused")).check(doesNotExist());
    }

    @Test
    public void startsHighScoresActivityWhenEndGameButtonIsClicked() {
        onView(withText("||")).perform(click());
        onView(withText("End Game")).perform(click());

        intended(hasComponent(HighScoresActivity.class.getName()));
    }
}