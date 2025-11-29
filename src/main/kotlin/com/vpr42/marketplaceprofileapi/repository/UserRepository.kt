package com.vpr42.marketplaceprofileapi.repository

import com.vpr42.marketplace.jooq.tables.pojos.Users
import com.vpr42.marketplace.jooq.tables.references.USERS
import com.vpr42.marketplaceprofileapi.dto.request.UserInfoUpdateRequest
import org.jooq.DSLContext
import org.jooq.Field
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepository(
    private val dsl: DSLContext
) {

    fun findById(id: UUID) = dsl
        .selectFrom(USERS)
        .where(USERS.ID.eq(id))
        .fetchOneInto(Users::class.java)

    fun update(id: UUID, source: UserInfoUpdateRequest): Users? {
        val updates = mutableMapOf<Field<*>, Any?>()

        source.surname?.let { updates[USERS.SURNAME] = it }
        source.name?.let { updates[USERS.NAME] = it }
        source.patronymic?.let { updates[USERS.PATRONYMIC] = it }
        source.city?.let { updates[USERS.CITY] = it }

        return if (updates.isNotEmpty()) {
            dsl.update(USERS)
                .set(updates)
                .where(USERS.ID.eq(id))
                .returning()
                .fetchOneInto(Users::class.java)
        } else {
            dsl.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOneInto(Users::class.java)
        }
    }

    fun updateAvatar(id: UUID, url: String) = dsl
        .update(USERS)
        .set(USERS.AVATAR_PATH, url)
        .where(USERS.ID.eq(id))
        .returning()
        .fetchOneInto(Users::class.java)
}
