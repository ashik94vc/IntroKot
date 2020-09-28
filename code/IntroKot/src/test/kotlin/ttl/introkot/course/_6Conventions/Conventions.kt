package ttl.introkot.course._6Conventions

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

//The concept of conventions in Kotlin refers to the ability to implement
//functions with specific signatures, which allows the compiler to
//treat them differently in certain scenarios.  This ability is most
//used to overload operators
class Point(val x: Int, val y: Int) {
    fun add(scalar: Int) : Point{
       return Point(x + scalar, y + scalar)
    }

    //If, instead of 'add', we use the following convention
    //for the method signature, we get an overloaded operator +
    operator fun plus(scalar: Int) : Point {
        return Point(x + scalar, y + scalar)
    }
}

class ConventionsTest
{
    @Test
    fun testAddFunction() {
        val p = Point(10, 20)
        val p2 = p.add(20)

        assertTrue(p2.x == 30 && p2.y == 40)
    }

    @Test
    fun testPlusFunction() {
        val p = Point(10, 20)
        val p2 = p.plus(20)

        val p3 = p + 20

        assertTrue(p3.x == 30 && p3.y == 40)
    }
}