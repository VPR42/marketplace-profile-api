package com.vpr42.marketplaceprofileapi.util

import com.vpr42.marketplace.jooq.tables.pojos.MastersInfo
import com.vpr42.marketplace.jooq.tables.pojos.Users
import com.vpr42.marketplaceprofileapi.dto.ProfileResponse

fun Users.toProfileResponse(masterInfo: MastersInfo?, skills: List<Int>) = ProfileResponse(
    id = id,
    email = email,
    password = password,
    surname = surname,
    name = name,
    patronymic = patronymic,
    avatarPath = avatarPath,
    createdAt = createdAt,
    city = city,
    masterInfo = masterInfo?.toDto(),
    skills = skills
)

fun MastersInfo.toDto() = ProfileResponse.MasterInfo(
    masterId = masterId,
    experience = experience,
    description = description,
    pseudonym = pseudonym,
    phoneNumber = phoneNumber,
    about = about,
    daysOfWeek = daysOfWeek,
    startTime = startTime,
    endTime = endTime
)
