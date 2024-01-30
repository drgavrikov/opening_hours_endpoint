package com.example.openinghoursendpoint.service

import com.example.openinghoursendpoint.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ScheduleServiceTest {

    private val scheduleService = ScheduleService()

    @Test
    fun testBuildSchedule() {
        val schedule = scheduleService.buildScheduleFromJson(readResourceFile(VALID_SCHEDULE_JSON))
        assertThat(schedule).isEqualTo(VALID_SCHEDULE)
    }

    @Test
    fun testValidateCorrectOpeningHours() {
        val invalidOpeningHoursJson = readResourceFile(INVALID_HOURS_SCHEDULE_JSON  )
        assertThatThrownBy { scheduleService.buildScheduleFromJson(invalidOpeningHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage(INVALID_HOURS_SCHEDULE_EXCEPTION_MESSAGE)
    }

    @Test
    fun testValidateOpeningHoursSequence() {
        val invalidOpeningHoursJson = readResourceFile(INVALID_SEQUENCE_SCHEDULE_JSON)
        assertThatThrownBy { scheduleService.buildScheduleFromJson(invalidOpeningHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage(INVALID_SEQUENCE_EXCEPTION_MESSAGE)
    }

    @Test
    fun testInvalidFirstCloseHour() {
        val invalidFirstCloseHoursJson = readResourceFile(INVALID_FIRST_CLOSE_SCHEDULE_JSON)
        assertThatThrownBy { scheduleService.buildScheduleFromJson(invalidFirstCloseHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage(INVALID_FIRST_CLOSE_EXCEPTION_MESSAGE)
    }

    @Test
    fun testInvalidLastOpenHour() {
        val invalidLastOpenHoursJson = readResourceFile(INVALID_LAST_OPEN_SCHEDULE_JSON)
        assertThatThrownBy { scheduleService.buildScheduleFromJson(invalidLastOpenHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage(INVALID_LAST_OPEN_EXCEPTION_MESSAGE)
    }
}
