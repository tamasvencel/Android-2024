package main.problem1

fun main(args: Array<String>) {
//    val dict: IDictionary = ListDictionary("src/main/dict.txt")
//    println("Number of words: ${dict.size()}")
//    var word: String?
//    println(System.getProperty("user.dir"))
//    while(true){
//        print("What to find? ")
//        word = readLine()
//        if( word.equals("quit")){
//            break
//        }
//        println("Result: ${word?.let { dict.find(it) }}")
//    }

    val arrayListDict: IDictionary = DictionaryProvider.createDictionary(DictionaryType.ARRAY_LIST)
    val treeSetDict: IDictionary = DictionaryProvider.createDictionary(DictionaryType.TREE_SET)
    val hashSetDict: IDictionary = DictionaryProvider.createDictionary(DictionaryType.HASH_SET)

    println("ArrayList contains asd: ${arrayListDict.find("asd")}")
    println("ArrayList contains aals: ${arrayListDict.find("aals")}")
    println("ArrayList size: ${arrayListDict.size()}")
    println()
    println("TreeSet contains asd: ${treeSetDict.find("asd")}")
    println("TreeSet contains aals: ${treeSetDict.find("aals")}")
    println("TreeSet size: ${treeSetDict.size()}")
    println()
    println("HashSet contains asd: ${hashSetDict.find("asd")}")
    println("HashSet contains aals: ${hashSetDict.find("aals")}")
    println("HashSet size: ${hashSetDict.size()}")
}