package main.problem1

import java.io.File

class TreeSetDictionary(filepath: String): IDictionary {
    private val words: MutableSet<String> = java.util.TreeSet()

    init {
        val file = File(filepath)
        if (file.exists()) {
            file.forEachLine {
                add(it)
            }
        }
    }

    override fun add(word: String): Boolean {
        return words.add(word)
    }

    override fun find(word: String): Boolean {
        return words.contains(word)
    }

    override fun size(): Int {
        return words.size
    }
}