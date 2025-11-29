package com.vpr42.marketplaceprofileapi.dto.request

data class MasterInfoUpdateRequest(
    val experience: Int? = null,
    val description: String? = null,
    val pseudonym: String? = null,
    val phoneNumber: String? = null,
    val about: String? = null,
    val daysOfWeek: Array<Int?>? = null,
    val startTime: String? = null,
    val endTime: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MasterInfoUpdateRequest

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
        var result = experience ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (pseudonym?.hashCode() ?: 0)
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + (about?.hashCode() ?: 0)
        result = 31 * result + (daysOfWeek?.contentHashCode() ?: 0)
        result = 31 * result + (startTime?.hashCode() ?: 0)
        result = 31 * result + (endTime?.hashCode() ?: 0)
        return result
    }
}
