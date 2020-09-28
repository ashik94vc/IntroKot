package ttl.introkot.course.playground

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */
class KotLoops {

    @Test
    fun forTest() {
        val i = 0xFFFF
        val ch = 'a'
        println("first i=$i, a=${ch.toInt()}")
        val x = ch + i

        //In kotlin, 'ch + i' calls Char.plus(int)
        //This will do the addition, then return the
        //lower 2 bytes as a char
        for(ch in "abc") {
            print(ch + i)
            print(", ")
            println(ch.toInt() + i)
        }
    }

    @Test
    fun testStringRange() {
        val y = "aa".."az"
        if("ab" in "aa".."az") {
            println("Yup")
        }
    }
}
