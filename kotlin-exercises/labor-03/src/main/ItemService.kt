package main

class ItemService(private val itemRepository: ItemRepository) {
    fun selectRandomItems(count: Int): List<Item> {
        val allItems = itemRepository.getAllItems()
        return allItems.shuffled().take(count)
    }
}