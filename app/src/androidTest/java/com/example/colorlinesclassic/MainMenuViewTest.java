package com.example.colorlinesclassic;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import com.example.colorlinesclassic.Activities.MainMenuActivity;

import java.lang.reflect.Field;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainMenuViewTest {
    @Rule
    public ActivityTestRule<MainMenuActivity> activityTestRule =
            new ActivityTestRule<>(MainMenuActivity.class);

    @Test
    public void checkDefaultValues()throws NoSuchFieldException,IllegalAccessException{
        MainMenuActivity menuActivity = activityTestRule.getActivity();
        Field timeField = MainMenuActivity.class.
                getDeclaredField("timeForDoubleBackPressed");
        timeField.setAccessible(true);
        int time = (int) timeField.get(menuActivity);
        Assert.assertTrue(time>1000);
    }

    @Test
    public void checkContainerIsDisplayed() {
        onView(ViewMatchers.withId(R.id.newGameBtn))
                .check(matches(isDisplayed())).check(matches(withText(R.string.new_game)));
        onView(ViewMatchers.withId(R.id.quitGameBtn))
                .check(matches(isDisplayed())).check(matches(withText(R.string.quit_game)));
        onView(ViewMatchers.withId(R.id.menuOptionsLayout))
                .check(matches(hasChildCount(2)));
    }
}
