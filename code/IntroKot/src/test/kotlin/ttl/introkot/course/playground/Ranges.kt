package ttl.introkot.course.playground

import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Period

/**
 * Our custom range for LocalDate objects
 * @author whynot
 */

class TestRanges {
    @Test
    fun dateRanges() {
        var now = LocalDate.now()

        //Java functions called + with one argument
        //get an ez pass.  No operator functions required
        //This is calling now.plus(Period.ofDays(1)
        now + Period.ofDays(1)

        var playTime: ClosedRange<LocalDate> = now..(now.plusDays(10))

        for(ld in playTime) {
            println(ld)
        }
    }

    /**
     * The magic sauce.  Basically an iterator to step
     * through your range.
     * We also take advantage of the fact that LocalDate
     * implements Comparable, so we get to use <=, ==, etc. syntax
     * to compare dates.  Very nice.
     */
    operator fun ClosedRange<LocalDate>.iterator() : Iterator<LocalDate> = object : Iterator<LocalDate> {
        var current = start
        override fun hasNext(): Boolean {
           return current <= endInclusive
        }

        override fun next(): LocalDate {
            current = current.plusDays(1)
            return current
        }
    }
}