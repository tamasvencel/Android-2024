package labor1.main

fun groupAnagrams(strs: Array<String>): List<List<String>> {
    val finalAnagrams = mutableListOf<List<String>>()
    for (str2 in strs) {
        val anagrams = mutableListOf<String>()
        for (str1 in strs) {
            if (str1.lowercase().all {str2.lowercase().contains(it.lowercase())}) {
                anagrams.add(str1.lowercase())
            }
        }
        if (!finalAnagrams.contains(anagrams)) {
            finalAnagrams.add(anagrams)
        }
    }
    return finalAnagrams
}

fun main() {
    val anagrams = groupAnagrams(listOf("eat", "tEa", "Tan", "atE", "NAT",
            "bat").toTypedArray())
    println(anagrams)

//    Assert.assertTrue(anagrams.contains(listOf("eat", "tea", "ate")))
//    Assert.assertTrue(anagrams.contains(listOf("tan", "nat")))
//    Assert.assertTrue(anagrams.contains(listOf("bat")))
}