package com.vpr42.marketplaceprofileapi.config

import com.vpr42.marketplaceprofileapi.properties.MinioProperties
import io.minio.MinioClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfig(
    private val minioProperties: MinioProperties,
) {

    @Bean
    fun minioClient(): MinioClient = MinioClient
        .builder()
        .endpoint(minioProperties.urls.api)
        .credentials(minioProperties.login, minioProperties.password)
        .build()
}
