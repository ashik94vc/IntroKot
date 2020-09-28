package ttl.introkot.course._1Intro

/**
 * @author whynot
 */

//won't compile
//var nullInitialized: String
//won't compile
//var nullInitialized: String = null

var nullInitialized: String? = null

fun useVar(input: String?) {
    if(input != null) {
        println("${input.length}")
    }

    input?.length
}

//fun main() {
//    useVar(nullInitialized)
//}
