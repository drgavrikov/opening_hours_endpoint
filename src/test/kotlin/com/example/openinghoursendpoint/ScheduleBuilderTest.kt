package com.example.openinghoursendpoint

import com.example.openinghoursendpoint.builder.InvalidScheduleDataException
import com.example.openinghoursendpoint.builder.ScheduleBuilder
import com.example.openinghoursendpoint.model.DayOfWeek
import com.example.openinghoursendpoint.model.OpeningHours
import com.example.openinghoursendpoint.model.OpeningType
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ScheduleBuilderTest {

    @Test
    fun testBuildSchedule() {
        val mondayOpeningHours = listOf(
            OpeningHours(OpeningType.OPEN, 79200),
        )
        val tuesdayOpeningHours = listOf(
            OpeningHours(OpeningType.CLOSE, 3600),
            OpeningHours(OpeningType.OPEN, 25200),
            OpeningHours(OpeningType.CLOSE, 54000)
        )
        val dayOpeningHours = mapOf(
            DayOfWeek.MONDAY to mondayOpeningHours,
            DayOfWeek.TUESDAY to tuesdayOpeningHours
        )

        val schedule = ScheduleBuilder.buildSchedule(dayOpeningHours)

        val humanReadableSchedule =
            "Monday: 10 PM - 1 AM\n" +
            "Tuesday: 7 AM - 3 PM\n" +
            "Wednesday: Closed\n" +
            "Thursday: Closed\n" +
            "Friday: Closed\n" +
            "Saturday: Closed\n" +
            "Sunday: Closed"

        Assertions.assertThat(schedule.getHumanReadable()).isEqualTo(humanReadableSchedule)
    }

    @Test
    fun testValidateCorrectOpeningHours() {

        val mondayOpeningHours = listOf(OpeningHours(OpeningType.OPEN, 88000))
        val wednesdayOpeningHours = listOf(OpeningHours(OpeningType.OPEN, 90000))
        val dayOpeningHours = mapOf(
            DayOfWeek.MONDAY to mondayOpeningHours,
            DayOfWeek.WEDNESDAY to wednesdayOpeningHours
        )

        assertThatThrownBy { ScheduleBuilder.buildSchedule(dayOpeningHours) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage(
                "Exception: Opening hours for the following days exceed max value: \n" +
                        "- MONDAY: ${mondayOpeningHours.joinToString(", ") { it.value.toString() }}\n" +
                        "- WEDNESDAY: ${wednesdayOpeningHours.joinToString(", ") { it.value.toString() }}\n"
            )
    }

    @Test
    fun testValidateOpeningHoursSequence() {
        val mondayOpeningHours = listOf(
            OpeningHours(OpeningType.OPEN, 3600),
            OpeningHours(OpeningType.OPEN, 7200)
        )
        val tuesdayOpeningHours = listOf(
            OpeningHours(OpeningType.OPEN, 3600),
            OpeningHours(OpeningType.CLOSE, 7200),
            OpeningHours(OpeningType.CLOSE, 8800)
        )

        val dayOpeningHours = mapOf(DayOfWeek.MONDAY to mondayOpeningHours, DayOfWeek.TUESDAY to tuesdayOpeningHours)

        assertThatThrownBy { ScheduleBuilder.buildSchedule(dayOpeningHours) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("Exception: Two consecutive OpeningHours with the same type in ${DayOfWeek.MONDAY.name}, ${DayOfWeek.TUESDAY.name}\n")
    }

    @Test
    fun testInvalidFirstCloseHour() {
        val mondayOpeningHours = listOf(OpeningHours(OpeningType.OPEN, 64000))
        val tuesdayOpeningHours = listOf(OpeningHours(OpeningType.CLOSE, 3600), OpeningHours(OpeningType.CLOSE, 4000))
        val dayOpeningHours = mapOf(
            DayOfWeek.MONDAY to mondayOpeningHours,
            DayOfWeek.TUESDAY to tuesdayOpeningHours,
        )
        assertThatThrownBy { ScheduleBuilder.buildSchedule(dayOpeningHours) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("The first Hour should not be of type Close in the subsequent days: ${DayOfWeek.TUESDAY.name}")
    }

    @Test
    fun testInvalidLastOpenHour() {
        val mondayOpeningHours = listOf(
            OpeningHours(OpeningType.OPEN, 20000),
            OpeningHours(OpeningType.CLOSE, 30000),
            OpeningHours(OpeningType.OPEN, 80000),
        )
        val dayOpeningHours = mapOf(DayOfWeek.MONDAY to mondayOpeningHours)
        assertThatThrownBy { ScheduleBuilder.buildSchedule(dayOpeningHours) }
            .isInstanceOf(InvalidScheduleDataException::class.java)
            .hasMessage("The last Hour should not be of type Open in the subsequent days: ${DayOfWeek.MONDAY.name}")
    }
}