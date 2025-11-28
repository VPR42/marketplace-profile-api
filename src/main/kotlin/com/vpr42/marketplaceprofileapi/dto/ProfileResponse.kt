package com.vpr42.marketplaceprofileapi.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.UUID

data class ProfileResponse (
    val id: UUID,
    val email: String,
    val password: String,
    val surname: String,
    val name: String,
    val patronymic: String,
    val avatarPath: String,
    val createdAt: LocalDateTime,
    val city: Int,
    @field:JsonProperty("master-info")
    val masterInfo: MasterInfo? = null,
    val skills: List<Int>,
) {
    data class MasterInfo(
        val masterId: UUID,
        val experience: Int,
        val description: String?,
        val pseudonym: String?,
        val phoneNumber: String,
        val about: String?,
        val daysOfWeek: Array<Int?>?,
        val startTime: String?,
        val endTime: String?
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as MasterInfo

            if (experience != other.experience) return false
            if (masterId != other.masterId) return false
            if (description != other.description) return false
            if (pseudonym != other.pseudonym) return false
            if (phoneNumber != other.phoneNumber) return false
            if (about != other.about) return false
            if (!daysOfWeek.contentEquals(other.daysOfWeek)) return false
            if (startTime != other.startTime) return false
            if (endTime != other.endTime) return false

            return true
        }

        override fun hashCode(): Int {
            var result = experience
            result = 31 * result + masterId.hashCode()
            result = 31 * result + (description?.hashCode() ?: 0)
            result = 31 * result + (pseudonym?.hashCode() ?: 0)
            result = 31 * result + phoneNumber.hashCode()
            result = 31 * result + (about?.hashCode() ?: 0)
            result = 31 * result + (daysOfWeek?.contentHashCode() ?: 0)
            result = 31 * result + (startTime?.hashCode() ?: 0)
            result = 31 * result + (endTime?.hashCode() ?: 0)
            return result
        }
    }
}
