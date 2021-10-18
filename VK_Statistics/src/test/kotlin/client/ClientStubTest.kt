package client

import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition
import com.xebialabs.restito.server.StubServer
import kotlinx.coroutines.runBlocking
import org.glassfish.grizzly.http.Method
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant


class ClientStubTest {

    private val port = 443

    private val client = VkHttpClientImpl("src/main/resources/test_config.properties",
                                          "src/main/resources/private_config.properties", SimpleHttpClient())

    private val simpleResponse = """
        {"response":{"total_count" : 366}}
    """.trimIndent()

    private val defaultHashtag = "stub"
    private val defaultEndTime = Instant.now().epochSecond
    private val defaultStartTime = Instant.now().epochSecond - Duration.ofHours(1).seconds

    private suspend fun runWithStubServer(port: Int, callback: suspend (StubServer) -> Unit) {
        var stubServer: StubServer? = null
        try {
            stubServer = StubServer(port).run()
            callback(stubServer)
        } finally {
            stubServer?.stop()
        }
    }

    @Test
    internal fun testWithStub() = runBlocking {
        runWithStubServer(port) {
            whenHttp(it).match(Condition.method(Method.GET), Condition.startsWithUri("/method/putIfAbsent"))
                .then(stringContent(simpleResponse))
            val response = client.getHashtagCount(defaultHashtag, defaultStartTime, defaultEndTime)
            println(response.getResponse())

            assertEquals(response.getHashtagCount(), 366)
        }
    }
}