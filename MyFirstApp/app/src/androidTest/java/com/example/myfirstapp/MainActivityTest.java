package com.example.myfirstapp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.CloseKeyboardAction;
import androidx.test.espresso.action.ScrollToAction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    final private static String FIRST_NAME = "John";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

//    @Test
//    public void firstNameTest() {
//        onView(withId(R.id.firstName)).perform(typeText(FIRST_NAME));
//        onView(withId(R.id.firstName)).check(matches(withText("John")));
//    }

    @Test
    public void testIntent() {
        onView(withId(R.id.firstName)).perform(typeText(FIRST_NAME));
        onView(withId(R.id.firstName)).check(matches(withText("John")));
        onView(withId(R.id.eventNext)).perform(scrollTo(), click());
//        intended(allOf(
//                hasComponent(hasShortClassName(".ConfirmDetailsActivity")),
//                toPackage("com.example.myfirstapp"),
//                hasExtra(MainActivity.PUT_EXTRA, "I am running an intent")));
        intended(allOf(
                toPackage("com.example.myfirstapp")));
    }
}
