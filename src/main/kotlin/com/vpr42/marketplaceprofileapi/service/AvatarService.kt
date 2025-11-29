package com.vpr42.marketplaceprofileapi.service

import com.vpr42.marketplaceprofileapi.dto.response.AvatarResponse
import com.vpr42.marketplaceprofileapi.properties.MinioProperties
import com.vpr42.marketplaceprofileapi.repository.UserRepository
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class AvatarService(
    private val minioProperties: MinioProperties,
    private val minioClient: MinioClient,
    private val userRepository: UserRepository,
) {
    private val logger = LoggerFactory.getLogger(AvatarService::class.java)

    fun upload(id: UUID, file: MultipartFile): AvatarResponse {
        val oldUrl = requireNotNull(userRepository.findById(id)) {
            "User with $id is not exist"
        }.avatarPath

        if (oldUrl.startsWith(minioProperties.urls.public)) deleteObject(oldUrl)

        val objectName = "$id-${file.originalFilename ?: "avatar.png"}"
        val avatarUrl = minioProperties.urls.public + objectName

        logger.info("Save avatar of user $id with name $objectName")

        logger.info("Avatar saves successfully")
        putObject(objectName, file)
        userRepository.updateAvatar(id, avatarUrl)
        logger.info("Avatar url successfully updated in database")

        return AvatarResponse(
            filename = objectName,
            url = avatarUrl
        )
    }

    private fun putObject(objectName: String, file: MultipartFile) = minioClient
        .putObject(
            PutObjectArgs.builder()
                .bucket(minioProperties.bucket)
                .`object`(objectName)
                .stream(
                    file.inputStream,
                    file.size,
                    -1,
                )
                .contentType(file.contentType)
                .build()
        )

    private fun deleteObject(oldUrl: String) = minioClient
        .removeObject(
            RemoveObjectArgs.builder()
                .bucket(minioProperties.bucket)
                .`object`(oldUrl.substringAfter(minioProperties.urls.public))
                .build()
        )
}
