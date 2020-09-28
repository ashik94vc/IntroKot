package ttl.introkot.course._1Intro

/**
 * @author whynot
 */


//Vals *have* to be initialized when constructed

//With vars you have an out with lateinit.

//This won't compile
//var notInitialized : String


lateinit var initialized: String

fun lateInitVarBoom(){
    //Boom
    val x = initialized
    println("${initialized.length}")
}

fun lateInitVar(){
    initialized = "Phew"
    //No Boom
    val x = initialized
    println("${initialized.length}")
}

//fun main() {
//    lateInitVarBoom()
//}

