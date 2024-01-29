package com.example.openinghoursendpoint

import com.example.openinghoursendpoint.builder.InvalidScheduleDataException
import com.example.openinghoursendpoint.builder.ScheduleBuilder
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ScheduleBuilderTest {

    @Test
    fun testBuildSchedule() {
        val schedule = ScheduleBuilder.buildScheduleFromJson(readResourceFile("opening_hours.json"))
        val humanReadableSchedule = readResourceFile("schedule.txt")
        Assertions.assertThat(schedule.getHumanReadable()).isEqualTo(humanReadableSchedule)
    }

    @Test
    fun testValidateCorrectOpeningHours() {
        val invalidOpeningHoursJson = readResourceFile("invalid_opening_hours.json")
        assertThatThrownBy { ScheduleBuilder.buildScheduleFromJson(invalidOpeningHoursJson) }
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
        assertThatThrownBy { ScheduleBuilder.buildScheduleFromJson(invalidOpeningHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("Exception: Two consecutive OpeningHours with the same type in MONDAY, TUESDAY\n")
    }

    @Test
    fun testInvalidFirstCloseHour() {
        val invalidFirstCloseHoursJson = readResourceFile("invalid_first_close_hours.json")
        assertThatThrownBy { ScheduleBuilder.buildScheduleFromJson(invalidFirstCloseHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("The first Hour should not be of type Close in the subsequent days: TUESDAY")
    }

    @Test
    fun testInvalidLastOpenHour() {
        val invalidLastOpenHoursJson = readResourceFile("invalid_last_open_hours.json")
        assertThatThrownBy { ScheduleBuilder.buildScheduleFromJson(invalidLastOpenHoursJson) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("The last Hour should not be of type Open in the subsequent days: MONDAY")
    }
}
