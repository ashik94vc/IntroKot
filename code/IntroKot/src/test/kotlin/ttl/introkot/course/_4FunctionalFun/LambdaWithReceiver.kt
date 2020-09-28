package ttl.introkot.course._4FunctionalFun

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

class LambdaWithReceiver {
    //Lambda
    val lengthSquared: (String) -> Int = { s: String -> s.length * s.length}
    //lambda with receiver
    val lengthSquaredWithReceiver: String.() -> Int = { this.length * this.length}

    val s1 = lengthSquared("hello")
    val s2 = "hello".lengthSquaredWithReceiver()

    fun builder1() : String {
        val sb1 = StringBuilder()
        sb1.append("The")
        sb1.append("Way")
        sb1.append("The")
        sb1.append("Cookie")
        sb1.append("Crumbles")

        return sb1.toString()
    }

    fun builder2() : String {
        val sb1 = StringBuilder().apply {
            append("The")
            append("Way")
            append("The")
            append("Cookie")
            append("Crumbles")
        }

        return sb1.toString()
    }

    fun builder3() : String {
        val str = buildString {
            append("The")
            append("Way")
            append("The")
            append("Cookie")
            append("Crumbles")
        }

        return str
    }

    fun builder4() = buildString {
            append("The")
            append("Way")
            append("The")
            append("Cookie")
            append("Crumbles")
        }

    fun buildString(init: (StringBuilder.() -> Unit)) : String {
        val sb = StringBuilder()
        sb.init()
        return sb.toString()
    }


    @Test
    fun testBuildString() {
        val str = buildString {
            append("The ")
            append("Way ")
            append("The ")
            append("Cookie ")
            append("Crumbles")
        }

        println("str: $str")
    }
}

