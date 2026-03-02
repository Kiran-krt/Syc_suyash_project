package com.syc.dashboard.query.notification.inapp.repository.reactive

import com.syc.dashboard.query.notification.entity.enums.InAppNotificationStatusEnum
import com.syc.dashboard.query.notification.inapp.entity.InAppNotification
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface InAppNotificationReactiveRepository : ReactiveMongoRepository<InAppNotification, String> {

    fun findByTenantIdAndUserIdAndStatusIn(
        tenantId: String,
        userId: String,
        status: List<InAppNotificationStatusEnum>,
        pageable: Pageable,
    ): Flux<InAppNotification>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " {'userId': {\$eq: :#{#userId} }} " +
            " ] } ",
        count = true,
    )
    fun inAppNotificationCountByTenantIdAndUserIdAndStatus(
        tenantId: String,
        userId: String,
        status: List<InAppNotificationStatusEnum>,
    ): Mono<Long>
}
