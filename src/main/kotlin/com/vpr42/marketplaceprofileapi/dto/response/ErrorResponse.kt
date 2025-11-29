package com.vpr42.marketplaceprofileapi.dto.response

data class ErrorResponse(
    val status: String,
    val message: String,
    val path: String,
)
