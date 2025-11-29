package com.vpr42.marketplaceprofileapi.dto

data class MasterInfo(
    val experience: Int,
    val description: String,
    val pseudonym: String,
    val phoneNumber: String,
    val about: String,
    val daysOfWeek: Array<Int?>?,
    val startTime: String,
    val endTime: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MasterInfo

        if (experience != other.experience) return false
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
        result = 31 * result + description.hashCode()
        result = 31 * result + pseudonym.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + about.hashCode()
        result = 31 * result + (daysOfWeek?.contentHashCode() ?: 0)
        result = 31 * result + startTime.hashCode()
        result = 31 * result + endTime.hashCode()
        return result
    }
}
