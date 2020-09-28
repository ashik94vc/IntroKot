package ttl.introkot.course.playground

/**
 * @author whynot
 */


fun main() {
    start("abc")
}

fun start(input: String) {
    val result = step0(input) { str ->
        val len = str.length

        val r2 = step1(len) { input ->
            if(input < 10) {
                return@step1 10
            } else {
                input * input
            }
        }

        //Same thing
        //return@step0 r2
        r2
    }

    println("final result: $result")

}

inline fun step0(input: String, outer: (String) -> Int) : Int{
   val outerResult = outer(input)
    println("step0.outerResult:  $outerResult")
    return outerResult
}

inline fun step1(input: Int, inner: (Int) -> Int) : Int{
    val innerResult = inner(input)
    println("step1.innerResult: $innerResult")
    return innerResult

}