package com.example.openinghoursendpoint.model

import java.util.*

class Schedule(private val dayOpeningHours: Map<DayOfWeek, List<OpeningHours>>) {

    fun getHumanReadable(): String {
        return dayOpeningHours.map { (day, openingHour) ->
            val humanReadableDay = day.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }
            if (openingHour.isNotEmpty()) {
                val humanReadableHours = (openingHour.indices step 2)
                    .joinToString(", ") { index ->
                        openingHour[index].convertTime() + " - " + openingHour[index + 1].convertTime()
                    }
                "$humanReadableDay: $humanReadableHours"
            } else "$humanReadableDay: Closed"
        }.joinToString("\n")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Schedule

        return dayOpeningHours == other.dayOpeningHours
    }

    override fun hashCode(): Int {
        return dayOpeningHours.hashCode()
    }

    override fun toString(): String {
        return getHumanReadable()
    }
}
