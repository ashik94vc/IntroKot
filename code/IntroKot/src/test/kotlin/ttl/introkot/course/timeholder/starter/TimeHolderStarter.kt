package ttl.introkot.course.timeholder.starter

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalTime

/**
 * A Class to represent Time in Hours, Minutes and Seconds.  It implements Comparable
 * so we can compare two TimeHolder objects using symbols.
 * e.g. time1 > time2.  Very cool.
 */
data class TimeHolder(val hour: Int, val min: Int, val second: Int)

/**
 * Utility function to addTime based on the TimePart.  The default
 * amount to add is 1.  We cheat here and use LocalTime to do the
 * actual calculation
 */
fun TimeHolder.addTime(timePart: TimePart, amount: Long = 1): TimeHolder {
    val lt = LocalTime.of(hour, min, second, 0)
    val result = when (timePart) {
        TimePart.HOUR -> lt.plusHours(amount)
        TimePart.MINUTE -> lt.plusMinutes(amount)
        TimePart.SECOND -> lt.plusSeconds(amount)
    }

    return TimeHolder(result.hour, result.minute, result.second)
}

enum class TimePart {
    HOUR,
    MINUTE,
    SECOND
}

/**
 * This class is used to work with multiple TimeParts.
 * e.g, to add 2 hours we could say
 * val newTime = oldTime + HOUR * 2
 * This means we need a multiply operator on TimePart, which
 * needs to return something that can hold both the TimePart and
 * the multiplication value.  That's what this class is for.
 * The multiply operator for TimePart returns a TIRepeat
 * object.  This then gets sent to the plus operator on TimeHolder
 * where we now have both values to work with.
 */
class TIRepeat(val timePart: TimePart, val numTimes: Long)

/**
 * This is used to handle invocations like 'YEAR * 2'
 * It simply saves the state in a TIRepeat object, which is then
 * used as mentioned above.
 */
operator fun TimePart.times(numTimes: Long): TIRepeat = TIRepeat(this, numTimes)


/**
 * @author whynot
 */

class TestTimeHolder {
//    @Test
//    fun testInGreaterLessThan() {
//        val t1 = TimeHolder(10, 10, 10)
//        val t2 = TimeHolder(10, 10, 20)
//
//        Assertions.assertTrue(t2 > t1)
//
//        val t4 = TimeHolder(10, 10, 10)
//        Assertions.assertTrue(t4 == t1)
//        Assertions.assertTrue(t4 < t2)
//    }



//    @Test
//    fun testIteration() {
//        val t1 = TimeHolder(10, 10, 10)
//        val t2 = TimeHolder(10, 10, 20)
//
//        var count = 0
//        for (t in t1..t2) {
//            println("t: $t")
//            count++
//        }
//        Assertions.assertEquals(11, count)
//    }
//
//    @Test
//    fun testPlus() {
//        val t1 = TimeHolder(10, 10, 10)
//        val t2 = t1 + TimePart.MINUTE
//
//        Assertions.assertTrue(t2 == TimeHolder(10, 11, 10))
//    }
//
//
//    @Test
//    fun testPlusMinuteChange() {
//        val t1 = TimeHolder(10, 59, 10)
//        val t2 = t1 + TimePart.MINUTE
//
//        Assertions.assertTrue(t2 == TimeHolder(11, 0, 10))
//    }
//
//    @Test
//    fun testPlusWithMultiple() {
//        val t1 = TimeHolder(10, 59, 10)
//        val t2 = t1 + TimePart.MINUTE * 10
//
//        Assertions.assertTrue(t2 == TimeHolder(11, 9, 10))
//    }
//
//    @Test
//    fun testMinus() {
//        val t1 = TimeHolder(10, 10, 10)
//        val t2 = t1 - TimePart.SECOND
//
//        Assertions.assertTrue(t2 == TimeHolder(10, 10, 9))
//    }
//
//    @Test
//    fun testMinusChangeMinute() {
//        val t1 = TimeHolder(10, 10, 0)
//        val t2 = t1 - TimePart.SECOND
//
//        Assertions.assertTrue(t2 == TimeHolder(10, 9, 59))
//    }
//
//    @Test
//    fun testMinusChangeMinuteMultiple() {
//        val t1 = TimeHolder(10, 10, 5)
//        val t2 = t1 - TimePart.SECOND * 10
//
//        Assertions.assertTrue(t2 == TimeHolder(10, 9, 55))
//        println("t2: $t2")
//    }
//
//    @Test
//    fun testUntil() {
//        val t1 = TimeHolder(10, 10, 5)
//        val t2 = TimeHolder(10, 10, 20)
//
//        val range = t1 until t2
//        Assertions.assertEquals(range.end, TimeHolder(10, 10, 20))
//        Assertions.assertEquals(range.realEnd, TimeHolder(10, 10, 19))
//    }
//
//    @Test
//    fun testIterationUntil() {
//        val t1 = TimeHolder(10, 10, 10)
//        val t2 = TimeHolder(10, 10, 20)
//
//        var count = 0
//        for (t in t1 until t2) {
//            println("t: $t")
//            count++
//        }
//        Assertions.assertEquals(10, count)
//    }
//
//    @Test
//    fun testIterationStep() {
//        val t1 = TimeHolder(10, 10, 11)
//        val t2 = TimeHolder(10, 10, 20)
//
//        var count = 0
//        for (t in t1..t2 step 3) {
//            println("t: $t")
//            count++
//        }
//        Assertions.assertEquals(4, count)
//
//        count = 0
//        for (t in t1 until t2 step 3) {
//            println("t: $t")
//            count++
//        }
//        Assertions.assertEquals(3, count)
//    }
//
//    @Test
//    fun testTimePart() {
//        val t1 = TimeHolder(10, 10, 11)
//        val t2 = TimeHolder(10, 15, 20)
//
//        var count = 0
//        for (t in t1..t2 tp TimePart.MINUTE) {
//            println("t: $t")
//            count++
//        }
//        Assertions.assertEquals(6, count)
//
//        count = 0
//        for (t in t1 until t2 tp TimePart.MINUTE) {
//            println("t: $t")
//            count++
//        }
//        Assertions.assertEquals(5, count)
//    }
//
//    @Test
//    fun testTimePartWithStep() {
//        val t1 = TimeHolder(10, 10, 11)
//        val t2 = TimeHolder(20, 16, 20)
//
//        var count = 0
//        for (t in t1..t2 tp TimePart.HOUR step 2) {
//            println("t: $t")
//            count++
//        }
//        Assertions.assertEquals(6, count)
//
//        count = 0
//        for (t in t1 until t2 tp TimePart.MINUTE step 2) {
//            count++
//        }
//        Assertions.assertEquals(303, count)
//    }
//
//    @Test
//    fun testWithIndex() {
//        val t1 = TimeHolder(10, 10, 11)
//        val t2 = TimeHolder(10, 16, 20)
//
//        val r = t1..t2
//        var count = 0
//        var validIndexes = mutableSetOf(0, 2, 4, 6)
//        for ((index, t) in ((t1..t2) tp TimePart.MINUTE step 2).withIndex()) {
//            println("$index: $t")
//            assertTrue(index in validIndexes)
//            validIndexes.remove(index)
//            count++
//        }
//        Assertions.assertEquals(4, count)
//        assertEquals(0, validIndexes.size);
//
//        count = 0
//        validIndexes = mutableSetOf(0, 3)
//        for (t in (t1 until t2 tp TimePart.MINUTE step 3).withIndex()) {
//            println("t: $t")
//            assertTrue(t.first in validIndexes)
//            validIndexes.remove(t.first)
//            count++
//        }
//        Assertions.assertEquals(2, count)
//        assertEquals(0, validIndexes.size);
//    }
}
