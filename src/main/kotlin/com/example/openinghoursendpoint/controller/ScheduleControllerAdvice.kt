package com.example.openinghoursendpoint.controller

import com.example.openinghoursendpoint.service.InvalidScheduleDataException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ScheduleControllerAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Input json format: ${ex.message}")
    }

    @ExceptionHandler(InvalidScheduleDataException::class)
    fun handleInvalidScheduleDataException(ex: InvalidScheduleDataException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("${ex.message}")
    }
}
