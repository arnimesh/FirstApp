package com.example.github_assignment


import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.github_assignment.adapter.ContributorViewAdapter
import com.example.github_assignment.adapter.RecyclerViewAdapter
import com.example.github_assignment.utils.EspressoIdlingResource
import com.example.github_assignment.views.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
        // registering the idling resource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregister() {
        // unregistering the idling resource
         IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun `testCompleteFlowforValidUser`() {
        val userName = "vipinhelloindia"

        onView(withId(R.id.username)).perform(typeText(userName))

        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnSubmit)).perform(click())

        // check if recycler view is displayed
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))

        // clicking the recycler view item
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<RecyclerViewAdapter.ViewHolder>(0, click()))

        // checking if the contributor list is displayed
        onView(withId(R.id.contributor_view)).check(matches(isDisplayed()))
       //  clicking on the contributor list
        onView(withId(R.id.contributor_view))
            .perform(actionOnItemAtPosition<ContributorViewAdapter.ViewHolder>(0, click()))

//        // checking if again profile fragment is displayed
        onView(withId(R.id.repositories)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<RecyclerViewAdapter.ViewHolder>(0, click()))

        // checking if the contributor list is displayed
        onView(withId(R.id.contributor_view)).check(matches(isDisplayed()))
    //  checking back button
        pressBack()
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        //checking back flow again
//        pressBack()
//        onView(withId(R.id.btnSubmit)).check(matches(isDisplayed()))

    }

    @Test
    fun testCompleteFlowForInvalidUserName() {
        val userName = "invalid12345"
        onView(withId(R.id.username)).perform(typeText(userName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnSubmit)).perform(click())

        // checks if User Not Found is Displayed
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    @Test
    fun testInput() {
        val userName = "vipinhelloindia"
        onView(withId(R.id.username)).perform(typeText(userName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnSubmit)).perform(click())
        onView(withText("@$userName")).check(matches(isDisplayed()))
    }

    @Test
    fun testRepositories() {
        val userName = "vipinhelloindia"
        onView(withId(R.id.username)).perform(typeText(userName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnSubmit)).perform(click())
        onView(withId(R.id.followers)).check(matches(isDisplayed()))
    }
}
