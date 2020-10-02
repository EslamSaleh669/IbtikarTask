package com.eslam.ebtikartask.ui;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.eslam.ebtikartask.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recyclerTest (){

      // Espresso.onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

      RecyclerView recyclerView = mainActivityActivityTestRule.getActivity().findViewById(R.id.recyclerView);
      int itemcount = recyclerView.getAdapter().getItemCount();
      Espresso.onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(itemcount-1));

//
//        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
//        String itemval = "Keanu Reeves" ;
//        Espresso.onView(withText(itemval)).check(matches(isDisplayed()));

    }
}