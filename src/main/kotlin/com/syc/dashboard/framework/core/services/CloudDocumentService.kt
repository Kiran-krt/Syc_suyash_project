package com.syc.dashboard.framework.core.services

import com.syc.dashboard.framework.common.config.DocumentConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CloudDocumentService @Autowired constructor(
    private val documentConfig: DocumentConfig,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun isCloudDocumentFolderExist(url: String): Boolean {
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Basic ${documentConfig.cloudAuth}")
            .method("PROPFIND", null)
            .build()

        val response = OkHttpClient().newCall(request).execute()
        val isSuccess = response.code != 404
        // check for response.isSuccessful here, or read the body if required
        response.body?.close()
        return isSuccess
    }

    fun createCloudDocumentFolder(url: String) {
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Basic ${documentConfig.cloudAuth}")
            .method("MKCOL", null)
            .build()

        val response = OkHttpClient().newCall(request).execute()
        if (response.code != 201 && response.code != 200 && response.code != 204) {
            log.warn("Folder not created to document server: $url")
            log.warn("Response: ${response.message} - ${response.code}")
        }
    }
}
