package com.syc.dashboard.framework.common.notification.email

import com.syc.dashboard.framework.common.config.NotificationConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class EmailSender @Autowired constructor(
    private val config: NotificationConfig,
    private val webClientBuilder: WebClient.Builder,
) {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun send(subject: String, message: String, emailTo: String, emailCC: String = ""): Mono<Boolean> {
        return webClientBuilder.build()
            .post()
            .uri(config.emailConfig.api)
            .body(
                BodyInserters.fromValue(
                    listOf(
                        EmailDto(
                            title = subject,
                            message = message,
                            userToken = emailTo,
                            userTokenCC = emailCC,
                        ),
                    ),
                ),
            )
            .exchangeToMono { clientResponse ->
                if (clientResponse.statusCode() == HttpStatus.OK) {
                    Mono.just(true)
                } else {
                    log.warn("Error while sending email to  '$emailTo' regarding '$subject'.")
                    Mono.just(false)
                }
            }
            .onErrorResume {
                log.warn("Error while sending email to  '$emailTo' regarding '$subject'. See the error below:.")
                log.warn(it.message)
                Mono.just(false)
            }
    }

    private data class EmailDto(
        val title: String,
        val message: String,
        val userToken: String,
        val userTokenCC: String,
        val type: String = "EMAIL",
        val priority: Int = 1,
    )
}
