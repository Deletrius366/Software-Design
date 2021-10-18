package app

import client.VkHttpClient
import java.time.Duration
import java.time.Instant

class HashtagCounter (private val client : VkHttpClient) {
    suspend fun countHashtags(hashtag : String, hours : Long) : List<Int> {
        val timeNowSeconds = Instant.now().epochSecond
        val diagram = mutableListOf<Int>()
        for (h in 0 until hours) {
            val startTime = timeNowSeconds - Duration.ofHours(h + 1).seconds
            val endTime = timeNowSeconds - Duration.ofHours(h).seconds
            diagram.add(client.getHashtagCount(hashtag, startTime, endTime).getHashtagCount())
        }
        return diagram
    }
}