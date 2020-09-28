package ttl.introkot.course._4FunctionalFun

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalTime
import java.time.temporal.ChronoUnit

/**
 * @author whynot
 */

/**
 * Extension property on String to give you the middle character,
 * if any.
 *
 * @return If the length of the String is 0 return null.
 *         else return the character at length / 2
 */
val String.middleChar: Char?
    get() {
        return if(length == 0) {
            null
        } else {
            this[length / 2]
        }
    }

class TestSimpleExtension {
    @Test
    fun testMiddleChar() {
        val str = "abc";

        val str2: String? = null

        val mid = str.middleChar
        println("str: $str, mid: $mid")
    }
}
