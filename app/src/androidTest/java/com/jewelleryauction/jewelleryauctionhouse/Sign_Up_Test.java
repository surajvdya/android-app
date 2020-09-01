package com.jewelleryauction.jewelleryauctionhouse;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class Sign_Up_Test {
    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule = new ActivityTestRule<>( RegisterActivity.class );
    @Test
    public void testSignUp(){
        onView(withId(R.id.first_name)).perform(typeText( "rohit" ));
        closeSoftKeyboard();
        onView(withId(R.id.last_name)).perform(typeText( "shrestha" ));
        closeSoftKeyboard();
        onView(withId(R.id.signup_email)).perform(typeText( "rohit@gmail.com" ));
        closeSoftKeyboard();

        onView( withId( R.id.signpassword) ).perform(typeText("rohit"),closeSoftKeyboard());

        onView( withId( R.id.signrepassword) ).perform(typeText("rohit"), closeSoftKeyboard());
        onView(withId(R.id.rdogender)).perform(click());


        onView( withId( R.id.btnSignup ) ).perform( click() );

    }

}
