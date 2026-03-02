package com.syc.dashboard.query.notification.mobile.repository.reactive

import com.syc.dashboard.query.notification.entity.enums.MobileNotificationStatusEnum
import com.syc.dashboard.query.notification.mobile.entity.MobileNotification
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface MobileNotificationReactiveRepository : ReactiveMongoRepository<MobileNotification, String> {

    fun findByTenantIdAndUserIdAndStatusIn(
        tenantId: String,
        userId: String,
        status: List<MobileNotificationStatusEnum>,
        pageable: Pageable,
    ): Flux<MobileNotification>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'userId': {\$eq: :#{#userId} }} " +
            " {'status': {\$in: :#{#status} }}, " +
            " ] } ",
        count = true,
    )
    fun mobileNotificationCountByTenantIdAndUserIdAndStatus(
        tenantId: String,
        userId: String,
        status: List<MobileNotificationStatusEnum>,
    ): Mono<Long>
}
