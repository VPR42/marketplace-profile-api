package com.vpr42.marketplaceprofileapi.controller

import com.vpr42.marketplaceprofileapi.dto.ProfileResponse
import com.vpr42.marketplaceprofileapi.service.ProfileService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Controller
@RequestMapping("/api/profile")
@Tag(
    name = "Профили",
    description = "Контроллер работы с профилем"
)
class ProfileController(
    private val profileService: ProfileService,
) {
    private val logger = LoggerFactory.getLogger(ProfileController::class.java)

    @GetMapping
    @Operation(summary = "Запрос на получение информации о профиле текущего пользователя")
    fun getUserProfile(
        @RequestHeader("id") id: String
    ): ResponseEntity<ProfileResponse> = getProfile(id)

    @GetMapping("/{id}")
    @Operation(summary = "Запрос на получение информации о профиле любого пользователя по id")
    fun getProfileById(
        @PathVariable("id") id: String
    ): ResponseEntity<ProfileResponse> = getProfile(id)

    private fun getProfile(id: String): ResponseEntity<ProfileResponse> {
        logger.info("Request to get profile of user with id: $id")
        return ResponseEntity.ok(
            profileService.getProfileById(UUID.fromString(id))
        )
    }
}
