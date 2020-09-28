package ttl.kotcourse.generics


/**
 * The 'in T' says allow a list of some Type into which
 * one can put T (similar to ? super T in Java
 */
fun <T> addToList(list: MutableList<in T>, arr: Array<T>) {
    for(t in arr) {
        list.add(t)
    }
}

fun <T : Number> sum(list: MutableList<T>) : Double {
    return list.fold(0.0) {
        acc, next -> acc + next.toDouble()
    }
}

fun sumList(list: List<Number>) : Double {
    return list.fold(0.0) {
        acc, next -> acc + next.toDouble()
    }
}

fun main(args: Array<String>) {
    var nList: MutableList<Number> = mutableListOf(22.3, 36, 55.3, 99, 200)

    var result = sum(nList)
    println(result)

    var iList: MutableList<Int> = mutableListOf(22, 36, 55, 99, 200)

    result = sum(iList)
    println(result)

    var iarr = arrayOf(10, 20, 30, 40)
    addToList(iList, iarr)
    addToList(nList, iarr)
}


open class Base(age: Int){}
class Derived : Base {
    constructor(age: Int) : super(age)
}