package ttl.introkot.course._2ClassesEtc

import org.junit.jupiter.api.Test
import java.awt.Rectangle

/**
 * @author whynot
 */

class Circle(val radius: Int, var x: Int, var y: Int) {
    val area: Double
        get() {
            return Math.PI * radius * radius
        }

    val circumference: Double = 2 * Math.PI * radius
}

class CustomSet
{
    var requestCount = 0
        private set(value) {
            if(value < 5) {
                println("Setting counter: $value")
                field = value
            }
        }

    fun processRequest() {
        requestCount++
        //process request
    }
}

class _4TestCircle{
    @Test
    fun doTest() {
        val r1 = Circle(20, 10, 10);
        println("${r1.area}")

        println("${r1.circumference}")

       val cs = CustomSet()
//        cs.requestCount = 3

        cs.processRequest();
        cs.processRequest();
    }
}
