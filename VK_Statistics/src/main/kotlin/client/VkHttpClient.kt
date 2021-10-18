package client

import response.VkResponse

interface VkHttpClient {
    suspend fun getHashtagCount(hashtag : String, startTime : Long, endTime : Long) : VkResponse
}