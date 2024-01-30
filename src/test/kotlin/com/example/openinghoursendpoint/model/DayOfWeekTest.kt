package com.example.openinghoursendpoint.model

import com.example.openinghoursendpoint.model.DayOfWeek
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DayOfWeekTest {

    @Test
    fun testCompareTo() {
        Assertions.assertThat(DayOfWeek.MONDAY).isLessThan(DayOfWeek.TUESDAY)
        Assertions.assertThat(DayOfWeek.TUESDAY).isLessThan(DayOfWeek.WEDNESDAY)
        Assertions.assertThat(DayOfWeek.WEDNESDAY).isLessThan(DayOfWeek.THURSDAY)
        Assertions.assertThat(DayOfWeek.THURSDAY).isLessThan(DayOfWeek.FRIDAY)
        Assertions.assertThat(DayOfWeek.FRIDAY).isLessThan(DayOfWeek.SATURDAY)
        Assertions.assertThat(DayOfWeek.SATURDAY).isLessThan(DayOfWeek.SUNDAY)
    }
}