package main.problem1

object DictionaryProvider {
    private var arrayListDictionary: ListDictionary? = null
    private var treeSetDictionary: TreeSetDictionary? = null
    private var hashSetDictionary: HashSetDictionary? = null

    fun createDictionary(type: DictionaryType): IDictionary {
        return when (type) {
            DictionaryType.ARRAY_LIST -> {
                if (arrayListDictionary == null) {
                    arrayListDictionary = ListDictionary("src/main/problem1/dict.txt")
                }
                arrayListDictionary!!
            }
            DictionaryType.TREE_SET -> {
                if (treeSetDictionary == null) {
                    treeSetDictionary = TreeSetDictionary("src/main/problem1/dict.txt")
                }
                treeSetDictionary!!
            }
            DictionaryType.HASH_SET -> {
                if (hashSetDictionary == null) {
                    hashSetDictionary = HashSetDictionary("src/main/problem1/dict.txt")
                }
                hashSetDictionary!!
            }
        }
    }
}