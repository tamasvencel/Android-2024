package main.extra

import java.io.File

class SimpleTextGenerator : TextGenerator {
    private val data = mutableMapOf<String, MutableList<String>>()

    override fun trainFromText(trainingText: String) {
        val words = trainingText.split(" ")
        var prefixFirstWord = ""
        var prefixSecondWord = ""

        for (word in words) {
            val cleanWord = cleanWord(word)

            if (prefixFirstWord.isNotEmpty() && prefixSecondWord.isNotEmpty()) {
                val prefix = "$prefixFirstWord $prefixSecondWord"
                data.computeIfAbsent(prefix) { mutableListOf() }.add(cleanWord)
            }

            prefixFirstWord = prefixSecondWord
            prefixSecondWord = cleanWord
        }

        // Add the final pair to handle the end of the text
        val lastPrefix = "$prefixFirstWord $prefixSecondWord"
        data.computeIfAbsent(lastPrefix) { mutableListOf() }.add("")

        // Ensure unique postfixes for each prefix
        data.forEach { (prefix, postfixes) ->
            data[prefix] = postfixes.distinct().toMutableList()
        }
    }

    override fun trainFromFile(filename: String) {
        val file = File(filename)

        if (!file.exists()) {
            println("Failed to open file: $filename")
            return
        }

        val text = file.readText()
        trainFromText(text)
    }

    override fun generate(startWords: String, numWords: Int): String {
        var result = startWords
        var currentWords = startWords
        var steps = 1

        println()
        println("$steps. $result")

        while (steps < numWords) {
            // Find the possible postfixes for the current prefix
            val foundPostfix = data[currentWords]?.randomOrNull()

            if (!foundPostfix.isNullOrEmpty()) {
                // Append the new word and form the new prefix
                result += " $foundPostfix"
                currentWords = currentWords.substringAfter(' ') + " $foundPostfix"
                steps += 1

                println("$steps. $result")
            } else {
                break
            }
        }

        return result.trim()
    }

    fun printData() {
        data.forEach { (prefix, postfixes) ->
            println("Prefix: $prefix - Postfixes: { ${postfixes.joinToString(", ")} }")
        }
    }

    private fun cleanWord(word: String): String {
        val regex = Regex("[^a-zA-Z'-]+")
        return word.replace(regex, "").lowercase()
    }
}