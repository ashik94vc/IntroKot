package ttl.introkot.course.playground

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import kotlin.random.Random

/**
 * @author whynot
 */

class NothingTests {

    @RepeatedTest(5)
    fun testNothing() {
        assertThrows(RuntimeException::class.java) {
            val i = Random.nextInt(20)
            val j = if (i > 20) i else oops(i)

            val x = j + 1
            println("x: $x")
        }
    }

    //If you return Unit from here, then the type of j above
    //becomes 'Any'.  So you can't add 1 to j.
    //With Nothing, the type of j is Int since Nothing is a
    //subtype of Int.  But then you have to "return" a Nothing from here, which
    //is usually done by throwing an Exception
    fun oops(i: Int) : Nothing {
        //println("Ooops: $i")
        throw RuntimeException("Ooops: $i")
    }
}