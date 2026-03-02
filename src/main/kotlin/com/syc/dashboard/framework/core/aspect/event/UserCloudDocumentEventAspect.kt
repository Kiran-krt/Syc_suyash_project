package com.syc.dashboard.framework.core.aspect.event

import com.syc.dashboard.framework.common.config.DocumentConfig
import com.syc.dashboard.framework.core.events.TenantBaseEvent
import com.syc.dashboard.shared.admin.events.AdminLoggedInEvent
import com.syc.dashboard.shared.admin.events.AdminRegisteredEvent
import com.syc.dashboard.shared.employee.events.EmployeeLoggedInEvent
import com.syc.dashboard.shared.employee.events.EmployeeRegisteredEvent
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.text.StringSubstitutor
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Aspect
@Configuration
class UserCloudDocumentEventAspect @Autowired constructor(
    private val documentConfig: DocumentConfig,
) : AbstractEventAspect() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)
    private val USERS_FOLDER = "users"

    @After("execution(* com.syc.dashboard.framework.core.infrastructure.EventHandler.on(..))")
    fun after(joinPoint: JoinPoint) {
        val event = getEvent(joinPoint = joinPoint)

        if (event != null) {
            val baseUserPath = StringSubstitutor(mapOf(Pair("tenantId", event.tenantId)))
                .replace(documentConfig.cloudBaseUserUrl) + "$USERS_FOLDER/" + event.id + "/"

            if (event is EmployeeLoggedInEvent || event is AdminLoggedInEvent) {
                if (verifyCloudDocumentFolderExist(baseUserPath)) {
                    log.info("Root documents folders are existing for user id: ${event.id}")
                    documentConfig.userEntities.forEach {
                        if (!verifyCloudDocumentFolderExist(baseUserPath + it)) {
                            createCloudDocumentFolder(baseUserPath + it)
                            log.info("Created missing document folder for user id: ${event.id} and entity $it")
                        }
                    }
                } else {
                    createCloudUserDocumentFolders(baseUserPath, event)
                }
            }

            if (event is EmployeeRegisteredEvent || event is AdminRegisteredEvent) {
                createCloudUserDocumentFolders(baseUserPath, event)
            }
        }
    }

    private fun createCloudUserDocumentFolders(baseUserPath: String, event: TenantBaseEvent) {
        createCloudDocumentFolder(baseUserPath)
        documentConfig.userEntities.forEach {
            createCloudDocumentFolder(baseUserPath + it)
        }
        log.info("Documents folders created for user id: ${event.id}")
    }

    private fun createCloudDocumentFolder(url: String) {
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

    private fun verifyCloudDocumentFolderExist(url: String): Boolean {
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Basic ${documentConfig.cloudAuth}")
            .method("PROPFIND", null)
            .build()

        val response = OkHttpClient().newCall(request).execute()
        return response.code != 404
    }
}
