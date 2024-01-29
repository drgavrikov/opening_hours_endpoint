package com.example.openinghoursendpoint

import com.example.openinghoursendpoint.model.OpeningHours
import com.example.openinghoursendpoint.model.OpeningType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OpeningHoursTest {

    @Test
    fun testConvertTime() {
        val openingHours1 = OpeningHours(OpeningType.OPEN, 32400)
        val openingHours2 = OpeningHours(OpeningType.CLOSE, 37800)
        val openingHours3 = OpeningHours(OpeningType.CLOSE, 37840)
        val openingHours4 = OpeningHours(OpeningType.OPEN, 64800)
        val openingHours5 = OpeningHours(OpeningType.CLOSE, 65400)
        val openingHours6 = OpeningHours(OpeningType.OPEN, 65430)

        assertThat(openingHours1.convertTime()).isEqualTo("9 AM")
        assertThat(openingHours2.convertTime()).isEqualTo("10:30 AM")
        assertThat(openingHours3.convertTime()).isEqualTo("10:30:40 AM")
        assertThat(openingHours4.convertTime()).isEqualTo("6 PM")
        assertThat(openingHours5.convertTime()).isEqualTo("6:10 PM")
        assertThat(openingHours6.convertTime()).isEqualTo("6:10:30 PM")

        assertThat(openingHours1).isLessThan(openingHours2)
        assertThat(openingHours2).isLessThan(openingHours3)
        assertThat(openingHours3).isLessThan(openingHours4)
        assertThat(openingHours4).isLessThan(openingHours5)
        assertThat(openingHours5).isLessThan(openingHours6)
    }
}
