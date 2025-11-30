package com.vpr42.marketplaceprofileapi.dto

import java.time.LocalDateTime
import java.util.*

data class ProfileInfo(
    val id: UUID,
    val email: String,
    val password: String,
    val surname: String,
    val name: String,
    val patronymic: String,
    val avatarPath: String,
    val createdAt: LocalDateTime,
    val city: Int,
    val masterInfo: MasterInfo? = null,
    val skills: List<Int>,
    val orders: OrdersInfo,
)
