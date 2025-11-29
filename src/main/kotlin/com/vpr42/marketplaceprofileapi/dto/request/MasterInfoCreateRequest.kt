package com.vpr42.marketplaceprofileapi.dto.request

import com.vpr42.marketplaceprofileapi.dto.MasterInfo

data class MasterInfoCreateRequest(
    val masterInfo: MasterInfo,
    val skills: List<Int>,
)
