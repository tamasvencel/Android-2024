package main

class ItemController(private val itemService: ItemService) {
    fun quiz(numberOfQuestions: Int) {
        val selectedItems = itemService.selectRandomItems(numberOfQuestions)
        var numOfCorrectAnswers = 0

        for (item in selectedItems) {
            println(item.question)
            item.answers.forEachIndexed{ index, answer ->
                println("$index: $answer")
            }

            print("Your answer: ")
            val userAnswer = readlnOrNull()?.toIntOrNull()

            if (userAnswer != null && userAnswer == item.correct) {
                println("Correct!\n")
                numOfCorrectAnswers++
            } else {
                println("Wrong! The correct answer is: ${item.answers[item.correct]}\n")
            }
        }

        println("Quiz finished! You got $numOfCorrectAnswers out of $numberOfQuestions correct.")
    }
}