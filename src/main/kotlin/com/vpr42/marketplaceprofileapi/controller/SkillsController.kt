package com.vpr42.marketplaceprofileapi.controller

import com.vpr42.marketplace.jooq.tables.pojos.Skills
import com.vpr42.marketplaceprofileapi.service.SkillsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/skills")
@Tag(
    name = "Навыки",
    description = "Контроллер получения списка навыков"
)
class SkillsController(
    private val skillsService: SkillsService,
) {
    private val logger = LoggerFactory.getLogger(SkillsController::class.java)

    @GetMapping
    @Operation(summary = "Получение полного списка навыков")
    fun getSkillsList(): List<Skills> {
        logger.info("Request to get skills list")
        return skillsService.getAll()
    }
}
