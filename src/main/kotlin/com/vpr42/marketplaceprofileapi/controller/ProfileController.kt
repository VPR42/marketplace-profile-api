package com.vpr42.marketplaceprofileapi.controller

import com.vpr42.marketplaceprofileapi.dto.ProfileInfo
import com.vpr42.marketplaceprofileapi.dto.request.MasterInfoCreateRequest
import com.vpr42.marketplaceprofileapi.dto.request.MasterInfoUpdateRequest
import com.vpr42.marketplaceprofileapi.dto.request.UserInfoUpdateRequest
import com.vpr42.marketplaceprofileapi.dto.response.AvatarResponse
import com.vpr42.marketplaceprofileapi.service.AvatarService
import com.vpr42.marketplaceprofileapi.service.ProfileService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Controller
@RequestMapping("/api/profile")
@Tag(
    name = "Профили",
    description = "Контроллер работы с профилем"
)
class ProfileController(
    private val profileService: ProfileService,
    private val avatarService: AvatarService,
) {
    private val logger = LoggerFactory.getLogger(ProfileController::class.java)

    @GetMapping
    @Operation(summary = "Запрос на получение информации о профиле текущего пользователя")
    fun getUserProfile(
        @RequestHeader("id") id: String
    ): ResponseEntity<ProfileInfo> = getProfile(id)

    @GetMapping("/{id}")
    @Operation(summary = "Запрос на получение информации о профиле любого пользователя по id")
    fun getProfileById(
        @PathVariable("id") id: String
    ): ResponseEntity<ProfileInfo> = getProfile(id)

    private fun getProfile(id: String): ResponseEntity<ProfileInfo> {
        logger.info("Request to get profile of user with id: $id")
        return ResponseEntity.ok(
            profileService.getProfileById(UUID.fromString(id))
        )
    }

    @PostMapping("master-info")
    @Operation(summary = "Запрос на \"регистрацию\" мастера как пользователя")
    fun createMasterInfo(
        @RequestHeader("id") id: String,
        @RequestBody request: MasterInfoCreateRequest,
    ): ResponseEntity<ProfileInfo> {
        logger.info("Request to create master info of user with id: $id")
        return ResponseEntity.ok(
            profileService.createMasterInfo(UUID.fromString(id), request)
        )
    }

    @PatchMapping("/user")
    @Operation(summary = "Запрос на изменение пользовательской информации пользователя")
    fun updateUserInfo(
        @RequestHeader("id") id: String,
        @RequestBody request: UserInfoUpdateRequest,
    ): ResponseEntity<ProfileInfo> {
        logger.info("Request to update user info of user with id: $id")
        return ResponseEntity.ok(
            profileService.updateUserInfo(UUID.fromString(id), request)
        )
    }

    @PatchMapping("/master-info")
    @Operation(summary = "Запрос на изменение информации о пользователе как о мастере")
    fun updateMasterInfo(
        @RequestHeader("id") id: String,
        @RequestBody request: MasterInfoUpdateRequest,
    ): ResponseEntity<ProfileInfo> {
        logger.info("Request to update master info of user with id: $id")
        return ResponseEntity.ok(
            profileService.updateMasterInfo(UUID.fromString(id), request)
        )
    }

    @PatchMapping("/skills")
    @Operation(summary = "Запрос на изменение списка навыков пользователя")
    fun updateSkills(
        @RequestHeader("id") id: String,
        @RequestBody request: List<Int>,
    ): ResponseEntity<ProfileInfo> {
        logger.info("Request to update master skills of user with id: $id")
        return ResponseEntity.ok(
            profileService.updateSkills(UUID.fromString(id), request)
        )
    }

    @PutMapping("/avatar", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "Запрос на загрузку аватара пользователя")
    fun uploadAvatar(
        @RequestHeader("id") id: String,
        @RequestPart("file") file: MultipartFile,
    ): ResponseEntity<AvatarResponse> {
        logger.info("Request to upload new avatar for user $id")
        return ResponseEntity.ok(
            avatarService.upload(UUID.fromString(id), file)
        )
    }
}
