package main.problem1

class ListDictionary(filepath: String): IDictionary {
    private val words: MutableList<String> = mutableListOf()

    init {
        // read the words from the file and add them to the words list
        val file = java.io.File(filepath)
        if (file.exists()) {
            file.forEachLine {
                add(it)
            }
        }
    }

    override fun add(word: String): Boolean {
        return if (!words.contains(word)) {
            words.add(word)
            true
        } else {
            false
        }
    }

    override fun find(word: String): Boolean {
        return words.contains(word)
    }

    override fun size(): Int {
        return words.size
    }
}