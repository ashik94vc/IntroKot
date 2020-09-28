package ttl.introkot.larku.domain

/**
 * @author whynot
 */
class OurOwnCopy(var id: Int, var name: String) {

    fun copy(id: Int = this.id, name: String = this.name) : OurOwnCopy {
       return OurOwnCopy(id, name)
    }

    override fun toString(): String {
        return "OurOwnCopy(id=$id, name='$name')"
    }
}

fun main() {
    val ooc1 = OurOwnCopy(10, "Joe")

    val ooc2 = ooc1.copy(name = "Frank")

    println("ooc1: $ooc1, ooc2: $ooc2")
}