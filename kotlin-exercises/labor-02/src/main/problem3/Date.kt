package main.problem3

import java.time.LocalDate
import kotlin.random.Random

data class Date(
    var year: Int = LocalDate.now().year,
    var month: Int = LocalDate.now().monthValue,
    var day: Int = LocalDate.now().dayOfMonth
) : Comparable<Date> {
    override fun compareTo(other: Date): Int {
        return when {
            this.year != other.year -> this.year - other.year
            this.month != other.month -> this.month - other.month
            else -> this.day - other.day
        }
    }
}

fun Date.isLeapYear(): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun Date.isValid(): Boolean {
    return try {
        LocalDate.of(year, month, day)
        true
    } catch(e: Exception) {
        false
    }
}

fun generateRandomDate(): Date {
    val year = Random.nextInt(1900, 2101)
    val month = Random.nextInt(1, 13)
    val day = Random.nextInt(1, 32)

    return Date(year, month, day)
}