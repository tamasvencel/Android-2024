package main

fun main(args: Array<String>) {
    val itemRepository = ItemRepository()
    val itemService = ItemService(itemRepository)
    val itemController = ItemController(itemService)

    print("Enter the number of questions for the quiz: ")
    val numberOfQuestions = readlnOrNull()?.toIntOrNull() ?: 5 // Default to 5 if input is invalid

    itemController.quiz(numberOfQuestions)
}