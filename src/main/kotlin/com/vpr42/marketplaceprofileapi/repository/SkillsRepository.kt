package com.vpr42.marketplaceprofileapi.repository

import com.vpr42.marketplace.jooq.tables.pojos.Skills
import com.vpr42.marketplace.jooq.tables.references.SKILLS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class SkillsRepository(
    private val dsl: DSLContext
) {

    fun findAll() = dsl
        .selectFrom(SKILLS)
        .fetchInto(Skills::class.java)
}
