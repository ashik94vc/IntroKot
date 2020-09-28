package ttl.introkot.course._4FunctionalFun

/**
 * @author whynot
 */
data class Processor(var id: Int = 0) {
    lateinit var name: String
    lateinit var address: String
    lateinit var proxy: String
    val headers = mutableListOf<String>()


    fun process1() = "process1"
    fun process2()  = "process2"
    fun process3() = "process3"
}


