package main.problem3

fun main() {
    val validDates = mutableListOf<Date>()

    while (validDates.size < 10) {
        val randomDate = generateRandomDate()
        if (randomDate.isValid()) {
            validDates.add(randomDate)
        } else {
            println(randomDate)
        }
    }
    println()

    // print valid dates
    println("Valid dates: ")
    validDates.forEach{ println(it) }

    // sort the list by natural ordering
    validDates.sort()
    println("\nSorted valid dates: ")
    validDates.forEach { println(it) }

    // reverse the sorted list
    validDates.reverse()
    println("\nReversed sorted valid dates: ")
    validDates.forEach { println(it) }

    // sort with custom comparator
    val customComparator = Comparator<Date> {
        d1, d2 ->
            when {
                d1.day != d2.day -> d1.day - d2.day
                d1.month != d2.month -> d1.month - d2.month
                else -> d1.year - d2.year
            }
    }

    validDates.sortWith(customComparator)
    println("\nCustom sorted valid dates (by day, month, year):")
    validDates.forEach { println(it) }
}