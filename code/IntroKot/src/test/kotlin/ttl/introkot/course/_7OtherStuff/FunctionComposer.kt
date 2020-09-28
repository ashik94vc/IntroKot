package ttl.introkot.course._7OtherStuff

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * @author whynot
 */
fun sq(input: Int): Int {
    return (input * input).also {
        println("Square: $it")
    }
}

fun double(input: Int): Int = (input * 2).also { println("Double: $it") }
fun half(input: Int): Int = (input / 2).also { println("Half: $it") }

fun blowUp(input: Int) : Int = if(input < 5) 10 else throw RuntimeException("Bad Input: $input")

fun retrieve(input: Int) = "result$input"

fun transform(input: String): String {
    return input.capitalize()
}

/**
 * First attempt at creating a function composer
 */
fun composer(first: (Int) -> Int, second: (Int) -> Int): (Int) -> Int {
    return { input: Int ->
        second(first(input))
    }
}

/**
 * Create an operator to express the composition.
 * This is superseded by the Generic version below
 */
//operator fun ((Int) -> Int).plus(other: (Int) -> Int) : (Int) -> Int {
//    return { input: Int ->
//        other(this(input))
//    }
//}

/**
 * Override operator + for Function1 objects
 * Function1 is of type (T) -> R
 * This function takes another Function1 as an argument,
 * and returns a function that, when called, will first call
 * this function, then the other function passed in as the argument.
 * We have chained the two function calls.
 * The returned function is a composition of this function and the argument.
 *
 * Because it is an operator you can compose functions thusly:
 *      ::sq + ::double + ::pp
 *
 * They will be invoked left to right.
 *
 * @param other: The other function .
 * @return A Function representing the composition of this function and the argument.
 */
operator fun <T, U, R> Function1<T, U>.plus(other: Function1<U, R>): (T) -> R {
    return { input: T ->
        other(this(input))
    }
}

/**
 * Could also declare the signature explicitly.
 *
 */
//operator fun <T, U, R> ((T) -> U).plus(other: (U) -> R) : (T) -> R {
//    return { input: T ->
//        other(this(input))
//    }
//}


/**
 * Compose Predicates.
 * Unfortunately the plus operator is taken for Function1 above.
 * So we create infix extension functions 'and' and 'or' for
 * all Functions of type (T) -> Boolean
 */

infix fun <T> ((T) -> Boolean).and(other: (T) -> Boolean): (T) -> Boolean {
    return { input: T ->
        this(input) && other(input)
    }
}

infix fun <T> ((T) -> Boolean).or(other: (T) -> Boolean): (T) -> Boolean {
    return { input: T ->
        this(input) || other(input)
    }
}

class TestComposers {
    @Test
    fun testComposeFunction() {
//    val sqThenDouble = composer(::sq, ::double)
//    sqThenDouble(2).also {println("Final: $it")}

        //Have to use this awful syntax if we call the function the standard way
        val sqThenDoubleThenHalf = composer(composer(::sq, ::double), ::half)
        val result = sqThenDoubleThenHalf(2)
        println("Final: $result")
        assertEquals(4, result)
    }

    @Test
    fun testComposeWithPlus() {
        val sqThenDouble2 = ::sq + ::double + ::half
        sqThenDouble2(2).also {
            println("Final: $it")
            assertEquals(4, it)
        }
    }

    @Test
    fun testComposeWithTransforms() {
        val transformer = ::sq + ::double + ::half + ::retrieve + ::transform
        val result = transformer(2)
        println("Final: $result")
        assertTrue(result[0].isUpperCase());
    }

    @Test
    fun testBlowUp() {
        assertThrows(RuntimeException::class.java) {
            val transformer = ::sq + ::blowUp + ::double + ::half + ::retrieve + ::transform
            val result = transformer(5)
            println("Final: $result")
            assertTrue(result[0].isUpperCase());
        }

    }

    @Test
    fun testPredicateAndOr() {

        val list = listOf("one", "biggish", "collection", "of", "not very", "big things", "boo", "vox")

        val nameMatch = { str: String -> str.startsWith("b") }
        val lenMatch = { str: String -> str.length > 3 }
        val vMatch = { str: String -> str.contains("v") }
        //Test Predicates
        val p = { str: String -> str.startsWith("b") } and
                { it.length > 3 } or
                { it.contains("v") }

        val q = nameMatch and lenMatch or vMatch

        p("voo").also {
            println(it)
            assertTrue(it)
        }

        val filtered = list.filter(p).also { println("List: $it") }
        assertEquals(4, filtered.size)

    }
}