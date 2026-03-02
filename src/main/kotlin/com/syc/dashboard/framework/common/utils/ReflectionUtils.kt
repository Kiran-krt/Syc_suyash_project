package com.syc.dashboard.framework.common.utils

import com.syc.dashboard.query.document.dto.DocumentIdDto
import org.slf4j.LoggerFactory
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

object ReflectionUtils {

    private val log = LoggerFactory.getLogger(javaClass)

    fun getFieldValue(obj: Any, fieldName: String): String {
        return obj::class.memberProperties
            .first { it.name == fieldName }
            .getter.call(obj).toString()
    }

    fun setFieldValue(any: Any, fieldName: String, value: Any?) {
        val property = any::class.declaredMemberProperties
            .find { it.name == fieldName } as? KMutableProperty<*>

        if (property != null) {
            try {
                val typedValue = when (val classifier = property.returnType.classifier) {
                    String::class -> value as? String
                    Int::class -> value.toString().toIntOrNull()
                    List::class -> {
                        val elementType = property.returnType.arguments.firstOrNull()?.type?.classifier
                        when (elementType) {
                            DocumentIdDto::class -> {
                                if (value is String) {
                                    listOf(DocumentIdDto(documentId = value))
                                } else {
                                    (value as? List<*>)?.map { it as? DocumentIdDto }
                                }
                            }
                            else -> value as? List<*>
                        }
                    }
                    else -> value
                }
                property.setter.call(any, typedValue)
            } catch (e: Exception) {
                log.warn("Error setting field value: ${e.message}")
            }
        } else {
            log.warn("Field '$fieldName' not found on object of type ${any::class}")
        }
    }
}
