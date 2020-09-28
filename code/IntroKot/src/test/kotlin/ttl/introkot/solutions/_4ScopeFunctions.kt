package ttl.introkot.solutions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

class ScopeFunctionTasks {
    class Machine(id: Int, name: String) {
        var horsePower: Int? = null
        var outputWatts: Int = 2500
        var outputAmps: Int = 15
    }

    companion object {
        fun initMachine(): Machine {
            val machine = Machine(20, "Big Guy")
            machine.horsePower = 2000
            machine.outputWatts = 30
            machine.outputAmps = 2

            return machine
        }

        /**************************************task 1********************************/
        //TODO
        //Rewrite initMachine using the most appropriate Scope Function
        //Note - 'apply' is usually the best function for initialization
        fun initWithScopeFunction(): Machine {
            return Machine(20, "BigGuy").apply {
                horsePower = 2000
                outputWatts = 30
                outputAmps = 2

            }
            TODO()
        }

        /**************************************task 2********************************/
        class Bundle(val name: String, val count: Int)

        fun getBundle(): Bundle? = Bundle("Insert", 10)
        fun process(bundle: Bundle) =
                when {
                    bundle.count < 10 -> "Low"
                    bundle.count in (10..20) -> "Medium"
                    else -> "High"
                }

        fun doExchange(): String? {
            val bundle = getBundle()
            return if (bundle != null) {
                return process(bundle)
            } else {
                null
            }
        }

        //TODO
        //Rewrite doExchange using the most appropriate Scope Function
        //Note - 'let' works well for null checks and transformations
        fun doExchangeWithScopeFunction(): String? {
            return getBundle()?.let {
                process(it)
            }
            TODO()
        }
    }

}

class TestScopeTasks {
    @Test
    fun testTask1() {
        val machine = ScopeFunctionTasks.initWithScopeFunction()
        assertEquals(2, machine.outputAmps)
    }

    @Test
    fun testTask2() {
        val result = ScopeFunctionTasks.doExchangeWithScopeFunction()
        assertEquals("Medium", result)
    }
}