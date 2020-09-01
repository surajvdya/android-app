package com.jewelleryauction.jewelleryauctionhouse;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
@RunWith(JUnit4.class)
@LargeTest
public class Bid_Add_Test {

    @Rule
    public ActivityTestRule<ProductDetailActivity> activityTestRule = new ActivityTestRule<>(ProductDetailActivity.class);

    @Test
    public void Bid(){
        onView(withId(R.id.amount)).perform(typeText( "12000" ),closeSoftKeyboard());


        onView( withId( R.id.bit_button ) ).perform( click() );


    }
}
