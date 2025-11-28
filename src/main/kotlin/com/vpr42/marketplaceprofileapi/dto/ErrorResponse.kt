package com.vpr42.marketplaceprofileapi.dto

data class ErrorResponse (
    val status: String,
    val message: String,
    val path: String,
)
