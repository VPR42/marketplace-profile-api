package com.vpr42.marketplaceprofileapi.util

import com.vpr42.marketplace.jooq.tables.pojos.MastersInfo
import com.vpr42.marketplace.jooq.tables.pojos.Users
import com.vpr42.marketplaceprofileapi.dto.MasterInfo
import com.vpr42.marketplaceprofileapi.dto.OrdersInfo
import com.vpr42.marketplaceprofileapi.dto.ProfileInfo

fun Users.toProfileInfo(
    masterInfo: MastersInfo?,
    skills: List<Int>,
    ordersInfo: OrdersInfo,
) = ProfileInfo(
    id = id,
    email = email,
    surname = surname,
    name = name,
    patronymic = patronymic,
    avatarPath = avatarPath,
    createdAt = createdAt,
    city = city,
    masterInfo = masterInfo?.toDto(),
    skills = skills,
    orders = ordersInfo,
)

fun MastersInfo.toDto() = MasterInfo(
    experience = experience,
    description = requireNotNull(description) { "description shouldn't be null" },
    pseudonym = requireNotNull(pseudonym) { "pseudonym shouldn't be null" },
    phoneNumber = phoneNumber,
    about = requireNotNull(about) { "about shouldn't be null" },
    daysOfWeek = daysOfWeek?.apply { this.sort() } ?: arrayOf(),
    startTime = requireNotNull(startTime) { "startTime shouldn't be null" },
    endTime = requireNotNull(endTime) { "endTime shouldn't be null" }
)
