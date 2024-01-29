package com.example.openinghoursendpoint

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApplicationLauncher

fun main(args: Array<String>) {
    runApplication<ApplicationLauncher>(*args)
}
