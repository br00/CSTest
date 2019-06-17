package com.alessandroborelli.cstest

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.TimeUnit


/**
 * Created by aborelli on 2019-06-17.
 */

internal class MockServerDispatcher {

    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {

            return when {
                request.path == "/prod/mockcredit/values" -> MockResponse().setResponseCode(200).setBody(
                    "{\"creditReportInfo\":{\"score\":326,\"maxScoreValue\":700,\"minScoreValue\":0}}"
                )
                else -> MockResponse().setResponseCode(404)
            }

        }
    }

    /**
     * Return error response from mock server
     */
    internal inner class ErrorDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {

            return when {
                request.path == "/prod/mockcredit/values" -> MockResponse().setResponseCode(400).setBody(
                    "{\"errorInfo\":{\"errorMessage\":\"Some error message\",\"errorCode\":400}}"
                )
                else -> MockResponse().setResponseCode(404)
            }

        }
    }
}