package main.problem2

fun main() {
    val name = "John Smith"
    println(name.monogram())
    println()

    val fruits = listOf("apple", "pear", "strawberry", "melon")
    println(fruits.joinBySeparator("#"))
    println()

    println(fruits.longestString())

}

fun String.monogram(): String {
    return this.split(" ").map { it.first() }.joinToString("")
}

fun List<String>.joinBySeparator(separator: String): String {
    return this.joinToString(separator)
}

fun List<String>.longestString(): String {
    return this.maxByOrNull { it.length } ?: ""
}