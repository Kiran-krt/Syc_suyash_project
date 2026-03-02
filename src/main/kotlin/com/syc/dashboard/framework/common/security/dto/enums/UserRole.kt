package com.syc.dashboard.framework.common.security.dto.enums

enum class UserRole {
    ADMIN, MANAGER, EMPLOYEE, SYSTEM, SUPERVISOR;

    companion object {

        fun ALL(): Array<String> = entries.map { role -> role.name }.toTypedArray()

        fun ALL_ADMIN(): Array<String> = entries
            .filter { it == ADMIN }
            .map { it.name }
            .toTypedArray()

        fun ALL_EMPLOYEE(): Array<String> = entries
            .filter { it == EMPLOYEE || it == MANAGER || it == SUPERVISOR }
            .map { it.name }
            .toTypedArray()
    }
}
