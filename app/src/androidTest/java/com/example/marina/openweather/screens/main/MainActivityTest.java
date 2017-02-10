package com.example.marina.openweather.screens.main;

import android.support.test.espresso.contrib.RecyclerViewActions;

import com.example.marina.openweather.R;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


public class MainActivityTest {
    @Test
    public void onCreate() throws Exception {
        onView(withId(R.id.recycleView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    }

}