package com.syc.dashboard.framework.common.document.client

import com.github.sardine.DavResource
import com.github.sardine.impl.SardineImpl
import com.syc.dashboard.framework.common.config.DocumentConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicHeader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.InputStream
import javax.xml.namespace.QName

@Component
class WebDavClient @Autowired constructor(
    documentConfig: DocumentConfig,
) {
    private val client = HttpClientBuilder
        .create()
        .setDefaultHeaders(
            listOf(
                BasicHeader("Authorization", "Basic ${documentConfig.cloudAuth}"),
            ),
        )
    private val sardine = SardineImpl(client)

    fun getCloudFile(fileUrl: String): InputStream {
        return sardine.get(fileUrl)
    }

    fun getCloudFileMetadata(fileUrl: String): CloudFileMetadata {
        val resources: List<DavResource> = sardine.propfind(
            fileUrl,
            0,
            setOf(
                QName(OWNCLOUD_NAMESPACE_URI, FILE_ID_KEY, OWNCLOUD_NAMESPACE_PREFIX),
                QName(NEXTCLOUD_NAMESPACE_URI, HAS_PREVIEW_KEY, NEXTCLOUD_NAMESPACE_PREFIX),
            ),
        )
        return if (resources.isEmpty()) {
            CloudFileMetadata()
        } else {
            val resource = resources.first()
            CloudFileMetadata(
                fileId = resource.customProps[FILE_ID_KEY] ?: "-1",
                hasPreview = resource.customProps[HAS_PREVIEW_KEY] == "true",
            )
        }
    }

    companion object {
        private const val OWNCLOUD_NAMESPACE_URI = "http://owncloud.org/ns"
        private const val OWNCLOUD_NAMESPACE_PREFIX = "oc"
        private const val NEXTCLOUD_NAMESPACE_URI = "http://nextcloud.org/ns"
        private const val NEXTCLOUD_NAMESPACE_PREFIX = "nc"

        private const val FILE_ID_KEY = "fileid"
        private const val HAS_PREVIEW_KEY = "has-preview"
    }
}
