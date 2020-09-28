package ttl.introkot.course.playground

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

class Sequences {
    /**
     * generateSequence stops at null value
     */
    @Test
    fun generator() {
        var list = generateSequence(1) {
            if (it > 3) null else it + 1
        }.toList()

        println(list)
    }
}

