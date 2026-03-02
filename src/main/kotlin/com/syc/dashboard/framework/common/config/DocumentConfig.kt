package com.syc.dashboard.framework.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.document")
data class DocumentConfig(
    var cloudUser: String = "",
    var cloudBaseUrl: String = "",
    var cloudBaseUserUrl: String = "",
    var cloudAuth: String = "",
    var userEntities: MutableList<String> = mutableListOf(),
    var defaultSystemId: String = "",
    var systemEntities: MutableList<String> = mutableListOf(),
    var tempFolderPath: String = "/tmp/gyri/documents/syc",
    val uploadFolder: String = "upload",
    val downloadFolder: String = "download",
    var projectReportEntities: MutableList<String> = mutableListOf(),
) {
    var uploadFolderPath = "$tempFolderPath/$uploadFolder"
    var downloadFolderPath = "$tempFolderPath/$downloadFolder"
}
