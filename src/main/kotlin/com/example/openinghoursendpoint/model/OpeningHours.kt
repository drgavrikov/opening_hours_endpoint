package com.example.openinghoursendpoint.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Serializable
data class OpeningHours(
    @SerialName("type") val type: OpeningType,
    @SerialName("value") val value: Long
) : Comparable<OpeningHours> {

    fun convertTime(): String {
        val localTime = LocalTime.ofSecondOfDay(value)
        return when {
            localTime.second != 0 -> localTime.format(FORMATTER_WITH_SECONDS)
            localTime.minute != 0 -> localTime.format(FORMATTER_WITH_MINUTES)
            else -> localTime.format(FORMATTER_WITH_HOURS)
        }
    }

    companion object {
        private val FORMATTER_WITH_HOURS = DateTimeFormatter.ofPattern("h a")
        private val FORMATTER_WITH_MINUTES = DateTimeFormatter.ofPattern("h:mm a")
        private val FORMATTER_WITH_SECONDS = DateTimeFormatter.ofPattern("h:mm:ss a")
    }

    override fun toString(): String {
        return "${type.name} $value"
    }

    override fun compareTo(other: OpeningHours): Int {
        return compareBy<OpeningHours> { it.value }
            .thenBy { it.type }
            .compare(this, other)
    }
}
