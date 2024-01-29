package com.example.openinghoursendpoint.builder

import com.example.openinghoursendpoint.model.DayOfWeek
import com.example.openinghoursendpoint.model.OpeningHours
import com.example.openinghoursendpoint.model.OpeningType
import com.example.openinghoursendpoint.model.Schedule
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

object ScheduleBuilder {

    fun buildScheduleFromJson(openingHoursJson: String): Schedule {
        val dayOpeningHours = Json.decodeFromString<Map<DayOfWeek, List<OpeningHours>>>(openingHoursJson)
        val schedule = DayOfWeek.entries.associateWith { _ -> mutableListOf<OpeningHours>() }
        dayOpeningHours.forEach { (day, openingHours) -> schedule.getValue(day).addAll(openingHours) }
        schedule.forEach { (_, openingHours) -> openingHours.sort() }

        schedule
            .filter { (_, openingHours) -> openingHours.isNotEmpty() }
            .forEach { (day, openingHours) ->
                val first = openingHours[0]
                if (first.type == OpeningType.CLOSE) {
                    openingHours.removeFirst()
                    schedule.getValue(day.previous()).add(first)
                }
            }

        validateCorrectOpeningHours(schedule)
        validateOpeningHoursSequence(schedule)

        return Schedule(schedule)
    }

    private fun validateCorrectOpeningHours(dayOpeningHours: Map<DayOfWeek, List<OpeningHours>>) {
        val incorrectOpeningHours = dayOpeningHours.entries
            .map { (day, openingHours) -> day to openingHours.filter { hour -> hour.value >= TimeUnit.DAYS.toSeconds(1) } }
            .filter { (_, openingHours) -> openingHours.isNotEmpty() }
            .toMap()

        if (incorrectOpeningHours.isNotEmpty()) {
            val message = "Exception: Opening hours for the following days exceed max value: \n" + incorrectOpeningHours
                .entries
                .joinToString("") { (day, openingHours) -> "- $day: ${openingHours.joinToString(", ") { it.value.toString() }}\n" }
            throw InvalidScheduleDataException(message)
        }
    }

    private fun validateOpeningHoursSequence(dayOpeningHours: Map<DayOfWeek, List<OpeningHours>>) {
        val daysWithIncorrectOpeningHoursSequence = dayOpeningHours
            .filter { (_, openingHours) -> openingHours.zipWithNext().any { (prev, next) -> prev.type == next.type } }
            .keys
        if (daysWithIncorrectOpeningHoursSequence.isNotEmpty()) {
            val message = "Exception: Two consecutive OpeningHours with the same type in ${
                daysWithIncorrectOpeningHoursSequence.joinToString(", ") { it.name }
            }\n"
            throw InvalidScheduleDataException(message)
        }

        val daysWithFirstCloseHour = dayOpeningHours
            .filter { (_, openingHours) -> openingHours.isNotEmpty() && openingHours.first().type == OpeningType.CLOSE }
            .keys
        if (daysWithFirstCloseHour.isNotEmpty()) {
            val message = "The first Hour should not be of type Close in the subsequent days: ${
                daysWithFirstCloseHour.joinToString(", ") { it.name }
            }"
            throw InvalidScheduleDataException(message)
        }

        val daysWithLastOpenHour = dayOpeningHours
            .filter { (_, openingHours) -> openingHours.isNotEmpty() && openingHours.last().type == OpeningType.OPEN }
            .keys
        if (daysWithLastOpenHour.isNotEmpty()) {
            val message =
                "The last Hour should not be of type Open in the subsequent days: ${
                    daysWithLastOpenHour.joinToString(", ") { it.name }
                }"
            throw InvalidScheduleDataException(message)
        }
    }
}
