package com.vpr42.marketplaceprofileapi.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.minio")
data class MinioProperties(
    val login: String,
    val password: String,
    val bucket: String,
    val urls: MinioUrlsProperty
) {
    data class MinioUrlsProperty(
        val api: String,
        val public: String,
    )
}
