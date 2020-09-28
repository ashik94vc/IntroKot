@file:JvmName("KotExceptions")

package ttl.introkot.course._1Intro

import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Paths


/**
 * @author whynot
 */

fun calculate(x: Int, y: Int) : Int {
    return x / y;
}

//fun main() {
//    val result = calculate(2, 3)
//    println("result: $result")
//
//    //Note - try as an expression
//    val result2 = try {
//        calculate(2, 0)
//    }catch(e: Exception) {
//        println("try got exception $e, returning 0")
//        0
//    }
//    println("result2: $result2")
//
//    doAFile("Boo");
////    doAFileWithThrows("Boo")
//
//    //Note - We can call the Java Function which
//    // throws IOException without having to catch it.
////    _8ExceptionsFromJava.doAfileFromJava("Boo")
//}

//Note - No Need for an Exception declaration
fun doAFile(fileName: String) {

    //JDK 11
    //val f = Files.lines(Paths.get(fileName)).limit(1).findAny().orElseThrow();
    val f = Files.lines(Paths.get(fileName)).limit(1).findAny()
    if (!f.isPresent()) {
        throw NoSuchElementException("Nothing found")
    }
}

//Note - You can add one if you like, but it makes
// no difference when you call from Kotlin.
// Java code will see it as a throws declaration.
@Throws(NoSuchFileException::class)
fun doAFileWithThrows(fileName: String) {


    //val f = Files.lines(Paths.get(fileName)).limit(1).findAny().orElseThrow();
    val f = Files.lines(Paths.get(fileName)).limit(1).findAny()
    if (!f.isPresent()) {
        throw NoSuchElementException("Nothing found")
    }
}
