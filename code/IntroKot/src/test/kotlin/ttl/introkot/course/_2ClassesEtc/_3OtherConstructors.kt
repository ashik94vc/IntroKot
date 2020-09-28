package ttl.introkot.course._2ClassesEtc

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */
/**
 * Secondary constructors.
 */
//Note - You should  generally use default arguments with default arguments
// instead of many constructors.
class Account3(var id: Int, var balance: Double, var name: String) {

    //Note - you *have* to call the primary constructor from here.
    // You can do other initialization in here if you want.
    constructor(id: Int) : this(id, 0.0, "Boo") {
    }

    override fun toString(): String {
        return "Account(id=$id, balance=$balance, name='$name')"
    }

}

class _3TestAccount {
    @Test
    fun testAccounts() {
        val account = Account3(10, 1000.0, "Pralahan")
        println("$account")

        val a2 = Account3(20)
        println("$a2")
    }
}