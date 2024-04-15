package com.pyproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ActivityScenario.ActivityAction;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.hamcrest.Description;

import android.view.Window;
import android.view.WindowManager;



import android.os.IBinder;


import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import androidx.test.espresso.Root;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    // Mock the backend responses
    @Before
    public void setUp() {
    }

    @Test
    public void ensureLoginButtonWorks() {
        // Type text and press the button
        onView(withId(R.id.editTextTextEmailAddress))
                .perform(typeText("user@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)) // Replace with your actual login button id
                .perform(click());

        // Check that ProductsActivity was opened
        Intents.init();
        intended(hasComponent(ProductsActivity.class.getName()));
        Intents.release();
    }



}