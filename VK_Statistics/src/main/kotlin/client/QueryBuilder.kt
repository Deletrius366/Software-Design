package client

import java.io.FileInputStream
import java.util.*

class QueryBuilder (publicConfigFilename : String, privateConfigFilename : String) {

    private val publicPropertyFile = Properties()
    private val privatePropertyFile = Properties()

    init {
        publicPropertyFile.load(FileInputStream(publicConfigFilename))
        privatePropertyFile.load(FileInputStream(privateConfigFilename))
    }

    private fun getProperty(name: String): String {
        return publicPropertyFile.getProperty(name)
    }

    private fun getPrivateProperty(name : String) : String {
        return privatePropertyFile.getProperty(name)
    }

    fun buildQuery(hashtag: String, startTime: Long, endTime: Long): String {
        return getProperty("vk_prefix") +
                getProperty("vk_host") + ":" +
                getProperty("vk_port") + "/method/" +
                getProperty("vk_method") +
                "?q=%23$hashtag" +
                "&v=" + getProperty("vk_version") +
                "&access_token=" + getPrivateProperty("vk_access_token") +
                "&count=0" +
                "&start_time=$startTime" + "&end_time=$endTime"
    }
}