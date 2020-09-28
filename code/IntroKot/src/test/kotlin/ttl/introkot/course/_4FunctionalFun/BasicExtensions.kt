package ttl.introkot.course._4FunctionalFun

/**
 * @author whynot
 */
class Person(val firstName: String, val lastName: String) {
//    fun fullName() : String = "$firstName $lastName"
}

fun Person.fullName() : String = "$firstName $lastName"

fun String.randomize() : String {
    var list = this.toMutableList()
    list.shuffle()

    val a: Array<Char> = list.toTypedArray()

    val array: CharArray = list.toTypedArray().toCharArray()
    val ns = String(array)
    return ns
}


fun main() {
    val p = Person("Joe", "Smith")
    println("${p.firstName} ${p.lastName}")

    println(p.fullName())

    val s = p.fullName().randomize()

    println(s)
}
