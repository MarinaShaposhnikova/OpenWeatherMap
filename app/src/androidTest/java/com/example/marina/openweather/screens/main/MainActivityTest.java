package com.example.marina.openweather.screens.main;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.marina.openweather.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.marina.openweather.screens.main.WaitingAction.waitFor;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.is;

public class MainActivityTest {
    private List<String> citiesOld = new ArrayList<>();
    private List<String> citiesNew = new ArrayList<>();
    private int count;
    private int visibility;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    //1 test
    @Test
    public void firstItem() {
        failTest(View.VISIBLE == getVisibility(withRecyclerView(R.id.recycleView).atPosition(0)));
    }

    //2 test
    @Test
    public void twiceViewAppearance() {
        boolean isSame = false;
        List<String> cityAdd = new ArrayList<>();
        cityAdd.add("Dubai");
        cityAdd.add("Amsterdam");

        onView(withId(R.id.fab))
                .perform(click());
        onView(withClassName(is("android.widget.EditText")))
                .perform(replaceText(cityAdd.get(0)), closeSoftKeyboard());
        onView(withText("OK"))
                .perform(click());
        onView(withId(R.id.fab))
                .perform(click());
        onView(withClassName(is("android.widget.EditText")))
                .perform(replaceText(cityAdd.get(1)), closeSoftKeyboard());
        onView(withText("OK"))
                .perform(click());


        for (int i = 0; i < getCount(withId(R.id.recycleView)); i++) {
            for (int j = 0; j < cityAdd.size(); j++) {
                if (cityAdd.get(j).equals(getText(withRecyclerView(R.id.recycleView).atPosition(i)))) {
                    System.out.print(cityAdd.get(j));
                    isSame = true;
                }
            }
        }
        failTest(isSame);
    }


    //3 test
    @Test
    public void swipeUpdate() {
        boolean isSame = false;
        citiesOld.clear();
        citiesNew.clear();
        for (int i = 0; i < getCount(withId(R.id.recycleView)); i++) {
            citiesOld.add(getText(withRecyclerView(R.id.recycleView).atPosition(i)));
        }
        onView(withId(R.id.recycleView))
                .perform(swipeUp());
        onView(isRoot())
                .perform(waitFor(2000));
        for (int i = 0; i < getCount(withId(R.id.recycleView)); i++) {
            citiesNew.add(getText(withRecyclerView(R.id.recycleView).atPosition(i)));
        }
        for (int i = 0; i < citiesOld.size(); i++) {
            isSame = false;
            if (citiesNew.get(i).equals(citiesOld.get(i))) {
                isSame = true;
            }
        }
        failTest(isSame);
    }

    //4 test
    @Test
    public void swipe() {
        onView(isRoot()).perform(waitFor(2000));
        int positionSwipe = 1;
        String cityName = getText(withRecyclerView(R.id.recycleView).atPosition(positionSwipe));
        onView(withId(R.id.recycleView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(positionSwipe, swipeRight()));
        onView(withRecyclerView(R.id.recycleView).atPosition(positionSwipe))
                .check(matches(hasDescendant(withText(cityName))));
    }

    //5 test
    @Test
    public void addOldItem() {
        citiesOld.clear();
        citiesNew.clear();
        for (int i = 0; i < getCount(withId(R.id.recycleView)); i++) {
            citiesOld.add(getText(withRecyclerView(R.id.recycleView).atPosition(i)));
        }

        onView(withId(R.id.fab))
                .perform(click());
        onView(withClassName(is("android.widget.EditText")))
                .perform(replaceText("kiev"), closeSoftKeyboard());
        onView(withText("OK"))
                .perform(click());
        onView(isRoot())
                .perform(waitFor(5000));

        for (int i = 0; i < getCount(withId(R.id.recycleView)) - 1; i++) {
            for (int j = i + 1; j < getCount(withId(R.id.recycleView)) - 2; j++) {
                if (citiesOld.get(i).equals(citiesOld.get(j))) {
                    failTest(false);
                }
            }
        }

    }

    private void failTest(boolean success) {
        if (!success) {
            fail();
        }

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }


    int getCount(final Matcher<View> matcher) {
        onView(matcher)
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(RecyclerView.class);
                    }

                    @Override
                    public String getDescription() {
                        return "getting count of List";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        RecyclerView recyclerView = (RecyclerView) view;
                        RecyclerView.Adapter adapter = recyclerView.getAdapter();
                        count = adapter.getItemCount();
                    }
                });
        return count;
    }


    String getText(final Matcher<View> matcher) {
        int positionTextView = 1;
        final String[] stringHolder = {null};
        onView(matcher)
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(LinearLayout.class);
                    }

                    @Override
                    public String getDescription() {
                        return "getting city name";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        LinearLayout linLayout = (LinearLayout) view;
                        RelativeLayout relLayout = (RelativeLayout) linLayout.getChildAt(0);
                        TextView tv = (TextView) relLayout.getChildAt(positionTextView);
                        stringHolder[0] = tv.getText().toString();
                    }
                });
        return stringHolder[0];
    }

    int getVisibility(final Matcher<View> matcher) {
        onView(matcher)
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(LinearLayout.class);
                    }

                    @Override
                    public String getDescription() {
                        return "getting visibility of item";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        LinearLayout linLayout = (LinearLayout) view;
                        RelativeLayout relLayout = (RelativeLayout) linLayout.getChildAt(0);
                        ImageView img = (ImageView) relLayout.getChildAt(0);
                        visibility = img.getVisibility();
                    }
                });
        return visibility;
    }
}