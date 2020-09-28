package ttl.introkot.course._1Intro

/**
 * @author whynot
 */

//Type is inferred.
//Compiler chooses primitive or reference based on context
//val => Read Only, *not* necessarily immutable
//var => Read Write
val i = 10

var changeAble = 10

fun foo() {
//   i = 20
    changeAble = 35
}


//immutable list
val list = listOf(10, 20, 30)
//mutable
val mutList = mutableListOf(10, 20, 30)

fun doLists() {
//    list.add(50)
    mutList.add(50)
}

fun takeAList(list: List<Int>) {
//    list.add(50)
}

