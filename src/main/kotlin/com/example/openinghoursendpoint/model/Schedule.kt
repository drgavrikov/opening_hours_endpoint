package com.example.openinghoursendpoint.model

class Schedule(private val dayOpeningHours: Map<DayOfWeek, List<OpeningHours>>) {

    fun getHumanReadable(): String {
        return dayOpeningHours.map { (day, openingHour) ->
            val humanReadableDay = day.name.lowercase().capitalize()
            if (openingHour.isNotEmpty()) {
                val humanReadableHours = (openingHour.indices step 2)
                    .joinToString(", ") { index ->
                        openingHour[index].convertTime() + " - " + openingHour[index + 1].convertTime()
                    }
                "$humanReadableDay: $humanReadableHours"
            } else "$humanReadableDay: Closed"
        }.joinToString("\n")
    }
}
