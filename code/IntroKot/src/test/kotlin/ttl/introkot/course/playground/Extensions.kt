package ttl.introkot.course.playground

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

class BasicExtensions {
    //Change this to an extension function on List
    fun oldsum(list: List<Int>): Int {
        var result = 0
        for (i in list) {
            result += i
        }
        return result
    }

    //Solution
    fun <T : Number> MutableList<T>.sum(): Int {
        var result = 0
        for (i in this) {
            result += i.toInt()
        }
        return result
    }

    @Test
    fun testSums() {
        val oldsum = oldsum(listOf(1, 2, 3))
        println(oldsum)    // 6

        val sum = listOf(1, 2, 3).sum()
        println(sum)    // 6

    }

    //Overriding String methods
    @Test
    fun testStringExtensions() {
        val s: String = "xyz.com"
        val arr = s.split(".")
        s.toLowerCase()

    }
}
