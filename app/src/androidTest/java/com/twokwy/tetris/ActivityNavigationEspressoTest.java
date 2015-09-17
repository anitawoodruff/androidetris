package com.twokwy.tetris;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityNavigationEspressoTest {

    @Rule
    public IntentsTestRule<TopMenuActivity> mActivityRule = new IntentsTestRule(TopMenuActivity.class);

    @Test
    public void cannotGoBackToGameAfterEnded() {
        onView(withText("Play")).perform(click());
        onView(withText("||")).perform(click());
        onView(withText("End Game")).perform(click());
        onView(withText("Tetris High Scores")).check(matches(isDisplayed()));

        pressBack();

        onView(withText("Tetris")).check(matches(isDisplayed()));
    }

    @Test
    public void canNavigateToHighScoresAndThenBackWithUpButton() {
        onView(withText("High Scores")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());

        onView(withText("Tetris")).check(matches(isDisplayed()));
    }

    @Test
    public void upReturnsToTopMenuAfterGameEnded() {
        onView(withText("Play")).perform(click());
        onView(withText("||")).perform(click());
        onView(withText("End Game")).perform(click());

        onView(withContentDescription("Navigate up")).perform(click());

        onView(withText("Tetris")).check(matches(isDisplayed()));
    }

}