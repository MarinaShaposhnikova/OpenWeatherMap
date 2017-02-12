package com.example.marina.openweather.screens.main.adapter;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.marina.openweather.R;
import com.example.marina.openweather.screens.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
@RunWith(AndroidJUnit4.class)
public class CityAdapterTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void itemContainsGpsImage() {
        onView(withId(R.id.recycleView))
                .perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.imgPin))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}