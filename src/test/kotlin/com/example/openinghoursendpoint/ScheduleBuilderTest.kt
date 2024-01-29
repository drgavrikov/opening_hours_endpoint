package com.example.openinghoursendpoint

import com.example.openinghoursendpoint.service.InvalidScheduleDataException
import com.example.openinghoursendpoint.service.ScheduleService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ScheduleBuilderTest {

    private val scheduleService = ScheduleService()

    @Test
    fun testBuildSchedule() {
        val schedule = scheduleService.buildScheduleFromJson(readResourceFile("valid_opening_hours.json"))
        val humanReadableSchedule = readResourceFile("schedule.txt")
        assertThat(schedule.getHumanReadable()).isEqualTo(humanReadableSchedule)
    }

    @Test
    fun testValidateCorrectOpeningHours() {
        val invalidOpeningHoursJson = readResourceFile("invalid_opening_hours.json")
        assertThatThrownBy { scheduleService.buildScheduleFromJson(invalidOpeningHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage(
                "Exception: Opening hours for the following days exceed max value: \n" +
                        "- MONDAY: 88000, 98800\n" +
                        "- TUESDAY: 95000, 97800\n"
            )
    }

    @Test
    fun testValidateOpeningHoursSequence() {
        val invalidOpeningHoursJson = readResourceFile("invalid_opening_hours_sequence.json")
        assertThatThrownBy { scheduleService.buildScheduleFromJson(invalidOpeningHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("Exception: Two consecutive OpeningHours with the same type in MONDAY, TUESDAY\n")
    }

    @Test
    fun testInvalidFirstCloseHour() {
        val invalidFirstCloseHoursJson = readResourceFile("invalid_first_close_hours.json")
        assertThatThrownBy { scheduleService.buildScheduleFromJson(invalidFirstCloseHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("The first Hour should not be of type Close in the subsequent days: TUESDAY")
    }

    @Test
    fun testInvalidLastOpenHour() {
        val invalidLastOpenHoursJson = readResourceFile("invalid_last_open_hours.json")
        assertThatThrownBy { scheduleService.buildScheduleFromJson(invalidLastOpenHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("The last Hour should not be of type Open in the subsequent days: MONDAY")
    }
}
