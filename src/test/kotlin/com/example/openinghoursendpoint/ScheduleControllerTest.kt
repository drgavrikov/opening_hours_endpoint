package com.example.openinghoursendpoint

import com.example.openinghoursendpoint.controller.ScheduleController
import org.hamcrest.Matchers.equalTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.web.servlet.MockMvc
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

@SpringJUnitConfig
@WebMvcTest(ScheduleController::class)
class ScheduleControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `valid JSON should return 200 OK`() {
        val validJson = readResourceFile("valid_opening_hours.json")
        val humanReadableSchedule = readResourceFile("schedule.txt")

        mockMvc.post("/schedule") {
            content = validJson
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { string(equalTo(humanReadableSchedule)) }
        }
    }

    @Test
    fun `invalid JSON should return 400 Bad Request`() {
        checkBadRequestStatus("invalid_opening_hours.json")
        checkBadRequestStatus("invalid_first_close_hours.json")
        checkBadRequestStatus("invalid_last_open_hours.json")
        checkBadRequestStatus("invalid_opening_hours_sequence.json")
    }

    private fun checkBadRequestStatus(fileName: String) {
        val invalidJson = readResourceFile(fileName)

        mockMvc.post("/schedule") {
            content = invalidJson
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest() }
        }
    }
}