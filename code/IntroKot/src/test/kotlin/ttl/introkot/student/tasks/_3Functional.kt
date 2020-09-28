package ttl.introkot.student.tasks

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ttl.introkot.solutions.total
import java.time.LocalTime

/**
 * @author whynot
 */

class Order(val price: Double, quantity: Int)

//TODO task1
// Write an extension function for the Order class called total
// that will calculate the total for the Order (price * quantity).
// You can also change the Order class if required.


class ExtensionTasks {
    companion object {
        /****************************************task 1********************************************/
        fun task1(): Double {
            val order = Order(22.5, 10)
            //TODO - Make this code compile.
            // See the TODO comment above
//            val totalprice = order.total()
//            return totalprice

            TODO()

        }

        /****************************************task 2********************************************/
        fun withManyArgs(arg1: Int, arg2: Double?, arg3: Int = 0, arg4: String): String? {
            return "boo"
        }

        //TODO
        //Finish the declaration of the argTakingLambda method to take a function of the
        // same type as withManyArgs.
//        fun argTakingLambda(fn: ) {
//            return fn(10, 22.2, 3, "handle")
//        }

        //TODO
        fun task2(): String {
            //Make this code compile
//            val result = argTakingLambda(::withManyArgs)
//            return result

            TODO()
        }


    }
}

class TestFunctionalTasks {
    @Test
    fun testTask1() {
        val total = ExtensionTasks.task1()
        assertEquals(225, total)
    }


    @Test
    fun testTask2() {
        val result = ExtensionTasks.task2()
        assertEquals("boo", result)
    }
}
