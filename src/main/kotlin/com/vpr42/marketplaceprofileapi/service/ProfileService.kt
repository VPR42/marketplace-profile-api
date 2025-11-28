package com.vpr42.marketplaceprofileapi.service

import com.vpr42.marketplaceprofileapi.dto.ProfileResponse
import com.vpr42.marketplaceprofileapi.repository.MasterInfoRepository
import com.vpr42.marketplaceprofileapi.repository.MasterSkillsRepository
import com.vpr42.marketplaceprofileapi.repository.UserRepository
import com.vpr42.marketplaceprofileapi.util.toProfileResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfileService(
    private val userRepository: UserRepository,
    private val masterInfoRepository: MasterInfoRepository,
    private val masterSkillsRepository: MasterSkillsRepository
) {

    fun getProfileById(id: UUID): ProfileResponse {
        val user = requireNotNull(userRepository.findById(id)) {
            "User with $id is not exist"
        }
        val masterInfo = masterInfoRepository.findByUserId(id)
        val skills = masterSkillsRepository.findByMaster(id)

        require(skills.isNotEmpty()) {
            "Skills list shouldn't was empty"
        }

        return user.toProfileResponse(masterInfo, skills.map { it.skillId })
    }
}
