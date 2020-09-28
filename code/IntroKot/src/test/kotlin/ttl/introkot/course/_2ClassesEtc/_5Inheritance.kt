package ttl.introkot.course._2ClassesEtc

/**
 * @author whynot
 */

//Classes *have* to be declared 'open' to be available for extension
open class Parent(val id: Int, val name: String) {
    //Methods *have* to be declared 'open' to allow overriding
    open fun doStuff() {
        println("doParent: $id, $name")
    }
}

//Sub class*has* to call a Parent class constructor.
//The 'balance' property exists only in Child.
//This can be better handled with default arguments, as shown below.
open class Child(id: Int, name: String, val balance: Double = 0.0) : Parent(id, name) {
    constructor(id: Int, balance: Double) : this(id, "", balance)

    //'override' keyword *has* to be used to override a method
    override fun doStuff() {

    }
}

open class ChildDefArgs(id: Int, name: String = "", val balance: Double = 0.0) : Parent(id, name) {

    //'override' keyword *has* to be used to override a method
    override fun doStuff() {

    }
}

class GrandChild(id: Int, name: String, handle: String = "") : Child(id, name)

class GrandChildDefArgs(id: Int, name: String, handle: String = "") : ChildDefArgs(id, name)

fun main() {
    val child = Child(10, "Joe")
    child.doStuff()

    val obj = Parent(10, "Priny")
    childSpecificProperty(obj)
}

fun childSpecificProperty(input: Parent) {
    input.doStuff()
//To access Child specific properties you have to
//cast appropriately.  Checking for instance type
// with 'is' automatically casts input if test is true
//This is called "Smart Casting"
    if(input is Child) {
        println("${input.balance}")
    }
}
