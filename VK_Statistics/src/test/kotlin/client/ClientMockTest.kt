package client

import kotlinx.coroutines.runBlocking
import response.VkResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.Duration
import java.time.Instant

class ClientMockTest {

    @Mock
    private lateinit var httpClient: MyHttpClient

    private lateinit var client: VkHttpClient

    @BeforeEach
    fun init() {
        MockitoAnnotations.openMocks(this)
        client = VkHttpClientImpl("src/main/resources/test_config.properties",
                                  "src/main/resources/private_config.properties", httpClient)
    }

    private val simpleResponse = """
        {"response":{"total_count" : 366}}
    """.trimIndent()

    private val multiFieldResponse = """
        {"response":{"total_count" : 1, "field1" : 10, "field2" : 4}}
    """.trimIndent()

    private val defaultHashtag = "mock"
    private val defaultEndTime = Instant.now().epochSecond
    private val defaultStartTime = Instant.now().epochSecond - Duration.ofHours(1).seconds
    private val queryBuilder = QueryBuilder("src/main/resources/test_config.properties", "src/main/resources/private_config.properties")

    private fun createResponseWithCount(i: Int): String {
        return """
        {"response":{"total_count" : $i}}
    """.trimIndent()
    }

    @Test
    fun simpleResponseTest() = runBlocking {

        Mockito.`when`(httpClient.get(queryBuilder.buildQuery(defaultHashtag, defaultStartTime, defaultEndTime)))
            .thenReturn(simpleResponse)

        val response = client.getHashtagCount(defaultHashtag, defaultStartTime, defaultEndTime);

        assertEquals(response.getResponse(), VkResponse(simpleResponse).getResponse())
    }

    @Test
    fun multiFieldResponseTest() = runBlocking {

        Mockito.`when`(httpClient.get(queryBuilder.buildQuery(defaultHashtag, defaultStartTime, defaultEndTime)))
            .thenReturn(multiFieldResponse)

        val response = client.getHashtagCount(defaultHashtag, defaultStartTime, defaultEndTime)

        assertEquals(response.getResponse(), VkResponse(multiFieldResponse).getResponse())
    }

    @Test
    fun changeResponseTest() = runBlocking {
        for (i in 0..10) {
            Mockito.`when`(
                httpClient.get(
                    queryBuilder.buildQuery(
                        defaultHashtag,
                        defaultEndTime - Duration.ofHours(i.toLong()).seconds,
                        defaultEndTime
                    )
                )
            ).thenReturn(createResponseWithCount(i))
        }
        for (i in 0..10) {
            val response = client.getHashtagCount(defaultHashtag, defaultEndTime - Duration.ofHours(i.toLong()).seconds, defaultEndTime)

            assertEquals(response.getResponse(), VkResponse(createResponseWithCount(i)).getResponse())
        }
    }
}