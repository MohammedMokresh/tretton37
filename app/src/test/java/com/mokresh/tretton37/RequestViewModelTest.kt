package com.mokresh.tretton37

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mokresh.tretton37.api.ApiServices
import com.mokresh.tretton37.view.QuestionsViewModel
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class RequestViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: QuestionsViewModel
    private lateinit var repository: QuestionsRepository

    private lateinit var apiServices: ApiServices


    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiServices = ApiServices.Companion.create()
        repository = QuestionsRepository(apiServices)
        viewModel = QuestionsViewModel(repository)

    }


    @Test
    suspend fun checkIfCreateCheckoutSuccess() {
        // Assign
        val getQuestionsResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("question_response.json").content)

        val mockResponse = getQuestionsResponse.getBody()?.readUtf8()

        // Act
        val actualResponse = apiServices.getQuestions(amount = 11, "multiple").body()
        // Assert
        assertEquals(
            mockResponse?.let { parseTokenFromResponse(it) }, actualResponse?.responseCode
        )


        mockWebServer.enqueue(getQuestionsResponse)

    }

    private fun parseTokenFromResponse(mockResponse: String): Int {
        val reader = JSONObject(mockResponse)
        return reader.getInt("response_code")
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}