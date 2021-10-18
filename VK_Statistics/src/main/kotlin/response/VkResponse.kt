package response

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import java.io.StringReader

class VkResponse (rawResponse : String) {

    private val klaxon = Klaxon()

    private val response = rawResponse
    private val hashtagCount : Int

    init {
        val jsonParsed = klaxon.parseJsonObject(StringReader(rawResponse))
        val responseValue = jsonParsed["response"] as JsonObject
        hashtagCount = responseValue["total_count"] as Int
    }

    fun getHashtagCount() : Int {
       return hashtagCount
    }

    fun getResponse() : String {
        return response
    }
}