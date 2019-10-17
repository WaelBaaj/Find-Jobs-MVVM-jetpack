package com.wa82bj.findgitjobs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wa82bj.findgitjobs.data.api.FindGithubJobsApi
import com.wa82bj.findgitjobs.data.model.JobsModel
import com.wa82bj.findgitjobs.di.NetworkModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class FindJobServiceUnitTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var findGithubJobsApi: FindGithubJobsApi

    private val mockWebServer = MockWebServer()

    @Before
    fun doSetup() {
        val networkModule = NetworkModule()
        mockWebServer.start()

        val serverUrl = mockWebServer.url("/").toString()
        val moshi = networkModule.provideMoshi()
        val httpClient = networkModule.provideHttpClient()
        val retrofit = networkModule.provideRetrofit(serverUrl, moshi, httpClient)

        findGithubJobsApi = networkModule.provideGitHubJobsApiService(retrofit)
    }

    @After
    fun doCleanup() {
        mockWebServer.shutdown()
    }

    @Test
    fun serverErrorTest() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("Server Error"))
        findGithubJobsApi.searchPositions("test")
            .test()
            .assertNoValues()
    }


    @Test
    fun searchPositionsTest() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(
            """
                    [{
    "id": "ID",
    "created_at": "today",
    "title": "Title",
    "location": "Location",
    "type": "Full Time",
    "description": "...",
    "how_to_apply": "...",
    "company": "Health",
    "company_url": null,
    "company_logo": null,
    "url": "http://localhost/"
},
    {
    "id": "ID_2",
    "created_at": "today",
    "title": "Title",
    "location": "Location",
    "type": "Full Time",
    "description": "...",
    "how_to_apply": "...",
    "company": "Ebaaj",
    "company_url": null,
    "company_logo": null,
    "url": "http://localhost/"
}]
                """.trimIndent()

        ))

        findGithubJobsApi.searchPositions("test")
            .test()
            .assertResult(
                listOf(
                    JobsModel("ID","today","Title","Location","Full Time","...",
                        "...","Health",null,null,"http://localhost/"),
                    JobsModel("ID_2","today","Title","Location","Full Time","...",
                        "...","Ebaaj",null,null,"http://localhost/")
                )

            )

    }
}