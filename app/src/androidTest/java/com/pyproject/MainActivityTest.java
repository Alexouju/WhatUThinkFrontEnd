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

        onView(withId(R.id.editTextTextEmailAddress))
                .perform(typeText("user@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(typeText("password123"), closeSoftKeyboard());
        onView(withId(R.id.loginButton))
                .perform(click());

        Intents.init();
        intended(hasComponent(ProductsActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void ensureErrorIsDisplayedOnFailedLogin() {

        onView(withId(R.id.editTextTextEmailAddress))
                .perform(typeText("wronguser@example.com"), closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(typeText("wrongpassword"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)) // Replace with your actual login button id
                .perform(click());


        onView(withText("Unknown response from server"))
                .inRoot((Matcher<Root>) new ToastMatcher())
                .check(matches(isDisplayed()));
    }

}

class ToastMatcher extends TypeSafeMatcher<Root> {
    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }

    @Override
    public boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams().get().type;
        if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            if (windowToken == appToken) {

                return true;
            }
        }
        return false;
    }




}