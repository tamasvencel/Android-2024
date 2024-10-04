package main.extra

interface TextGenerator {
    fun trainFromText(trainingText: String)
    fun trainFromFile(filename: String)
    fun generate(startWords: String, numWords: Int = 10): String
}