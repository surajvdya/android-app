package com.jewelleryauction.jewelleryauctionhouse;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class UpdateProfile_Test {
    @Rule
    public ActivityTestRule<AdminDashboardActivity> activityTestRule = new ActivityTestRule<>(AdminDashboardActivity.class);


    @Before
    public void Fragment(){
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();

    }

    @Test
    public void UpdateTest() {

        onView(withId(R.id.navigation_home))
                .perform(swipeLeft(),click());
        onView(withId(R.id.navigation_dashboard)).perform(click());

        onView(withId(R.id.btnupdateaccount)).perform(click());
        onView(withId(R.id.etuptfirst)).perform(typeText("sajik"),closeSoftKeyboard());
        onView(withId(R.id.etuptsecond)).perform(typeText("shrestha"),closeSoftKeyboard());
        onView(withId(R.id.btnUpdate)).perform(click());

    }

    @Test
    public void map(){
        onView(withId(R.id.navigation_notifications))
                .perform(swipeLeft());
        onView(withId(R.id.etCity)).perform(typeText("gokarna"),closeSoftKeyboard());
        onView(withId(R.id.btnSearch)).perform(click());
    }
}