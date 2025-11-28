package com.vpr42.marketplaceprofileapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class MarketplaceProfileApiApplication

fun main(args: Array<String>) {
    runApplication<MarketplaceProfileApiApplication>(*args)
}
