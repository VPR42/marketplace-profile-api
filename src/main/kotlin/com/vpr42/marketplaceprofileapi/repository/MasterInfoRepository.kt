package com.vpr42.marketplaceprofileapi.repository

import com.vpr42.marketplace.jooq.tables.pojos.MastersInfo
import com.vpr42.marketplace.jooq.tables.records.MastersInfoRecord
import com.vpr42.marketplace.jooq.tables.references.MASTERS_INFO
import com.vpr42.marketplaceprofileapi.dto.request.MasterInfoUpdateRequest
import org.jooq.DSLContext
import org.jooq.Field
import org.springframework.stereotype.Repository
import java.util.*

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

    fun insert(record: MastersInfoRecord) = dsl
        .insertInto(MASTERS_INFO)
        .set(record)
        .returning()
        .fetchOneInto(MastersInfo::class.java)

    fun update(userId: UUID, source: MasterInfoUpdateRequest): MastersInfo? {
        val updates = mutableMapOf<Field<*>, Any?>()

        source.experience?.let { updates[MASTERS_INFO.EXPERIENCE] = it }
        source.description?.let { updates[MASTERS_INFO.DESCRIPTION] = it }
        source.pseudonym?.let { updates[MASTERS_INFO.PSEUDONYM] = it }
        source.phoneNumber?.let { updates[MASTERS_INFO.PHONE_NUMBER] = it }
        source.about?.let { updates[MASTERS_INFO.ABOUT] = it }
        source.daysOfWeek?.let { updates[MASTERS_INFO.DAYS_OF_WEEK] = it }
        source.startTime?.let { updates[MASTERS_INFO.START_TIME] = it }
        source.endTime?.let { updates[MASTERS_INFO.END_TIME] = it }

        return if (updates.isNotEmpty()) {
            dsl.update(MASTERS_INFO)
                .set(updates)
                .where(MASTERS_INFO.MASTER_ID.eq(userId))
                .returning()
                .fetchOneInto(MastersInfo::class.java)
        } else {
            dsl.selectFrom(MASTERS_INFO)
                .where(MASTERS_INFO.MASTER_ID.eq(userId))
                .fetchOneInto(MastersInfo::class.java)
        }
    }
}
