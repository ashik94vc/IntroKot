package ttl.introkot.course._2ClassesEtc

/**
 * @author whynot
 */

class MyClass
{
    private fun foo() {
        println("MyClass.foo: ${this::class.qualifiedName}")
    }

    //This is the closest you can get to "static" things
    //for a class in Kotlin.
    companion object {
        //@JvmStatic
        fun fakeStaticFun() {
            println("Companion: ${this::class.qualifiedName}")
            println("fakeStatic called")
        }
    }

    //This is a Singleton called A
    //You invoke it's behaviours statically
    //through the name, e.g. MyClass.A.callFooFromA(..),
    //MyClass.A.i = 20
    object A {
        var i = 0
        fun callFooFromA(mc: MyClass) {
            println("A: ${this::class.qualifiedName}")
            mc.foo()
        }
    }
}

fun main() {
    val mc = MyClass()

    MyClass.A.callFooFromA(mc)

    //Companion Object syntax
    MyClass.fakeStaticFun()
}
