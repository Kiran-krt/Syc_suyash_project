package com.syc.dashboard.framework.core.aspect.event

import com.syc.dashboard.framework.common.config.DocumentConfig
import com.syc.dashboard.framework.core.services.CloudDocumentService
import com.syc.dashboard.query.document.entity.enums.DocumentObjectTypeEnum
import com.syc.dashboard.query.projectreport.api.queries.FindProjectReportByIdQuery
import com.syc.dashboard.shared.projectreport.events.OutfallPhotoUploadEvent
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
class ProjectReportCloudDocumentEventAspect @Autowired constructor(
    private val documentConfig: DocumentConfig,
    private val cloudDocumentService: CloudDocumentService,
) : AbstractEventAspect() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @After("execution(* com.syc.dashboard.framework.core.infrastructure.EventHandler.on(..))")
    fun afterEvent(joinPoint: JoinPoint) {
        val event = getEvent(joinPoint = joinPoint)

        if (event != null) {
            val baseProjectReportPath = StringSubstitutor(mapOf(Pair("tenantId", event.tenantId)))
                .replace(documentConfig.cloudBaseUserUrl) + "${DocumentObjectTypeEnum.PROJECT_REPORT.folderName}/" + event.id + "/"

            if (event is OutfallPhotoUploadEvent) {
                createCloudProjectReportDocumentFolders(baseProjectReportPath = baseProjectReportPath, projectReportId = event.id)
            }
        }
    }

    @After("execution(* com.syc.dashboard.framework.core.queries.QueryHandler.handle(..))")
    fun afterQuery(joinPoint: JoinPoint) {
        val query = getQuery(joinPoint = joinPoint)

        if (query != null && query is FindProjectReportByIdQuery) {
            val baseProjectReportPath = StringSubstitutor(mapOf(Pair("tenantId", query.tenantId)))
                .replace(documentConfig.cloudBaseUserUrl) + "${DocumentObjectTypeEnum.PROJECT_REPORT.folderName}/" + query.id + "/"

            if (cloudDocumentService.isCloudDocumentFolderExist(baseProjectReportPath)) {
                log.info("Root document folder is existing for project report id: ${query.id}")
                documentConfig.projectReportEntities.forEach {
                    if (!cloudDocumentService.isCloudDocumentFolderExist(baseProjectReportPath + it)) {
                        cloudDocumentService.createCloudDocumentFolder(baseProjectReportPath + it)
                        log.info("Created missing document folder for project report id: ${query.id} and entity $it")
                    }
                }
            } else {
                createCloudProjectReportDocumentFolders(baseProjectReportPath = baseProjectReportPath, projectReportId = query.id)
            }
        }
    }

    private fun createCloudProjectReportDocumentFolders(baseProjectReportPath: String, projectReportId: String) {
        cloudDocumentService.createCloudDocumentFolder(baseProjectReportPath)
        documentConfig.projectReportEntities.forEach {
            cloudDocumentService.createCloudDocumentFolder(baseProjectReportPath + it)
        }
        log.info("Documents folders created for project report id: $projectReportId")
    }
}
