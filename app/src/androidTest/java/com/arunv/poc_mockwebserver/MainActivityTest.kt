package com.arunv.poc_mockwebserver

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource.create
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val mockWebServer = MockWebServer()


    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setup() {
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(
            create(
                "okhttp",
                OkHttpProvider.getOkHttpClient()
            )
        )
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSuccessfulResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        "{" +
                                "\"data\": \"testworld04@reqres.in\"," + "  \"success\": true," + "  \"message\": \"User Registered\"\n" + "}"
                    )
            }
        }

        activityRule.launchActivity(null)
        onView(withId(R.id.etUserName)).perform(clearText())
        onView(withId(R.id.etUserName)).perform(
            ViewActions.typeText("autotest72@gmail.com"),
            click()
        )
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnClickMe)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.tvAppName)).check(matches(isDisplayed()))
    }

}
