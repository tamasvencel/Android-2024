package main

class ItemRepository {
    private val items = mutableListOf<Item>()

    init {
        items.add(Item("What is Kotlin?", listOf("A programming language", "A database", "A game"), 0))
        items.add(Item("Which company developed Kotlin?", listOf("Microsoft", "JetBrains", "Google"), 1))
        items.add(Item("What file extension is used for Kotlin files?", listOf(".kt", ".java", ".py"), 0))
        items.add(Item("Which of these is a Kotlin feature?", listOf("Null Safety", "Pointers", "Macros"), 0))
        items.add(Item("Can Kotlin run on JVM?", listOf("Yes", "No"), 0))
        items.add(Item("What is the primary use of the 'val' keyword in Kotlin?", listOf("To declare a mutable variable", "To declare an immutable variable", "To declare a function"), 1))
        items.add(Item("Which of the following is the correct way to define a function in Kotlin?", listOf("def myFunction()", "func myFunction()", "fun myFunction()"), 2))
        items.add(Item("Which of the following is not a Kotlin collection?", listOf("Array", "Vector", "List"), 1))
        items.add(Item("How does Kotlin handle null safety?", listOf("By allowing null in all variables", "By using nullable types with '?'", "By using null pointers explicitly"), 1))
        items.add(Item("What is the default visibility modifier in Kotlin?", listOf("public", "private", "internal"), 0))
        items.add(Item("Which keyword is used to inherit a class in Kotlin?", listOf("inherit", "extend", "open"), 2))
        items.add(Item("Which of the following can be used to create a singleton in Kotlin?", listOf("object", "class", "struct"), 0))
        items.add(Item("What is the correct way to declare an array in Kotlin?", listOf("val arr = Array(5)", "val arr = arrayOf(1, 2, 3)", "val arr = new Array()"), 1))
        items.add(Item("Which function is used to print to the console in Kotlin?", listOf("System.out.println()", "console.log()", "println()"), 2))
        items.add(Item("Which of the following is a Kotlin coroutine builder?", listOf("launch", "run", "asyncTask"), 0))
    }

    fun randomItem(): Item {
        return items.random()
    }

    fun save(item: Item): Unit {
        items.add(item)
    }

    fun size(): Int {
        return items.size
    }

    fun getAllItems(): List<Item> {
        return items
    }
}