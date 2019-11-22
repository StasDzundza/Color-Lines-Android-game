package com.example.colorlinesclassic;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import com.example.colorlinesclassic.Activities.GameActivity;
import com.example.colorlinesclassic.Activities.ViewSettings;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import java.lang.reflect.Field;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class GameActivityTest {
    @Rule
    public ActivityTestRule<GameActivity> activityTestRule =
            new ActivityTestRule<>(GameActivity.class);

    @Test
    public void checkDefaultGameActivityData() throws NoSuchFieldException,IllegalAccessException{
        Settings settings = new Settings();
        GameActivity gameActivity = activityTestRule.getActivity();
        Field Button = GameActivity.class.
                getDeclaredField("firstPressedButtonId");
        Button.setAccessible(true);
        int firstPressedButtonId = (int) Button.get(gameActivity);
        Assert.assertEquals(0,firstPressedButtonId);

        Button = GameActivity.class.
                getDeclaredField("secondPressedButtonId");
        Button.setAccessible(true);
        int secondPressedButtonId = (int) Button.get(gameActivity);
        Assert.assertEquals(0,secondPressedButtonId);

        Field idField = GameActivity.class.
                getDeclaredField("idCounter");
        idField.setAccessible(true);
        int id = (int) idField.get(gameActivity);
        Assert.assertEquals(settings.getColumns()*settings.getRows() + 1,id);
    }
    @Test
    public void checkDefaultViewSettingsData() throws NoSuchFieldException,IllegalAccessException{
        ViewSettings settings = new ViewSettings();
        Field cellField = ViewSettings.class.
                getDeclaredField("cellSize");
        cellField.setAccessible(true);
        int cellSize = (int) cellField.get(settings);
        Assert.assertEquals(100,cellSize);

        Field ballRadius = ViewSettings.class.
                getDeclaredField("ballRadius");
        ballRadius.setAccessible(true);
        int radius = (int) ballRadius.get(settings);
        Assert.assertEquals(25,radius);
    }

    @Test
    public void checkStartViewData(){
        onView(ViewMatchers.withId(R.id.record_text))
                .check(matches(isDisplayed())).check(matches(withText("Record : 0")));
        onView(ViewMatchers.withId(R.id.score_text))
                .check(matches(isDisplayed())).check(matches(withText("Score : 0")));
    }

    @Test
    public void checkCreationOfField(){
        Settings s = new Settings();
        onView(ViewMatchers.withId(R.id.linesLayout))
                .check(matches(hasChildCount(s.getRows())));
        onView(ViewMatchers.withId(-s.getRows()))
                .check(matches(hasChildCount(s.getColumns())));
    }
}
