package com.vpr42.marketplaceprofileapi.service

import com.vpr42.marketplace.jooq.tables.records.MastersInfoRecord
import com.vpr42.marketplaceprofileapi.dto.OrdersInfo
import com.vpr42.marketplaceprofileapi.dto.ProfileInfo
import com.vpr42.marketplaceprofileapi.dto.request.MasterInfoCreateRequest
import com.vpr42.marketplaceprofileapi.dto.request.MasterInfoUpdateRequest
import com.vpr42.marketplaceprofileapi.dto.request.UserInfoUpdateRequest
import com.vpr42.marketplaceprofileapi.repository.MasterInfoRepository
import com.vpr42.marketplaceprofileapi.repository.MasterSkillsRepository
import com.vpr42.marketplaceprofileapi.repository.OrdersRepository
import com.vpr42.marketplaceprofileapi.repository.UserRepository
import com.vpr42.marketplaceprofileapi.util.toProfileInfo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfileService(
    private val userRepository: UserRepository,
    private val masterInfoRepository: MasterInfoRepository,
    private val ordersRepository: OrdersRepository,
    private val masterSkillsRepository: MasterSkillsRepository
) {
    private val logger = LoggerFactory.getLogger(ProfileService::class.java)

    fun getProfileById(userId: UUID): ProfileInfo {
        val user = requireNotNull(userRepository.findById(userId)) {
            "User with $userId is not exist"
        }
        val masterInfo = masterInfoRepository.findByUserId(userId)
        val skills = masterSkillsRepository.findByMaster(userId)

        require(skills.isNotEmpty()) {
            "Skills list shouldn't was empty"
        }

        return user.toProfileInfo(
            masterInfo = masterInfo,
            ordersInfo = getOrdersInfo(userId),
            skills = skills.map { it.skillId }
        )
    }

    fun createMasterInfo(userId: UUID, request: MasterInfoCreateRequest): ProfileInfo {
        require(!masterInfoRepository.isMasterInfoExists(userId)) { "Master info already exist" }
        val user = requireNotNull(userRepository.findById(userId)) { "User with $userId is not exist" }

        val masterInfo = masterInfoRepository.insert(
            MastersInfoRecord(
                masterId = userId,
                experience = request.masterInfo.experience,
                description = request.masterInfo.description,
                pseudonym = request.masterInfo.pseudonym,
                phoneNumber = request.masterInfo.phoneNumber,
                about = request.masterInfo.about,
                daysOfWeek = request.masterInfo.daysOfWeek.apply { sort() },
                startTime = request.masterInfo.startTime,
                endTime = request.masterInfo.endTime,
            )
        )

        request.skills.forEach {
            masterSkillsRepository.insertSingle(userId, it)
        }

        logger.info("Master info created successfully")
        return user.toProfileInfo(
            masterInfo = masterInfo,
            ordersInfo = getOrdersInfo(userId),
            skills = masterSkillsRepository
                .findByMaster(userId)
                .map { it.skillId }
        )
    }

    fun updateUserInfo(userId: UUID, request: UserInfoUpdateRequest): ProfileInfo {
        val user = requireNotNull(userRepository.update(userId, request)) {
            "User not exist. Update impossible"
        }

        logger.info("User info updated successfully")
        return user.toProfileInfo(
            masterInfo = masterInfoRepository.findByUserId(userId),
            ordersInfo = getOrdersInfo(userId),
            skills = masterSkillsRepository
                .findByMaster(userId)
                .map { it.skillId }
        )
    }

    fun updateMasterInfo(userId: UUID, request: MasterInfoUpdateRequest): ProfileInfo {
        val user = requireNotNull(userRepository.findById(userId)) { "User with $userId is not exist" }

        val masterInfo = requireNotNull(masterInfoRepository.update(userId, request)) {
            "Master info not exist. Update impossible"
        }

        logger.info("Master info updated successfully")
        return user.toProfileInfo(
            masterInfo = masterInfo,
            ordersInfo = getOrdersInfo(userId),
            skills = masterSkillsRepository
                .findByMaster(userId)
                .map { it.skillId }
        )
    }

    fun updateSkills(userId: UUID, request: List<Int>): ProfileInfo {
        val user = requireNotNull(userRepository.findById(userId)) { "User with $userId is not exist" }

        logger.info("Start clear master $userId skills")
        masterSkillsRepository.deleteAllByMasterId(userId)
        logger.info("Clearing successful")

        logger.info("Start add not contained in database skills")
        masterSkillsRepository.insertList(userId, request)
        logger.info("Adding successful")

        logger.info("Master skills updated successfully")
        return user.toProfileInfo(
            masterInfo = masterInfoRepository.findByUserId(userId),
            ordersInfo = getOrdersInfo(userId),
            skills = masterSkillsRepository
                .findByMaster(userId)
                .map { it.skillId }
        )
    }

    private fun getOrdersInfo(id: UUID): OrdersInfo {
        val orderList = ordersRepository
            .findByMasterId(id)
            .filter { it.status == COMPLETED_STATUS || it.status == REJECTED_STATUS }

        val completedPercent =
            (orderList.filter { it.status == COMPLETED_STATUS }.size.toDouble() / orderList.size.toDouble()) * 100

        return OrdersInfo(
            ordersCount = orderList.size,
            completedPercent = completedPercent.toInt()
        )
    }

    companion object {
        private const val COMPLETED_STATUS = "COMPLETED"
        private const val REJECTED_STATUS = "REJECTED"
    }
}
