package ttl.introkot.course._2ClassesEtc

import org.junit.jupiter.api.Test
import kotlin.random.Random

/**
 * @author whynot
 */

//Note - the primary constructor *has* to be called when an
// instance of this class is created.
class _2Account(val id: Int, var balance: Double, var name: String) {
    var winsLottery: Boolean

    init {
        var ri = Random.nextInt(10)
        winsLottery = ri > 7
    }

    override fun toString(): String {
        return "_2Account(id=$id, balance=$balance, name='$name', winsLottery=$winsLottery)"
    }


}

class PrimClass(val id: Int, val name: String = "john", val balance: Double)

fun forPrimClass() {
    val pc1 = PrimClass(0, "Filler", 2.2)
    val pc2 = PrimClass(0, balance = 2.2)
}

class _2TestAccount {
    @Test
    fun testAccounts() {
        val account = _2Account(10, 1000.00, "Pralahan")

        println("$account")

    }
}