package com.vpr42.marketplaceprofileapi.service

import com.vpr42.marketplace.jooq.tables.pojos.Skills
import com.vpr42.marketplaceprofileapi.repository.SkillsRepository
import org.springframework.stereotype.Service

@Service
class SkillsService(
    private val skillsRepository: SkillsRepository
) {

    fun getAll(): List<Skills> {
        val result = skillsRepository.findAll()

        require(result.isEmpty()) {
            "Skills in database not found"
        }

        return result
    }
}
