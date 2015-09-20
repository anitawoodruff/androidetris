package com.twokwy.tetris;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityNavigationEspressoTest {

    public static final String TOP_MENU_TITLE = "Tetris";

    @Rule
    public IntentsTestRule<TopMenuActivity> mActivityRule = new IntentsTestRule(TopMenuActivity.class);

    @Test
    @Ignore // FIXME When there is an easy way to trigger game over / check if in same game
    public void cannotGoBackToGameAfterEnded() {
        onView(withText("Play")).perform(click());
    }

    @Test
    public void startsTopMenuActivityWhenEndGameButtonIsClicked() {
        onView(withText("Play")).perform(click());
        onView(withText("||")).perform(click());
        onView(withText("End Game")).perform(click());

        onView(withText(TOP_MENU_TITLE)).check(matches(isDisplayed()));
    }

    @Test
    public void canNavigateToHighScoresAndThenBackWithUpButton() {
        onView(withText("High Scores")).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());

        onView(withText(TOP_MENU_TITLE)).check(matches(isDisplayed()));
    }


}