package ttl.introkot.course._5CollectionOps

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

class CollectionOpChoices {
    /**
     * Use count instead of filter and size
     */
    @Test
    fun testLibraryFunctions() {
        val list = listOf("one", "two", "threeee")
        val s = list.filter { it.length > 3 }.size

        //Can be replaced with
        val s2 = list.count { it.length > 3 }

        println("s: $s, s2: $s2")
    }

    @Test
    fun testSortedByAndReveresed() {
        val list = listOf("one", "two", "threeee")
        val s = list.sortedBy { it }.reversed()

        //Can be replaced with
        val s2 = list.sortedByDescending { it }

        println("s: $s, s2: $s2")
    }


    @Test
    fun testMapNotNull() {
        data class User(val name: String?, val age: Int)
        val list = listOf(User("Joe", 10), User(null, 22))
        val s = list.map { it.name }.filterNotNull()

        //Can be replaced with
        val s2 = list.mapNotNull { it.name }

        println("s: $s, s2: $s2")
    }

    @Test
    fun testMapComputeIfAbsent() {
        data class User(val name: String?, val age: Int)
        val users = listOf(User("Joe", 10), User(null, 22), User("Sarla", 10))

        val map1 = mutableMapOf<Int, MutableList<User>>()

        var nextId = 1
        for(user in users) {
            //Check then act anti pattern
            var uList = map1.get(user.age)
            if(uList == null) {
                uList = mutableListOf<User>()
                map1.put(user.age, uList)
            }
            uList.add(user)
        }

        //Can be replaced with
        val map2 = mutableMapOf<Int, MutableList<User>>()
        for(user in users) {
            val uList = map2.computeIfAbsent(user.age) {
                age -> mutableListOf<User>()
            }
            uList += user
        }

        //Or Can be replaced with
        val map3 = mutableMapOf<Int, MutableList<User>>()
        for(user in users) {
            val uList = map3.getOrPut(user.age) {
                mutableListOf<User>()
            }
            uList += user
        }

        //Or, of course with a groupBy
        val map4 = users.groupBy{it.age}

        println("map1: $map1, map2: $map2, map3: $map3, map4: $map4")
    }
}