package ttl.introkot.course.playground.timeholder

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

/**
 * A Class to represent Time in Hours, Minutes and Seconds.  It implements Comparable
 * so we can compare two TimeHolder objects using symbols.
 * e.g. time1 > time2.  Very cool.
 */
data class TimeHolder(val hour: Int, val min: Int, val second: Int) : Comparable<TimeHolder> {
    override fun compareTo(other: TimeHolder): Int {
        var ld1 = LocalTime.of(hour, min, second, 0)
        var ld2 = LocalTime.of(other.hour, other.min, other.second, 0)

        val ret = ld1.compareTo(ld2)
        return ret
    }
}

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

//Add 1 unit of the specified TimePart to this date
operator fun TimeHolder.plus(timePart: TimePart): TimeHolder = addTime(timePart)

operator fun TimeHolder.minus(timePart: TimePart): TimeHolder = addTime(timePart, -1)

//This one is used with TIRepeat below to allow adding multiple units of a TimePart
operator fun TimeHolder.plus(tiRepeat: TIRepeat): TimeHolder = addTime(tiRepeat.timePart, tiRepeat.numTimes)

operator fun TimeHolder.minus(tiRepeat: TIRepeat): TimeHolder = addTime(tiRepeat.timePart, -tiRepeat.numTimes)

/**
 * This function is called for the '..' operator
 * e.g TimeHolder(20, 2, 2)..TimeHolder(20, 20, 20)
 * We just return an TimeRange object here
 */
operator fun TimeHolder.rangeTo(other: TimeHolder): TimeRange {
    val tr = TimeRange(this, other)
    return tr
}

/**
 * This function is called for the 'until' infix function
 * e.g. t1 until t2.  It's end is 1 stepPart before the end time
 */
infix fun TimeHolder.until(other: TimeHolder): TimeRange {
    val tr = TimeRange(this, other)
    //This will call the set() method on the property
    tr.endInclusive = false
    return tr
}

/**
 * Set the step size.  The actual value that will be
 * stepped depends on the stepPart setting, set
 * by the 'tp' function below.
 * Returning 'this' from here.
 */
infix fun TimeRange.step(stepSize: Long): TimeRange {
    this.step = stepSize
    return this
}

/**
 * To specify the TimePart to use for increment when stepping
 * We return 'this' from here, rather than create a new object.
 * Just because.
 */
infix fun TimeRange.tp(timePart: TimePart): TimeRange {
    stepPart = timePart
    return this
}

/**
 * Our own withIndex function.  This one *has* to be called
 * as the last call in a change of until, step etc.
 * See testWithIndex() below.
 * Here we wrap the original iterator from the TimeRange, with
 * one that will return a Pair<Int, TimeHolder>
 */
fun TimeRange.withIndex(): Iterable<Pair<Int, TimeHolder>> {
    val origIterator = iterator()
    var index = 0
    val iterable = object : Iterable<Pair<Int, TimeHolder>> {
        override fun iterator(): Iterator<Pair<Int, TimeHolder>> {
            val newIterator = object : Iterator<Pair<Int, TimeHolder>> {
                override fun hasNext(): Boolean {
                    return origIterator.hasNext()
                }

                override fun next(): Pair<Int, TimeHolder> {
                    val p = Pair(index, origIterator.next())
                    //increment the index by the step amount
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
class TimeRange(val start: TimeHolder, val end: TimeHolder, var step: Long = 1) : Iterable<TimeHolder> {
    var realEnd = end
    //Are we including the end value?
    var endInclusive = true
        set(value) {
            realEnd = end - stepPart
            field = value
        }
    //This is the field we use to decide
    //what TimePart we use for stepping
    var stepPart = TimePart.SECOND
        set(value) {
            //reset the end only if we are not endInclusive
            if (!endInclusive) {
                realEnd = end - value
            }
            field = value
        }

    override fun iterator(): Iterator<TimeHolder> =
            object : Iterator<TimeHolder> {
                var current = start
                override fun hasNext(): Boolean {
                    return current <= realEnd
                }

                override fun next(): TimeHolder {
                    val obj = current
                    current += stepPart * step
                    return obj
                }
            }
}

/**
 * This function is called when the 'in' operator is used.
 * e.g. 'TimeHolder(20, 2, 2) in someTimeRange' will result in
 * a call to someTimeRange.contains(timeObject)
 */
operator fun TimeRange.contains(timeHolder: TimeHolder): Boolean {
    return timeHolder >= start && timeHolder <= end
}

/**
 * This allows is to iterate over an TimeRange.
 * so we can do for(t in TimeRange) {...}
 * Not Used - we are using the Comparable function in the TimRange class instead
 */
operator fun TimeRange.iterator(): Iterator<TimeHolder> =
        object : Iterator<TimeHolder> {
            var current = start
            override fun hasNext(): Boolean {
                return current <= realEnd
            }

            override fun next(): TimeHolder {
                val obj = current
                current += stepPart * step
                return obj
            }
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

class TestTimeHolderComplete {
    @Test
    fun testInNotIn() {
        val t1 = TimeHolder(10, 10, 10)
        val t2 = TimeHolder(10, 10, 20)

        val t3 = TimeHolder(10, 10, 11)
        Assertions.assertTrue(t3 in t1..t2)

        val t4 = TimeHolder(9, 10, 10)
        Assertions.assertTrue(t4 !in t1..t2)
    }

    @Test
    fun testInGreaterLessThan() {
        val t1 = TimeHolder(10, 10, 10)
        val t2 = TimeHolder(10, 10, 20)

        Assertions.assertTrue(t2 > t1)

        val t4 = TimeHolder(10, 10, 10)
        Assertions.assertTrue(t4 == t1)
        Assertions.assertTrue(t4 < t2)
    }

    @Test
    fun testIteration() {
        val t1 = TimeHolder(10, 10, 10)
        val t2 = TimeHolder(10, 10, 20)

        var count = 0
        for (t in t1..t2) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(11, count)
    }

    @Test
    fun testPlus() {
        val t1 = TimeHolder(10, 10, 10)
        val t2 = t1 + TimePart.MINUTE

        Assertions.assertTrue(t2 == TimeHolder(10, 11, 10))
    }


    @Test
    fun testPlusMinuteChange() {
        val t1 = TimeHolder(10, 59, 10)
        val t2 = t1 + TimePart.MINUTE

        Assertions.assertTrue(t2 == TimeHolder(11, 0, 10))
    }

    @Test
    fun testPlusWithMultiple() {
        val t1 = TimeHolder(10, 59, 10)
        val t2 = t1 + TimePart.MINUTE * 10

        Assertions.assertTrue(t2 == TimeHolder(11, 9, 10))
    }

    @Test
    fun testMinus() {
        val t1 = TimeHolder(10, 10, 10)
        val t2 = t1 - TimePart.SECOND

        Assertions.assertTrue(t2 == TimeHolder(10, 10, 9))
    }

    @Test
    fun testMinusChangeMinute() {
        val t1 = TimeHolder(10, 10, 0)
        val t2 = t1 - TimePart.SECOND

        Assertions.assertTrue(t2 == TimeHolder(10, 9, 59))
    }

    @Test
    fun testMinusChangeMinuteMultiple() {
        val t1 = TimeHolder(10, 10, 5)
        val t2 = t1 - TimePart.SECOND * 10

        Assertions.assertTrue(t2 == TimeHolder(10, 9, 55))
        println("t2: $t2")
    }

    @Test
    fun testUntil() {
        val t1 = TimeHolder(10, 10, 5)
        val t2 = TimeHolder(10, 10, 20)

        val range = t1 until t2
        Assertions.assertEquals(range.end, TimeHolder(10, 10, 20))
        Assertions.assertEquals(range.realEnd, TimeHolder(10, 10, 19))
    }

    @Test
    fun testIterationUntil() {
        val t1 = TimeHolder(10, 10, 10)
        val t2 = TimeHolder(10, 10, 20)

        var count = 0
        for (t in t1 until t2) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(10, count)
    }

    @Test
    fun testIterationStep() {
        val t1 = TimeHolder(10, 10, 11)
        val t2 = TimeHolder(10, 10, 20)

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
    fun testTimePart() {
        val t1 = TimeHolder(10, 10, 11)
        val t2 = TimeHolder(10, 15, 20)

        var count = 0
        for (t in t1..t2 tp TimePart.MINUTE) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(6, count)

        count = 0
        for (t in t1 until t2 tp TimePart.MINUTE) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(5, count)
    }

    @Test
    fun testTimePartWithStep() {
        val t1 = TimeHolder(10, 10, 11)
        val t2 = TimeHolder(20, 16, 20)

        var count = 0
        for (t in t1..t2 tp TimePart.HOUR step 2) {
            println("t: $t")
            count++
        }
        Assertions.assertEquals(6, count)

        count = 0
        for (t in t1 until t2 tp TimePart.MINUTE step 2) {
            count++
        }
        Assertions.assertEquals(303, count)
    }

    @Test
    fun testWithIndex() {
        val t1 = TimeHolder(10, 10, 11)
        val t2 = TimeHolder(10, 16, 20)

        val r = t1..t2
        var count = 0
        var validIndexes = mutableSetOf(0, 2, 4, 6)
        for ((index, t) in ((t1..t2) tp TimePart.MINUTE step 2).withIndex()) {
            println("$index: $t")
            assertTrue(index in validIndexes)
            validIndexes.remove(index)
            count++
        }
        Assertions.assertEquals(4, count)
        assertEquals(0, validIndexes.size);

        count = 0
        validIndexes = mutableSetOf(0, 3)
        for (t in (t1 until t2 tp TimePart.MINUTE step 3).withIndex()) {
            println("t: $t")
            assertTrue(t.first in validIndexes)
            validIndexes.remove(t.first)
            count++
        }
        Assertions.assertEquals(2, count)
        assertEquals(0, validIndexes.size);
    }

}
