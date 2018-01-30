package com.twokwy.tetris.game;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.twokwy.tetris.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GamePlayActivityEspressoTest {

    @Rule
    public IntentsTestRule<GamePlayActivity> mActivityRule = new IntentsTestRule(GamePlayActivity.class);

    @Test
    public void showsPauseGameDialogWhenUserClicksPause() {
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
    public void showsPauseGameDialogWhenUserPressesBack() {
        // Initially should not be paused
        onView(withText("Game Paused")).check(doesNotExist());

        // user triggers pause with Back
        pressBack();

        // Dialog should now be visible
        onView(withText("Game Paused")).check(matches(isDisplayed()));

        // user dismisses dialog
        pressBack();

        // game should no longer be paused
        onView(withText("Game Paused")).check(doesNotExist());

        // user triggers pause with Back
        pressBack();

        // Dialog should now be visible
        onView(withText("Game Paused")).check(matches(isDisplayed()));

    }

    @Test
    public void showsEndGameDialogWhenUserMashesDrop() {
        onView(withText("drop")).perform(click());
        for (int i = 0; i < 20; i++) {
            try {
                onView(withText("drop")).perform(click());
            } catch (NoMatchingViewException e) {
                // That's ok, game over dialog must be showing already
            }
        }

        // Dialog should now be visible
        onView(withText("GAME OVER")).check(matches(isDisplayed()));
        onView(withId(R.id.score_label_text_box)).check(matches(withText(containsString
                ("SCORE:"))));

        // TODO reenable when high scores implemented
//        onView(withText("OK")).perform(click());
//
//        intended(hasComponent(HighScoresActivity.class.getName()));
    }
}