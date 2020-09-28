package ttl.introkot.course.playground.timeholder

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ttl.introkot.course.playground.timeholder.LocalTimePart.*
import java.time.Duration.ofMinutes
import java.time.LocalTime
import kotlin.coroutines.coroutineContext

/**
 * A Class to represent Time in Hours, Minutes and Seconds.  It implements Comparable
 * so we can compare two LocalTimeHolder objects using symbols.
 * e.g. time1 > time2.  Very cool.
 */
data class LocalTimeHolder(val myTime: LocalTime) : Comparable<LocalTimeHolder> {

    constructor(hour: Int, min: Int, second: Int) : this(LocalTime.of(hour, min, second, 0))

    override fun compareTo(other: LocalTimeHolder): Int {
        var ld1 = myTime
        var ld2 = other.myTime

        val ret = ld1.compareTo(ld2)
        return ret
    }
}

/**
 * Utility function to addTime based on the LocalTimePart.  The default
 * amount to add is 1.  We cheat here and use LocalTime to do the
 * actual calculation
 */
fun LocalTimeHolder.addTime(timePart: LocalTimePart, amount: Long = 1): LocalTimeHolder {
    val lt = myTime
    val result = when (timePart) {
        HOUR -> lt.plusHours(amount)
        MINUTE -> lt.plusMinutes(amount)
        SECOND -> lt.plusSeconds(amount)
    }

    return LocalTimeHolder(result.hour, result.minute, result.second)
}

//Add 1 unit of the specified LocalTimePart to this date
operator fun LocalTimeHolder.plus(timePart: LocalTimePart): LocalTimeHolder = addTime(timePart)

operator fun LocalTimeHolder.minus(timePart: LocalTimePart): LocalTimeHolder = addTime(timePart, -1)

//This one is used with LTRepeat below to allow adding multiple units of a TimePart
operator fun LocalTimeHolder.plus(tiRepeat: LTRepeat): LocalTimeHolder = addTime(tiRepeat.timePart, tiRepeat.numTimes)

operator fun LocalTimeHolder.minus(tiRepeat: LTRepeat): LocalTimeHolder = addTime(tiRepeat.timePart, -tiRepeat.numTimes)

/**
 * This function is called for the '..' operator
 * e.g LocalTimeHolder(20, 2, 2)..LocalTimeHolder(20, 20, 20)
 * We just return an LocalTimeRange object here
 */
operator fun LocalTimeHolder.rangeTo(other: LocalTimeHolder): LocalTimeRange {
    val hr = LocalTimeRange(this, other)
    return hr
}

/**
 * This function is called for the 'until' infix function
 * e.g. t1 until t2.  It's end is 1 stepPart before the end time
 */
infix fun LocalTimeHolder.until(other: LocalTimeHolder): LocalTimeRange {
    val hr = LocalTimeRange(this, other)
    hr.endInclusive = false
    return hr
}

/**
 * Set the step size.  The actual value that will be
 * stepped depends on the stepPart setting, set
 * by the 'tp' function below.
 */
infix fun LocalTimeRange.step(stepSize: Long): LocalTimeRange {
    this.step = stepSize
    return this
}

/**
 * To specify the LocalTimePart to use for increment when stepping
 * We return 'this' from here, rather than create a new object.
 * Just because.
 */
infix fun LocalTimeRange.tp(timePart: LocalTimePart): LocalTimeRange {
    stepPart = timePart
    return this
}

fun LocalTimeRange.withIndex(): Iterable<Pair<Int, LocalTimeHolder>> {
    val origIterator = iterator()
    var index = 0
    val iterable = object : Iterable<Pair<Int, LocalTimeHolder>> {
        override fun iterator(): Iterator<Pair<Int, LocalTimeHolder>> {
            val newIterator = object : Iterator<Pair<Int, LocalTimeHolder>> {
                override fun hasNext(): Boolean {
                    return origIterator.hasNext()
                }

                override fun next(): Pair<Int, LocalTimeHolder> {
                    val p = Pair(index, origIterator.next())
                    index += step.toInt()
                    return p
                }
            }

            return newIterator;
        }
    }

    return iterable;
}

/**
 * Class to hold state for a range of Time
 */
class LocalTimeRange(val start: LocalTimeHolder, val end: LocalTimeHolder, var step: Long = 1) : Iterable<LocalTimeHolder> {
    var realEnd = end
    //Are we including the end value?
    var endInclusive = true
        set(value) {
            realEnd = end - stepPart
            field = value
        }
    //This is the part we use to decide
    //what field we use for stepping
    var stepPart = SECOND
        set(value) {
            //reset the end only if we are not including it
            if (!endInclusive) {
                realEnd = end - value
            }
            field = value
        }

    override fun iterator(): Iterator<LocalTimeHolder> =
            object : Iterator<LocalTimeHolder> {
                var current = start
                override fun hasNext(): Boolean {
                    return current <= realEnd
                }

                override fun next(): LocalTimeHolder {
                    val obj = current
                    current += stepPart * step
                    return obj
                }
            }
}

/**
 * This function is called when the 'in' operator is used.
 * e.g. 'LocalTimeHolder(20, 2, 2) in someTimeRange' will result in
 * a call to someTimeRange.contains(timeObject)
 */
operator fun LocalTimeRange.contains(timeHolder: LocalTimeHolder): Boolean {
    return timeHolder >= start && timeHolder <= end
}

/**
 * This allows is to iterate over an LocalTimeRange.
 * so we can write for(t in LocalTimeRange) {...}
 */
operator fun LocalTimeRange.iterator(): Iterator<LocalTimeHolder> =
        object : Iterator<LocalTimeHolder> {
            var current = start
            override fun hasNext(): Boolean {
                return current <= realEnd
            }

            override fun next(): LocalTimeHolder {
                val obj = current
                current += stepPart * step
                return obj
            }
        }


enum class LocalTimePart {
    HOUR,
    MINUTE,
    SECOND
}

/**
 * This class is used to work with multiple LocalTimeParts.
 * e.g, to add 2 hours we could say
 * val newTime = oldTime + HOUR * 2
 * This means we need a multiply operator on LocalTimePart, which
 * needs to return something that can hold both the LocalTimePart and
 * the multiplication value.  That's what this class is for.
 * The multiply operator for LocalTimePart returns a LTRepeat
 * object.  This then gets sent to the plus operator on LocalTimeHolder
 * where we now have both values to work with.
 */
class LTRepeat(val timePart: LocalTimePart, val numTimes: Long)

/**
 * This is used to handle invocations like 'YEAR * 2'
 * It simply saves the state in a LTRepeat object, which is then
 * used as mentioned above.
 */
operator fun LocalTimePart.times(numTimes: Long): LTRepeat = LTRepeat(this, numTimes)


/**
 * @author whynot
 */

class TestLocalTimeHolder {
    @Test
    fun testInGreaterLessThan() {
        val t1 = LocalTimeHolder(10, 10, 10)
        val t2 = LocalTimeHolder(10, 10, 20)

        Assertions.assertTrue(t2 > t1)

        val t4 = LocalTimeHolder(10, 10, 10)
        Assertions.assertTrue(t4 == t1)
        Assertions.assertTrue(t4 < t2)
    }

    @Test
    fun testInNotIn() {
        val t1 = LocalTimeHolder(10, 10, 10)
        val t2 = LocalTimeHolder(10, 10, 20)

        val t3 = LocalTimeHolder(10, 10, 11)
        Assertions.assertTrue(t3 in t1..t2)

        val t4 = LocalTimeHolder(9, 10, 10)
        Assertions.assertTrue(t4 !in t1..t2)
    }


    @Test
    fun testIteration() {
        val t1 = LocalTimeHolder(10, 10, 10)
        val t2 = LocalTimeHolder(10, 10, 20)

        var count = 0
        for (t in t1..t2) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(11, count)
    }

    @Test
    fun testPlus() {
        val t1 = LocalTimeHolder(10, 10, 10)
        val t2 = t1 + MINUTE

        Assertions.assertTrue(t2 == LocalTimeHolder(10, 11, 10))
    }


    @Test
    fun testPlusMinuteChange() {
        val t1 = LocalTimeHolder(10, 59, 10)
        val t2 = t1 + MINUTE

        Assertions.assertTrue(t2 == LocalTimeHolder(11, 0, 10))
    }

    @Test
    fun testPlusWithMultiple() {
        val t1 = LocalTimeHolder(10, 59, 10)
        val t2 = t1 + MINUTE * 10

        var lt = LocalTime.now()
        var lt2 = lt + ofMinutes(10)

        Assertions.assertTrue(t2 == LocalTimeHolder(11, 9, 10))
    }

    @Test
    fun testMinus() {
        val t1 = LocalTimeHolder(10, 10, 10)
        val t2 = t1 - LocalTimePart.SECOND

        Assertions.assertTrue(t2 == LocalTimeHolder(10, 10, 9))
    }

    @Test
    fun testMinusChangeMinute() {
        val t1 = LocalTimeHolder(10, 10, 0)
        val t2 = t1 - LocalTimePart.SECOND

        Assertions.assertTrue(t2 == LocalTimeHolder(10, 9, 59))
    }

    @Test
    fun testMinusChangeMinuteMultiple() {
        val t1 = LocalTimeHolder(10, 10, 5)
        val t2 = t1 - LocalTimePart.SECOND * 10

        Assertions.assertTrue(t2 == LocalTimeHolder(10, 9, 55))
        println("t2: $t2")
    }

    @Test
    fun testUntil() {
        val t1 = LocalTimeHolder(10, 10, 5)
        val t2 = LocalTimeHolder(10, 10, 20)

        val range = t1 until t2
        Assertions.assertEquals(range.end, LocalTimeHolder(10, 10, 20))
        Assertions.assertEquals(range.realEnd, LocalTimeHolder(10, 10, 19))
    }

    @Test
    fun testIterationUntil() {
        val t1 = LocalTimeHolder(10, 10, 10)
        val t2 = LocalTimeHolder(10, 10, 20)

        var count = 0
        for (t in t1 until t2) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(10, count)
    }

    @Test
    fun testIterationStep() {
        val t1 = LocalTimeHolder(10, 10, 11)
        val t2 = LocalTimeHolder(10, 10, 20)

        var count = 0
        for (t in t1..t2 step 3) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(4, count)

        count = 0
        for (t in t1 until t2 step 3) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(3, count)
    }

    @Test
    fun testLocalTimePart() {
        val t1 = LocalTimeHolder(10, 10, 11)
        val t2 = LocalTimeHolder(10, 15, 20)

        var count = 0
        for (t in t1..t2 tp MINUTE) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(6, count)

        count = 0
        for (t in t1 until t2 tp LocalTimePart.MINUTE) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(5, count)
    }

    @Test
    fun testLocalTimePartWithStep() {
        val t1 = LocalTimeHolder(10, 10, 11)
        val t2 = LocalTimeHolder(20, 16, 20)

        var count = 0
        for (t in t1..t2 tp HOUR step 2) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(6, count)

        count = 0
        for (t in t1 until t2 tp MINUTE step 2) {
            count++
        }
        Assertions.assertEquals(303, count)
    }

    @Test
    fun testWithIndex() {
        val t1 = LocalTimeHolder(10, 10, 11)
        val t2 = LocalTimeHolder(10, 16, 20)

        val r = t1..t2
        var count = 0
        var validIndexes = mutableSetOf(0, 2, 4, 6)
        for ((index, t) in ((t1..t2) tp MINUTE step 2).withIndex()) {
            println("$index: $t")
            assertTrue(index in validIndexes)
            validIndexes.remove(index)
            count++
        }
        Assertions.assertEquals(4, count)
        assertEquals(0, validIndexes.size);

        count = 0
        validIndexes = mutableSetOf(0, 3)
        for (t in (t1 until t2 tp MINUTE step 3).withIndex()) {
            println("t: $t")
            assertTrue(t.first in validIndexes)
            validIndexes.remove(t.first)
            count++
        }
        Assertions.assertEquals(2, count)
        assertEquals(0, validIndexes.size);
    }

}
