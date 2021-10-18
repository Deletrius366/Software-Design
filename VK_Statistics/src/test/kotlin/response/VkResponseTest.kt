package response

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.lang.NullPointerException

class VkResponseTest {

    private val simpleResponse = """
        {"response":{"total_count" : 366}}
    """.trimIndent()

    private val failedResponse = """
        {"response":{"not_total_count" : 366}}
    """.trimIndent()

    @Test
    internal fun simpleResponseTest() {
        assertDoesNotThrow {  VkResponse(simpleResponse)}
    }

    @Test
    internal fun failedResponseTest() {
        assertThrows<NullPointerException> {  VkResponse(failedResponse)}
    }


}