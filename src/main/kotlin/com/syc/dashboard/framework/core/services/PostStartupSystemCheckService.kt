package com.syc.dashboard.framework.core.services

import com.syc.dashboard.command.systemconfig.api.commands.RegisterSystemConfigCommand
import com.syc.dashboard.command.tvhgConfig.api.commands.RegisterTvhgConfigCommand
import com.syc.dashboard.framework.common.config.DocumentConfig
import com.syc.dashboard.framework.core.infrastructure.CommandDispatcher
import com.syc.dashboard.query.document.entity.enums.DocumentObjectTypeEnum
import org.apache.commons.text.StringSubstitutor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class PostStartupSystemCheckService(
    @Value("\${syc.system.tenantIds:syc}")
    private val tenantIds: List<String>,
    private val documentConfig: DocumentConfig,
    private val cloudDocumentService: CloudDocumentService,
    private val commandDispatcher: CommandDispatcher,
) {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun handleContextRefresh(event: ContextRefreshedEvent?) {
        // verify and register system config
        verifyAndRegisterSystemConfig()

        // verify system document path check
        verifyDocumentPathStartupCheck()

        // verify and register tvhg config
        verifyAndRegisterTvhgConfig()
    }

    private fun verifyAndRegisterSystemConfig() {
        log.info("Verifying and register system config")

        for (tenantId in tenantIds) {
            commandDispatcher.send(RegisterSystemConfigCommand().buildSystemCommand(tenantId = tenantId))
        }
    }

    private fun verifyAndRegisterTvhgConfig() {
        log.info("Verifying and register tvhg config")

        for (tenantId in tenantIds) {
            commandDispatcher.send(RegisterTvhgConfigCommand().buildSystemCommand(tenantId = tenantId))
        }
    }

    private fun verifyDocumentPathStartupCheck() {
        log.info("Verifying document path startup check")

        for (tenantId in tenantIds) {
            val baseTenantPath = StringSubstitutor(mapOf(Pair("tenantId", tenantId)))
                .replace(documentConfig.cloudBaseUserUrl)

            try {
                if (!cloudDocumentService.isCloudDocumentFolderExist(baseTenantPath)) {
                    cloudDocumentService.createCloudDocumentFolder(baseTenantPath)
                }

                for (documentObject in DocumentObjectTypeEnum.entries) {
                    val documentPath = baseTenantPath + documentObject.folderName
                    if (!cloudDocumentService.isCloudDocumentFolderExist(documentPath)) {
                        cloudDocumentService.createCloudDocumentFolder(documentPath)
                    }

                    if (documentObject == DocumentObjectTypeEnum.SYSTEM) {
                        val documentObjectPath = documentPath + "/" + documentConfig.defaultSystemId
                        if (!cloudDocumentService.isCloudDocumentFolderExist(documentObjectPath)) {
                            cloudDocumentService.createCloudDocumentFolder(documentObjectPath)
                        }

                        for (systemEntity in documentConfig.systemEntities) {
                            val systemEntityDocumentObjectPath = "$documentObjectPath/$systemEntity"
                            if (!cloudDocumentService.isCloudDocumentFolderExist(systemEntityDocumentObjectPath)) {
                                cloudDocumentService.createCloudDocumentFolder(systemEntityDocumentObjectPath)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                log.warn("Failed to verify cloud documents folder for tenant '$tenantId'")
                log.warn(e.message)
            }
        }
    }
}
