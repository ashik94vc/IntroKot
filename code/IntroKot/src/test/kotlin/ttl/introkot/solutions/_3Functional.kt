package ttl.introkot.solutions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

class Order(val price: Double, val quantity: Int)

//TODO task1
// Write an extension function for Order called total
// that will calculate the total for the Order (price * quantity).
// You can also change the Order class if required.

//Note - Extensions function
fun Order.total() = price * quantity

class ExtensionTasks {
    companion object {
        /****************************************task 1********************************************/
        fun task1(): Double {
            val order = Order(22.5, 10)
            //Make this code compile
            //See the TODO comment above
            val totalprice = order.total()
            return totalprice

            TODO()

        }

        /****************************************task 2********************************************/
        fun withManyArgs(arg1: Int, arg2: Double?, arg3: Int = 0, arg4: String): String? {
            return "boo"
        }

        //TODO
        //Finish the declaration of the argTakingLambda method to take a function of the
        // same type as withManyArgs
        //Note - We have a choice of returning a String? here, or of
        // converting a null return fron fn(...) to a non null type.
        // Returning non null types should be preferred if possible.
        fun argTakingLambda(fn: (Int, Double?, Int, String) -> String?) : String{
            return fn(10, 22.2, 3, "handle") ?: "boo"
        }

        //TODO
        fun task2(): String {
            //Make this code compile
            val result = argTakingLambda(::withManyArgs)
            return result

//            TODO()
        }


    }
}

class TestFunctionalTasks {
    @Test
    fun testTask1() {
        val total = ExtensionTasks.task1()
        assertEquals(225.0, total)
    }


    @Test
    fun testTask2() {
        val result = ExtensionTasks.task2()
        assertEquals("boo", result)
    }
}
