package com.alessandroborelli.cstest

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import com.alessandroborelli.cstest.base.Constants
import com.alessandroborelli.cstest.presentation.creditscore.CreditScoreActivity
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by aborelli on 2019-06-16.
 */
@RunWith(AndroidJUnit4::class)
class CreditScoreActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule: ActivityTestRule<CreditScoreActivity> = ActivityTestRule<CreditScoreActivity>(CreditScoreActivity::class.java,
        false,
        false)

    private lateinit var webServer: MockWebServer

    @Before
    fun setUp() {
        Constants.BASE_URL = "http://localhost:8080/"
        webServer = MockWebServer()
        webServer.start(8080)
    }

    @Test
    fun retrieveCreditScoreWithSuccess() {
        webServer.setDispatcher(MockServerDispatcher().RequestDispatcher())
        mActivityTestRule.launchActivity(Intent())

        // Check that the views are displayed correctly after retrieving the data and update the viewmodel
        Espresso.onView(withId(R.id.loading_progress_bar)).check(matches(not(isDisplayed())))
        Espresso.onView(withId(R.id.error_message)).check(matches(not(isDisplayed())))
        Espresso.onView(withId(R.id.score_progress_bar)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.main_container)).check(matches(isDisplayed()))

        // Check the UI is updated with the right data
        Espresso.onView(withId(R.id.score)).check(matches(withText("326")))
        Espresso.onView(withId(R.id.max_score)).check(matches(withText("700")))
    }

    @Test
    fun retrieveCreditScoreWithFailure() {
        webServer.setDispatcher(MockServerDispatcher().ErrorDispatcher())
        mActivityTestRule.launchActivity(Intent())

        // Check that the views are displayed correctly after retrieving the data and receive a failure
        Espresso.onView(withId(R.id.loading_progress_bar)).check(matches(not(isDisplayed())))
        Espresso.onView(withId(R.id.error_message)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.main_container)).check(matches(not(isDisplayed())))
    }

    @After
    fun tearDown() {
        webServer.shutdown()
        // Give some time to the server to shutdown and restart for the next test
        Thread.sleep(2000)
    }
}