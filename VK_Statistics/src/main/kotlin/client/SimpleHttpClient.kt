package client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class SimpleHttpClient : MyHttpClient {

    private val client = HttpClient(CIO)

    override suspend fun get(query: String): String {
        val response : HttpResponse = client.get(query)
        return response.receive()
    }
}