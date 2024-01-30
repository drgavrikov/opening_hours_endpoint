package com.example.openinghoursendpoint

import com.example.openinghoursendpoint.model.OpeningHours
import com.example.openinghoursendpoint.model.OpeningType
import com.example.openinghoursendpoint.model.Schedule
import java.time.DayOfWeek

const val VALID_SCHEDULE_JSON = "valid_schedule.json"
const val HUMAN_READABLE_SCHEDULE = "human-readable-schedule.txt"

const val INVALID_HOURS_SCHEDULE_JSON = "invalid_hours_schedule.json"
const val INVALID_HOURS_SCHEDULE_EXCEPTION_MESSAGE =
    "Exception: Opening hours for the following days exceed max value: \n" +
    "- MONDAY: 88000, 98800\n" +
    "- TUESDAY: 95000, 97800"

const val INVALID_SEQUENCE_SCHEDULE_JSON = "invalid_sequence_schedule.json"
const val INVALID_SEQUENCE_EXCEPTION_MESSAGE = "Exception: Two consecutive OpeningHours with the same type in MONDAY, TUESDAY"

const val INVALID_FIRST_CLOSE_SCHEDULE_JSON = "invalid_first_close_schedule.json"
const val INVALID_FIRST_CLOSE_EXCEPTION_MESSAGE = "The first Hour should not be of type Close in the subsequent days: TUESDAY"

const val INVALID_LAST_OPEN_SCHEDULE_JSON = "invalid_last_open_schedule.json"
const val INVALID_LAST_OPEN_EXCEPTION_MESSAGE = "The last Hour should not be of type Open in the subsequent days: MONDAY"

val VALID_SCHEDULE = Schedule(
    mapOf(
        DayOfWeek.MONDAY to listOf(
            OpeningHours(type = OpeningType.OPEN, value = 36000),
            OpeningHours(type = OpeningType.CLOSE, value = 40800)
        ),
        DayOfWeek.TUESDAY to listOf(
            OpeningHours(type = OpeningType.OPEN, value = 36000),
            OpeningHours(type = OpeningType.CLOSE, value = 64800)
        ),
        DayOfWeek.WEDNESDAY to emptyList(),
        DayOfWeek.THURSDAY to listOf(
            OpeningHours(type = OpeningType.OPEN, value = 37800),
            OpeningHours(type = OpeningType.CLOSE, value = 64800)
        ),
        DayOfWeek.FRIDAY to listOf(
            OpeningHours(type = OpeningType.OPEN, value = 46400),
            OpeningHours(type = OpeningType.CLOSE, value = 3600),
        ),
        DayOfWeek.SATURDAY to listOf(
            OpeningHours(type = OpeningType.OPEN, value = 47000),
            OpeningHours(type = OpeningType.CLOSE, value = 3600),
        ),
        DayOfWeek.SUNDAY to listOf(
            OpeningHours(type = OpeningType.OPEN, value = 43200),
            OpeningHours(type = OpeningType.CLOSE, value = 48600)
        )
    )
)
