package com.vpr42.marketplaceprofileapi.repository

import com.vpr42.marketplace.jooq.tables.pojos.MasterSkills
import com.vpr42.marketplace.jooq.tables.references.MASTER_SKILLS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MasterSkillsRepository(
    private val dsl: DSLContext
) {

    fun findByMaster(masterId: UUID) = dsl
        .selectFrom(MASTER_SKILLS)
        .where(MASTER_SKILLS.MASTER_ID.eq(masterId))
        .fetchInto(MasterSkills::class.java)
}
