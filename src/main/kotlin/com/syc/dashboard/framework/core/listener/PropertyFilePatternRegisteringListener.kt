package com.syc.dashboard.framework.core.listener

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.ResourcePropertySource
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class PropertyFilePatternRegisteringListener :
    ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) {
        val environment = event.environment
        try {
            loadProfileProperties(environment)
            loadPlainProperties(environment)
        } catch (ex: IOException) {
            throw IllegalStateException("Unable to load configuration files", ex)
        }
    }

    private fun loadProfileProperties(environment: ConfigurableEnvironment) {
        val activeProfiles = environment.activeProfiles
        if (activeProfiles.isNotEmpty()) {
            loadProfileProperties(environment, activeProfiles)
        } else {
            loadProfileProperties(
                environment,
                environment.defaultProfiles,
            )
        }
    }

    private fun loadProfileProperties(environment: ConfigurableEnvironment, profiles: Array<String>) {
        for (activeProfile in profiles) {
            addFileToEnvironment(environment, "$NOTIFICATION_PROPERTY_FILE_PREFIX$FILE_SUFFIX")
            addFileToEnvironment(environment, "$NOTIFICATION_PROPERTY_FILE_PREFIX-$activeProfile$FILE_SUFFIX")

            addFileToEnvironment(environment, "$PROPERTY_FILE_PREFIX$FILE_SUFFIX")
            addFileToEnvironment(environment, "$PROPERTY_FILE_PREFIX-$activeProfile$FILE_SUFFIX")
        }
    }

    private fun loadPlainProperties(environment: ConfigurableEnvironment) {
        addFileToEnvironment(environment, PROPERTY_FILE_PREFIX + FILE_SUFFIX)
    }

    private fun addFileToEnvironment(environment: ConfigurableEnvironment, file: String) {
        val classPathResource = ClassPathResource(file)
        if (classPathResource.exists()) {
            environment.propertySources
                .addLast(ResourcePropertySource(classPathResource))
        }
    }

    companion object {
        private const val PROPERTY_FILE_PREFIX = "application"
        private const val NOTIFICATION_PROPERTY_FILE_PREFIX = "$PROPERTY_FILE_PREFIX-notification"
        private const val FILE_SUFFIX = ".properties"
    }
}
