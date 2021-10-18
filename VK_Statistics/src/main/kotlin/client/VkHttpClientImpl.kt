package client

import response.VkResponse

class VkHttpClientImpl (publicConfigFilename : String, privateConfigFilename : String, httpClient : MyHttpClient) : VkHttpClient {

    private val client = httpClient
    private val queryBuilder = QueryBuilder(publicConfigFilename, privateConfigFilename)

    override suspend fun getHashtagCount(hashtag : String, startTime : Long, endTime : Long): VkResponse {
        return VkResponse(client.get(queryBuilder.buildQuery(hashtag, startTime, endTime)))
    }
}