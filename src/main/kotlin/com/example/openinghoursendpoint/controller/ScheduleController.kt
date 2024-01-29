package com.example.openinghoursendpoint.controller

import com.example.openinghoursendpoint.service.InvalidScheduleDataException
import com.example.openinghoursendpoint.service.ScheduleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ScheduleController(private val scheduleService: ScheduleService) {

    @PostMapping("/schedule")
    fun getHumanReadableSchedule(@RequestBody openingHoursJson: String): ResponseEntity<String> {

        return try {
            val schedule = scheduleService.buildScheduleFromJson(openingHoursJson)
            ResponseEntity.ok(schedule.getHumanReadable())
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Input data.json format: ${e.message}")
        } catch (e: InvalidScheduleDataException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("${e.message}")
        }
    }
}
