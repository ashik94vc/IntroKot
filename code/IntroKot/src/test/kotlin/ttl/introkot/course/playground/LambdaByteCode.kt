package ttl.introkot.course.playground

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */
class LambdaByteCode {

    @Test
    fun test() {
        val captured = 20
//        foo(x)

        for(i in 0 until 10) {
            val r = foo(captured) {
//                input: Int -> "result $input : $captured, $i"
                input: Int -> "result $input, $i"
            }
//            val x = {input: Int -> "result $captured, $i"}
//            val r = foo(i, x)
            println("r: $r")
        }
    }

    inline fun foo(input: Int, crossinline func: (Int) -> String) : String{
        return bar {
            //Some
            func(input)
        }
    }

    fun bar(processor: () -> String) : String {
        return processor()
    }
}