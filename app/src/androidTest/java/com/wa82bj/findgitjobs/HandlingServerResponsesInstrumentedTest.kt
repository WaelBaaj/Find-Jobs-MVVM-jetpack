package com.wa82bj.findgitjobs


import android.content.Intent
import android.os.SystemClock
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.MimeTypeFilter.matches
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.wa82bj.findgitjobs.CustomMatchers.Companion.withItemCount
import com.wa82bj.findgitjobs.ui.MainActivity

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HandlingServerResponsesInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java,
        true,false)

    private lateinit var app : App

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setupServer() {

        val instrumentation = InstrumentationRegistry.getInstrumentation()

        app = instrumentation.targetContext as App

        val appInjector = DaggerTestAppComponent.builder()
            .application(app)
            .build()
        appInjector.inject(app)

        mockWebServer = appInjector.getMockWebServer()

        val intent = Intent(InstrumentationRegistry.getInstrumentation()
            .targetContext, MainActivity::class.java)

        activityRule.launchActivity(intent)

    }

    @Test
    fun recycleView_shouldHandleMalformedResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("Error"))

        onView(withId(R.id.searchViewJobs)).perform(typeSearchViewText("test"))

        //TODO: don't use sleep
        SystemClock.sleep(1000)

        onView(

            allOf(
                withId(android.support.design.R.id.snackbar_text),
                withText(app.getString(R.string.network_connection_error))
            )
        )
            .check(matches(isDisplayed()))

    }


    @Test
    fun recycleView_shouldShowThreeElements() {

        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(
                """
                    [{
    "id": "1133",
    "created_at": "Tue Jul 19 18:40:17 UTC 2019",
    "title": "Ruby Developer",
    "location": "Frankfurt, DE",
    "type": "Full Time",
    "description": "<p>The Lead Developer (LD) will play a vital role in working on web application features, primarily in the Ruby programming language, as well as maintaining the production web applications.</p>",
    "how_to_apply": "<p>Email your resume to <a href=\"mailto:jobs@sample.com\">jobs@sample.com</a> with the subject \"Lead Ruby Developer [via github]\"</p>",
    "company": "Mock company",
    "company_url": null,
    "company_logo": null,
    "url": "http://jobs.github.com/positions/1133"
}, {
    "id": "1134",
    "created_at": "Tue Jul 18 11:33:32 UTC 2019",
    "title": "Android Engineer",
    "location": "Hamburg, Germany",
    "type": "Full Time",
    "description": "<h3>CVM</h3> is a fast growing mobile health startup with headquarters in Hamburg, DE. We provide pharmacies and other health and wellness organizations with patient engagement, medication management, and medication adherence mobile solutions.",
    "how_to_apply": "<p><a href=\"https://www.example.com\">https://www.example.com/</a></p>",
    "company": "CVM",
    "company_url": null,
    "company_logo": null,
    "url": "http://jobs.github.com/positions/1134"
},
]
                """.trimIndent()

            ))

        onView(withId(R.id.searchViewJobs)).perform(typeSearchViewText("test"))

        //TODO: don't use sleep
        SystemClock.sleep(1000)

        onView(withId(R.id.recyclerViewJobs)).check(matches(withItemCount(3)))
    }

}

fun typeSearchViewText(text: String): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            //Ensure that only apply if it is a SearchView and if it is visible.
            return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
        }

        override fun getDescription(): String {
            return "Change view text"
        }

        override fun perform(uiController: UiController, view: View) {
            (view as SearchView).setQuery(text, true)
        }
    }
}