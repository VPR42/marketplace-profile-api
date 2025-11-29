package com.vpr42.marketplaceprofileapi.dto.request

data class UserInfoUpdateRequest(
    val surname: String? = null,
    val name: String? = null,
    val patronymic: String? = null,
    val city: Int? = null,
)
