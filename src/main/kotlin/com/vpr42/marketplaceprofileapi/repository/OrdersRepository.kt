package com.vpr42.marketplaceprofileapi.repository

import com.vpr42.marketplace.jooq.tables.pojos.Orders
import com.vpr42.marketplace.jooq.tables.references.JOBS
import com.vpr42.marketplace.jooq.tables.references.ORDERS
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class OrdersRepository(
    private val dsl: DSLContext
) {

    fun findByMasterId(id: UUID) = dsl
        .select(ORDERS.asterisk())
        .from(ORDERS)
        .join(JOBS)
        .on(ORDERS.JOB_ID.eq(JOBS.ID))
        .where(JOBS.MASTER_ID.eq(id))
        .fetchInto(Orders::class.java)
}
