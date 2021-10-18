package client

import io.ktor.client.statement.*


interface MyHttpClient {
    suspend fun get(query : String) : String
}