import app.HashtagCounter
import client.SimpleHttpClient
import client.VkHttpClient
import client.VkHttpClientImpl
import response.VkResponse


suspend fun main() {
    val client : VkHttpClient = VkHttpClientImpl("src/main/resources/public_config.properties",
                                                 "src/main/resources/private_config.properties", SimpleHttpClient())
    val counter = HashtagCounter(client)
    val diagram = counter.countHashtags("навальный", 10)
    val response = VkResponse("""
        {"rofl" : "rofl"}
    """.trimIndent())

    for (i in diagram)
        println(i)
}