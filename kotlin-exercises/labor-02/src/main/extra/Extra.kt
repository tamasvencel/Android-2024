package main.extra

fun main() {
    val generator = SimpleTextGenerator()
    val text = "Now is not the time for sleep, now is the time for party!"
    generator.trainFromText(text)
    generator.printData()

    println("\nGenerated text: ${generator.generate("now is", 6)}")
}
