package com.example.openinghoursendpoint.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DayOfWeek : Comparable<DayOfWeek> {
    @SerialName("monday") MONDAY,
    @SerialName("tuesday") TUESDAY,
    @SerialName("wednesday") WEDNESDAY,
    @SerialName("thursday") THURSDAY,
    @SerialName("friday") FRIDAY,
    @SerialName("saturday") SATURDAY,
    @SerialName("sunday") SUNDAY;

    fun previous(): DayOfWeek {
        val days = entries
        val index = (ordinal - 1 + days.size) % days.size
        return days[index]
    }
}
