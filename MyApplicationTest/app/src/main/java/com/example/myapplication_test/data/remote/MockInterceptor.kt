package com.example.myapplication_test.data.remote

import com.example.myapplication_test.constants.AppConstants.Network
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

// Pretends to be a real server — returns hardcoded JSON so the app works without a backend
// When the real API is ready, just remove this from AppModule and point to the real URL
class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()

        val responseBody = when {
            uri.endsWith(Network.ITEMS_ENDPOINT) -> MOCK_ITEMS_JSON
            else -> Network.NOT_FOUND_JSON
        }

        return Response.Builder()
            .code(200)
            .message(Network.RESPONSE_OK)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(responseBody.toResponseBody(Network.CONTENT_TYPE_JSON.toMediaType()))
            .build()
    }

    companion object {
        private val MOCK_ITEMS_JSON = """
        {
          "items": [
            { "id": 1, "title": "Kotlin Coroutines", "description": "Makes async code feel like regular code — no more callback hell" },
            { "id": 2, "title": "Jetpack Compose", "description": "Build your UI by describing what it should look like, not how to draw it" },
            { "id": 3, "title": "Koin DI", "description": "Wire up your dependencies in a few lines — no annotations, no magic" },
            { "id": 4, "title": "Retrofit", "description": "Turn your API into a Kotlin interface and let Retrofit handle the rest" },
            { "id": 5, "title": "Clean Architecture", "description": "Keep your code organized so changing one thing doesn't break everything else" },
            { "id": 6, "title": "Flow & StateFlow", "description": "Watch your data change over time and react to it automatically" },
            { "id": 7, "title": "MockK", "description": "Fake any dependency in your tests so you can test one thing at a time" },
            { "id": 8, "title": "MVVM Pattern", "description": "Split your screen logic from your UI so both are easy to test and change" }
          ]
        }
        """.trimIndent()
    }
}
