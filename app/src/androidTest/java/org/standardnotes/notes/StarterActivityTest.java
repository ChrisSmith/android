package org.standardnotes.notes;


import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.standardnotes.notes.comms.data.Tag;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StarterActivityTest {

    @Test
    public void createNote() {

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        withParent(allOf(withId(R.id.rootView),
                                withParent(withId(R.id.drawer_layout)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.titleEdit));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.titleEdit));
        appCompatEditText2.perform(scrollTo(), replaceText("Title1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.bodyEdit)));
        appCompatEditText14.perform(scrollTo(), replaceText("body1"), closeSoftKeyboard());

        ViewInteraction upButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        upButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list),
                        withParent(allOf(withId(R.id.noteListFrag),
                                withParent(withId(R.id.rootView)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.tags), withContentDescription("Tags"), isDisplayed()));
        actionMenuItemView.perform(click());

        pressBack();

        appCompatEditText14.perform(scrollTo(), replaceText("body1a"), closeSoftKeyboard());

        upButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.text), withText("body1a"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("body1a")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.title), withText("Title1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Title1")));

        textView2.check(matches(withText("Title1")));
        textView.check(matches(withText("body1a")));
    }

    @Test
    public void justSignInAndOut() {
    }

    @Test
    public void tagSomething() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        withParent(allOf(withId(R.id.rootView),
                                withParent(withId(R.id.drawer_layout)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.titleEdit));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.titleEdit));
        appCompatEditText2.perform(scrollTo(), replaceText("Title2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.bodyEdit)));
        appCompatEditText14.perform(scrollTo(), replaceText("body2"), closeSoftKeyboard());

        ViewInteraction tagsAction = onView(
                allOf(withId(R.id.tags), withContentDescription("Tags"), isDisplayed()));
        tagsAction.perform(click());
        onView(withId(R.id.fab)).perform(click());
        onView(
                allOf(withId(R.id.tag), isDisplayed())).perform(replaceText("tag1"), closeSoftKeyboard());
        onView(
                allOf(withId(android.R.id.button1), withText("OK"))).perform(scrollTo(), click());
        onView(withId(R.id.fab)).perform(click());
        onView(
                allOf(withId(R.id.tag), isDisplayed())).perform(replaceText("tag2"), closeSoftKeyboard());
        onView(
                allOf(withId(android.R.id.button1), withText("OK"))).perform(scrollTo(), click());
        onView(withId(R.id.fab)).perform(click());
        onView(
                allOf(withId(R.id.tag), isDisplayed())).perform(replaceText("tag3"), closeSoftKeyboard());
        onView(
                allOf(withId(android.R.id.button1), withText("OK"))).perform(scrollTo(), click());
        onView(
                withText("tag1")).perform(click());
        onView(
                withText("tag3")).perform(click());
        onView(
                withText("tag3")).perform(longClick());
        onView(
                allOf(isDisplayed(), withText("Delete"))).perform(click()); // context menu
        onView(
                allOf(isDisplayed(), withText("Delete"))).perform(click()); // popup
        onView(withText("tag3")).check(doesNotExist());

        pressBack();

        ViewInteraction upButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        upButton.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list),
                        withParent(allOf(withId(R.id.noteListFrag),
                                withParent(withId(R.id.rootView)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        onView(withText("tag1")).check(matches(isDisplayed()));

        tagsAction.perform(click());
        onView(
                withText("tag1")).perform(click()); // deselect
        onView(
                withText("tag2")).perform(click()); // select
        onView(withText("tag3")).check(doesNotExist());
        pressBack();
        onView(withText("tag2")).check(matches(isDisplayed()));
        onView(withText("tag1")).check(doesNotExist());
        pressBack();

        recyclerView.perform(actionOnItemAtPosition(0, click()));
        onView(withText("tag2")).check(matches(isDisplayed()));
        onView(withText("tag1")).check(doesNotExist());


        pressBack();
    }


    @Test
    public void openCloseOpenClose() {

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        withParent(allOf(withId(R.id.rootView),
                                withParent(withId(R.id.drawer_layout)))),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.titleEdit));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.titleEdit));
        appCompatEditText2.perform(scrollTo(), replaceText("Title2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.bodyEdit)));
        appCompatEditText14.perform(scrollTo(), replaceText("body2"), closeSoftKeyboard());
        pressBack();

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list),
                        withParent(allOf(withId(R.id.noteListFrag),
                                withParent(withId(R.id.rootView)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        pressBack();
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
