package labor1.main

import kotlin.math.sqrt
import kotlin.random.Random

fun main(args: Array<String>) {
    // 1.
    val a = 2
    val b = 3
    println("$a + $b = ${a+b}")
    println()

    // 2. a.
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    for (day in daysOfWeek) {
        println(day)
    }
    println()

    // 2. b.
    daysOfWeek.filter{day -> day.startsWith("T")}.forEach{day -> println(day)}
    println()

    // 2. c.
    daysOfWeek.filter{day -> day.contains("e")}.forEach{day -> println(day)}
    println()

    // 2. d.
    daysOfWeek.filter{day -> day.length == 6}.forEach{day -> println(day)}
    println()

    // 3.
//    print("Enter the starting number of the range: ")
//    val start = readlnOrNull()?.toInt() ?: 0
//    print("Enter the ending number of the range: ")
//    val end = readlnOrNull()?.toInt() ?: 10
//
//    println("Prime numbers between $start and $end are:")
//    for (num in start..end) {
//        if (isPrime(num)) {
//            println(num)
//        }
//    }
    println()

    // 4.
//    val originalMsg = "Hello world!"
//
//    val encodedMsg = messageCoding(originalMsg, ::encode)
//    println("Encoded message: $encodedMsg")
//
//    val decodedMsg = messageCoding(encodedMsg, ::decode)
//    println("Decoded message: $decodedMsg")
//
//    println()

    // 5.
    val numList = listOf(1,6,2,8,3,7,3,8)
    fun printEvenNumbers(numbers: List<Int>) = numList.filter { it % 2 == 0 }.forEach { println(it) }
    printEvenNumbers(numList)
    println()

    // 6.
    // a.
    fun doubleElements(numList: List<Int>) = numList.map{it * 2}.forEach { println(it) }
    doubleElements(numList)
    println()

    // b.
    fun daysOfWeekCapitalized(daysOfWeek: List<String>) = daysOfWeek.map { it.uppercase() }.forEach { println(it) }
    daysOfWeekCapitalized(daysOfWeek)
    println()

    // c.
    fun daysOfWeekFirstLetterCapitalized(daysOfWeek: List<String>) = daysOfWeek.map { it.first().uppercase() + it.substring(1) }.forEach { println(it) }
    daysOfWeekFirstLetterCapitalized(daysOfWeek)
    println()

    // d.
    fun lengthOfDays(daysOfWeek: List<String>) = daysOfWeek.map { it.length }.forEach { println(it) }
    lengthOfDays(daysOfWeek)
    println()

    // e.
    fun avgLengthOfDays(daysOfWeek: List<String>) = println(daysOfWeek.map { it.length }.average())
    avgLengthOfDays(daysOfWeek)
    println()

    // 7.
    // a.
    val mutableDaysOfWeek = daysOfWeek.toMutableList()
    mutableDaysOfWeek.removeIf { it.contains("n") }
    println(mutableDaysOfWeek)
    println()

    // b.
    mutableDaysOfWeek.withIndex().forEach{(i, day) -> println("Item at $i at $day")}
    println()
    // c.
    mutableDaysOfWeek.sort()
    print(mutableDaysOfWeek)
    println()

    // 8.
    // a.
    val randomNumbers = IntArray(10) { Random.nextInt(0, 101)}
    println("Random numbers:")
    randomNumbers.forEach { println(it) }
    println()

    // b.
    println("Random numbers in ascending order:")
    randomNumbers.sorted().forEach{ println(it)}

    // c.
    val containsEvenNumber = randomNumbers.any { it % 2 == 0}
    println("\nContains any even number: $containsEvenNumber")

    // d.
    val areAllNumbersEven = randomNumbers.all { it % 2 == 0 }
    println("\nAll the numbers are even: $areAllNumbersEven\n")

    // e.
    val avgOfRandNums1 = randomNumbers.average()

    var sum = 0;
    randomNumbers.forEach {sum += it }
    val avgOfRandNums2: Double = sum.toDouble() / randomNumbers.size

    println(avgOfRandNums1)
    println()
    println(avgOfRandNums2)

}

fun messageCoding(msg: String, func: (String) -> String): String {
    return func(msg)
}

fun encode(msg: String): String {
    return msg.map { char ->
        if (char.isLetter()) {
            val shift = 1
            if (char.isLowerCase()) {
                ((char.code - 'a'.code + shift) % 26 + 'a'.code).toChar()
            } else {
                ((char.code - 'A'.code + shift) % 26 + 'A'.code).toChar()
            }
        } else {
            char
        }
    }.joinToString("")
}

fun decode(msg: String): String {
    return msg.map { char ->
        if (char.isLetter()) {
            val shift = 1
            if (char.isLowerCase()) {
                ((char - 'a' - shift + 26) % 26 + 'a'.code).toChar()
            } else {
                ((char - 'A' - shift + 26) % 26 + 'A'.code).toChar()
            }
        } else {
            char
        }
    }.joinToString("")
}


fun isPrime(num: Int): Boolean {
    if (num < 2) return false
    for (i in 2..sqrt(num.toDouble()).toInt()) {
        if (num % i == 0) return false
    }

    return true
}