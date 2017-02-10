package com.example.marina.openweather.screens.main.adapter;

import android.support.test.espresso.matcher.ViewMatchers;

import com.example.marina.openweather.R;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class CityAdapterTest {
    @Test
    public void clickOnItemWithTextEqualToTwo() {
        onData(is(instanceOf(CityAdapter.class)))
                .inAdapterView(withId(R.id.recycleView))
                .perform(click()); // Standard ViewAction
    }

    @Test
    public void onBindViewHolder() throws Exception {

        onData(is(instanceOf(CityAdapter.ViewHolder.class)))
                .inAdapterView(withId(R.id.imgPin))
                .atPosition(0)
                //.onChildView(withId(R.id.imgPin))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        //onData(withId(R.id.imgPin)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

}