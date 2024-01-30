package com.example.openinghoursendpoint.controller

import com.example.openinghoursendpoint.*
import com.example.openinghoursendpoint.service.ScheduleService
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post


@SpringJUnitConfig
@WebMvcTest(ScheduleController::class)
class ScheduleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var scheduleService: ScheduleService

    @Test
    fun `valid JSON should return 200 OK`() {
        val validJson = readResourceFile(VALID_SCHEDULE_JSON)
        val humanReadableSchedule = readResourceFile(HUMAN_READABLE_SCHEDULE)

        `when`(scheduleService.buildScheduleFromJson(validJson)).thenReturn(VALID_SCHEDULE)

        mockMvc.post("/schedule") {
            content = validJson
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { string(equalTo(humanReadableSchedule)) }
        }
    }
}