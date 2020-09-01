package com.jewelleryauction.jewelleryauctionhouse;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class Search_Testing {
    @Rule
    public ActivityTestRule<SearchActivity> activityTestRule = new ActivityTestRule<>(SearchActivity.class );

    @Before
    public void fragment(){

    }
    @Test
    public void loginTest(){

        onView(withId(R.id.etCity)).perform(typeText( "Ashirvad Boarding School" ));
        closeSoftKeyboard();

        onView( withId( R.id.btnSearch ) ).perform( click() );

    }
}
