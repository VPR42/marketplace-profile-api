package com.vpr42.marketplaceprofileapi.repository

import com.vpr42.marketplace.jooq.tables.pojos.MasterSkills
import com.vpr42.marketplace.jooq.tables.references.MASTER_SKILLS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MasterSkillsRepository(
    private val dsl: DSLContext
) {

    fun findByMaster(masterId: UUID) = dsl
        .selectFrom(MASTER_SKILLS)
        .where(MASTER_SKILLS.MASTER_ID.eq(masterId))
        .fetchInto(MasterSkills::class.java)

    fun insertSingle(id: UUID, skill: Int) = dsl
        .insertInto(MASTER_SKILLS)
        .set(MASTER_SKILLS.MASTER_ID, id)
        .set(MASTER_SKILLS.SKILL_ID, skill)
        .execute()

    fun insertList(id: UUID, skills: List<Int>): Int {
        val insert = dsl.insertInto(
            MASTER_SKILLS,
            MASTER_SKILLS.MASTER_ID,
            MASTER_SKILLS.SKILL_ID
        )

        skills.forEach { insert.values(id, it) }
        return insert.execute()
    }

    fun deleteAllByMasterId(id: UUID) = dsl
        .delete(MASTER_SKILLS)
        .where(MASTER_SKILLS.MASTER_ID.eq(id))
        .execute()
}
