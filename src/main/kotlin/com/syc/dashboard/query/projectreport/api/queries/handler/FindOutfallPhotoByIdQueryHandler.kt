package com.syc.dashboard.query.projectreport.api.queries.handler

import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.queries.BaseQuery
import com.syc.dashboard.framework.core.queries.QueryHandler
import com.syc.dashboard.query.document.dto.DocumentIdDto
import com.syc.dashboard.query.projectreport.api.queries.FindOutfallPhotoByIdQuery
import com.syc.dashboard.query.projectreport.dto.OutfallPhotoDto
import com.syc.dashboard.query.projectreport.repository.reactive.ProjectReportReactiveRepository
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class FindOutfallPhotoByIdQueryHandler(
    private val projectReportReactiveRepository: ProjectReportReactiveRepository,
) : QueryHandler {

    override fun <T : BaseQuery> handle(query: T): Flux<out BaseDto> {
        query as FindOutfallPhotoByIdQuery

        return projectReportReactiveRepository
            .findByTenantIdAndIdAndOutfallPhotoList(
                tenantId = query.tenantId,
                id = query.id,
            )
            .flatMap { projectReport ->
                projectReport.outfallPhotoList.toFlux().map { outfallPhoto ->
                    OutfallPhotoDto(
                        id = outfallPhoto.id,
                        tenantId = projectReport.tenantId,
                        projectReportId = outfallPhoto.projectReportId,
                        document = outfallPhoto.document.map {
                            DocumentIdDto(
                                it.documentId,
                            )
                        },
                        status = outfallPhoto.status,
                        caption = outfallPhoto.caption,
                        order = outfallPhoto.order,
                        uploadedBy = outfallPhoto.uploadedBy,
                        uploadedByInfo = projectReport.uploadedByInfo,
                    )
                }
            }
    }
}
