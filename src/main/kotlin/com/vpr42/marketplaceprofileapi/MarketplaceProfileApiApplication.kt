package com.vpr42.marketplaceprofileapi

import com.vpr42.marketplaceprofileapi.properties.MinioProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(
    MinioProperties::class
)
class MarketplaceProfileApiApplication

fun main(args: Array<String>) {
    runApplication<MarketplaceProfileApiApplication>(*args)
}
