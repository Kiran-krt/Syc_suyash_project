package com.syc.dashboard.framework.core.utils

import com.google.gson.Gson
import com.syc.dashboard.framework.core.dto.BaseDto
import com.syc.dashboard.framework.core.entity.BaseEntity
import org.springframework.beans.BeanUtils
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object EntityDtoConversion {
    fun <T : BaseEntity, U : BaseDto> toDto(entity: T, dtoClass: KClass<U>): U {
        val dto = dtoClass.createInstance()
        BeanUtils.copyProperties(entity, dto)
        return dto
    }

    fun <T : BaseEntity, U : BaseDto> toEntity(dto: U, entityClass: KClass<T>): T {
        val entity = entityClass.createInstance()
        BeanUtils.copyProperties(dto, entity)
        return entity
    }

    fun <T : Any, U : Any> copyProperties(source: U, target: KClass<T>): T {
        val targetObject = target.createInstance()
        BeanUtils.copyProperties(source, targetObject)
        return targetObject
    }

    fun <T : Any, U : Any> copyFromJson(source: U, target: Class<T>): T {
        val gson = Gson()
        val sourceJson = gson.toJson(source)
        return gson.fromJson(sourceJson, target)
    }
}
