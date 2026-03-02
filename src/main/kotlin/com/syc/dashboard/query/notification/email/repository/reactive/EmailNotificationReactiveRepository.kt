package com.syc.dashboard.query.notification.email.repository.reactive

import com.syc.dashboard.query.notification.email.entity.EmailNotification
import com.syc.dashboard.query.notification.entity.enums.EmailNotificationStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface EmailNotificationReactiveRepository : ReactiveMongoRepository<EmailNotification, String> {

    fun findByTenantIdAndUserIdAndStatusIn(
        tenantId: String,
        userId: String,
        status: List<EmailNotificationStatusEnum>,
        pageable: Pageable,
    ): Flux<EmailNotification>

    @Query(
        value = " {\$and: [ " +
            " {'tenantId': {\$eq: :#{#tenantId} }}, " +
            " {'status': {\$in: :#{#status} }}, " +
            " {'userId': {\$eq: :#{#userId} }} " +
            " ] } ",
        count = true,
    )
    fun emailNotificationCountByTenantIdAndUserIdAndStatus(
        tenantId: String,
        userId: String,
        status: List<EmailNotificationStatusEnum>,
    ): Mono<Long>
}
