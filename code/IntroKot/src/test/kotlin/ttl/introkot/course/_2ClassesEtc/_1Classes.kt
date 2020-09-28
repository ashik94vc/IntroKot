package ttl.introkot.course._2ClassesEtc

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */
class Account1 {
    var id: Int = 0
    var balance: Double = 0.0
    var name: String = ""

    override fun toString(): String {
        return "Account(id=$id, balance=$balance, name='$name')"
    }
}

//class CheckingAccount : Account() {
//   val minBalance = 100.00
//}
//
//class SavingsAccount : Account() {
//   val interestRate = .03
//}

class TestAccount1 {
    @Test
    fun testAccounts() {
        val account = Account1()
        account.id = 10
        account.balance = 1000.0
        account.name = "Pralahan"

        println("$account")

    }
}