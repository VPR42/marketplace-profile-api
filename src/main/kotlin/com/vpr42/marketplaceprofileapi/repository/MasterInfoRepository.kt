package com.vpr42.marketplaceprofileapi.repository

import com.vpr42.marketplace.jooq.tables.pojos.MastersInfo
import com.vpr42.marketplace.jooq.tables.references.MASTERS_INFO
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MasterInfoRepository(
    private val dsl: DSLContext
) {

    fun findByUserId(id: UUID) = dsl
        .selectFrom(MASTERS_INFO)
        .where(MASTERS_INFO.MASTER_ID.eq(id))
        .fetchOneInto(MastersInfo::class.java)

    fun isMasterInfoExists(id: UUID) = dsl
        .fetchExists(
            dsl.selectOne()
                .from(MASTERS_INFO)
                .where(MASTERS_INFO.MASTER_ID.eq(id))
        )
}
