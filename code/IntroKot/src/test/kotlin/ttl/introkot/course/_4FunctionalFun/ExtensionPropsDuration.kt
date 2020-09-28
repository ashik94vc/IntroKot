package ttl.introkot.course._4FunctionalFun

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalTime
import java.time.temporal.ChronoUnit

/**
 * @author whynot
 */

val Int.hours: Duration
    get() {
        return Duration.ofHours(this.toLong())
    }

val Int.minutes: Duration
    get() {
        return Duration.ofMinutes(this.toLong())
    }

val Int.seconds: Duration
    get() {
        return Duration.ofSeconds(this.toLong())
    }

val Duration.fromNow: LocalTime
    get() {
        return LocalTime.now().withNano(0).plus(this)
    }

val Duration.ago: LocalTime
    get() {
        return LocalTime.now().withNano(0).minus(this)
    }

infix fun Duration.from(localTime: LocalTime): LocalTime = localTime + this
infix fun Duration.before(localTime: LocalTime): LocalTime = localTime - this

//Convenience property to do LocalTime.now().withNanos(0)
fun nownn(): LocalTime {
    return LocalTime.now().truncatedTo(ChronoUnit.SECONDS)
}

class TestExtensionProperties {
    @Test
    fun testAgoFromNow() {
        //One way to get the time 1 hour from now
        val d = Duration.ofHours(1);
        val lt1 = LocalTime.now().plus(d)

        var lt = 1.hours.fromNow
        var l2 = 1.minutes.ago

        assertEquals(1, lt.hour - nownn().hour)

        val now = nownn()
        val lt3 = 1.hours from now

        assertEquals(1, lt3.hour - now.hour)
        println("lt3: $lt3")

        val lt4 = 2.seconds before now
        println("now: $now, lt4: $lt4")
        assertEquals(2, now.second - lt4.second)
    }

    @Test
    fun testProps() {
        val now = LocalTime.now()
        val thirtyMinutesFromNow = now.plus(30, ChronoUnit.MINUTES)

        val tmfn = 30.minutes from now
    }
}
