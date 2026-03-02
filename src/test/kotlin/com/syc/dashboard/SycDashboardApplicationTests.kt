package com.syc.dashboard

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@TestPropertySource(
    locations = [
        "classpath:application-notification.properties",
        "classpath:application.properties",
    ],
)
@SpringBootTest
class SycDashboardApplicationTests {

    @Test
    fun contextLoads() {
    }
}
