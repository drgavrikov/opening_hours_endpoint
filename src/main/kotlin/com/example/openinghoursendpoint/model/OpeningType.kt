package com.example.openinghoursendpoint.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OpeningType: Comparable<OpeningType> {
    @SerialName("close") CLOSE,
    @SerialName("open") OPEN,
}
