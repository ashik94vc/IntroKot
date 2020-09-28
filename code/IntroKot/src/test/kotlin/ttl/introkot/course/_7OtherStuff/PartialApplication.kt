package ttl.introkot.course._7OtherStuff

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

fun add3(x: Int, y: Int, z: Int) : Int{
    return x + y + z
}

fun addStrings(x: String, y: String) : String {
    return "$x $y"
}

fun add(x: Int, y: Int) : Int{
   return x + y
}

/**
 * Partial Application.
 *
 * add (above) takes two Ints and returns an Int.
 * This function will take as arguments
 *      an Int,
 *      and a function that takes two Ints and returns an Int.
 * This function in turn returns a function that takes *one* Int
 * and returns an Int.  So we have to converted a function that takes
 * two Ints, into a function that takes one Int.
 */
fun partiallyApplyAdder(amount: Int = 1, block: (Int, Int) -> Int) : (Int) -> Int {
    //The result is a *function* that takes an Int, and calls the given
    //block of code with 'amount', (which will be part of the closure of
    //this new lambda), and the argument 'y', that will be supplied to
    //the result function when it is called at some point later.
    //Important to understand that the 'block' function is *NOT* called
    //at this point.
   val result = {y: Int -> block(amount, y) }
   return result
}


/**
 * Currying.
 *
 * Take a function that takes N arguments and convert it into N functions that
 * take 1 argument each.
 *
 * Here we are given a function that takes two Ints, and we are going to return
 * (deep breath) a function that takes an Int and returns another function that
 * takes an Int and returns an Int. (exhale)
 * So we have chopped up our original 2 argument function into two functions that
 * each take an Int.
 */
fun curryAdder(block: (Int, Int) -> Int) : (Int) -> (Int) -> Int {
    val f1: (Int) -> (Int) -> Int = {outer ->
        { inner ->
            block(outer, inner)
        }
    }

    return f1
}


/**
 * Same thing with three arguments
 */
fun curryAdder3(block: (Int, Int, Int) -> Int) : (Int) -> (Int) -> (Int) -> Int {
    val f1: (Int) -> (Int) -> (Int) -> Int = {outer ->
        { middle ->
            { inner ->
                block(outer, middle, inner)
            }
        }
    }

    return f1
}

/**
 * Generic
 */
fun <T, U, R> curryGen(block: (T, U) -> R) : (T) -> (U) -> R {
    val f1: (T) -> (U) -> R = {outer ->
        { inner ->
            block(outer, inner)
        }
    }
    return f1
}

class TestPartials {

    @Test
    fun testCurrying() {
        val curried = curryAdder(::add)
        val x = add(10, 20)
        val y = curried(10)(20);

        val add10: (Int) -> Int = curried(10)

        val z = add10(20)

        println("x: $x, y: $y, z: $z")

    }

    @Test
    fun moreTestCurring() {

        val curried3 = curryAdder3(::add3)
        val r2 = curried3(10)(20)(30)

        val strCurry = curryGen(::addStrings)
        //Here we are using currying as
        //partial application
        val greeter = strCurry("Hello");

        val r3 = greeter("Joe")
        val r4 = greeter("World")
        println("$r3, $r4")
    }

    @Test
    fun testPartialApplication() {
        val add1 = partiallyApplyAdder(block = ::add)
        val add10 = partiallyApplyAdder(10, ::add)

        val i1 = add(10, 20)
        val i2 = add10(20)

        val i3 = add1(i1)

        println("i1: $i1, i2: $i2, i3: $i3")
    }
}

fun main() {
    val tp = TestPartials();
    tp.testCurrying()
}