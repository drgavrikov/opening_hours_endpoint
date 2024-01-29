package com.example.openinghoursendpoint

import java.nio.charset.StandardCharsets

fun readResourceFile(fileName: String): String {
    val classLoader = ClassLoader.getSystemClassLoader()
    val resource = classLoader.getResource(fileName)
    return resource?.readText(StandardCharsets.UTF_8) ?: throw IllegalStateException("Unable to $fileName")
}
