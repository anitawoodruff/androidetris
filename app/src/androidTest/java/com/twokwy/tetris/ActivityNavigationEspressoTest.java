package com.twokwy.tetris;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityNavigationEspressoTest {

    public static final String TOP_MENU_TITLE = "Tetris";
    private static final String HIGH_SCORES_TITLE = "Tetris High Scores";

    @Rule
    public IntentsTestRule<TopMenuActivity> mActivityRule = new IntentsTestRule(TopMenuActivity.class);

    @Test
    public void cantGoBackToGameAfterNewHighScore() {
        onView(withText("Play")).perform(click());
        onView(withText("||")).perform(click());
        onView(withText("End Game")).perform(click());

        // Click through the new high score dialog:
        onView(withText(equalToIgnoringCase("ok"))).perform(click());
        onView(withText(HIGH_SCORES_TITLE)).check(matches(isDisplayed()));

        // Check it goes 'back' to the top menu (not game).
        pressBack();
        onView(withText(TOP_MENU_TITLE)).check(matches(isDisplayed()));
    }

    @Test
    public void canNavigateToHighScoresAndThenBackWithUpButton() {
        onView(withText("High Scores")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());

        onView(withText(TOP_MENU_TITLE)).check(matches(isDisplayed()));
    }


}