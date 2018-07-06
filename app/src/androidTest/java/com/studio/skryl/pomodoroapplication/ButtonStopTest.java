package com.studio.skryl.pomodoroapplication;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.studio.skryl.pomodoroapplication.utils.AppPreferences;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ButtonStopTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickStartPomodoro_stopPomodoro() throws Exception {
        AppPreferences preferences = AppPreferences.getInstance(mainActivityActivityTestRule.getActivity());
        String timePomodoro = preferences.getRestTimeStr();

        onView(withId(R.id.action_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.action_button)).perform(click());

        onView(withText(timePomodoro)).check(matches(isDisplayed()));
    }
}
