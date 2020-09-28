package ttl.introkot.course._1Intro

fun testRanges(c: Char) {
    if (c in 'a'..'z' || c in 'A'..'Z') {
        println("isLetter")
    } else if (c in '0'..'9') {
        println("isNumber")
    } else {
        println("None of the above")
    }
}

fun rangeWithWhen(c: Char) = when (c) {
    in '0'..'9' -> "isLetter"
    in 'a'..'z', in 'A'..'Z' -> "isNumber"
    else -> "None of the above"
}

//Ranges work by doing comparisons
//Can make a range of any type that implements
//Comparable
fun stringRanges() {

    val strRange = "Alpha".."Tango"

    val inRange = "Apple" in strRange
    val outOfRange = "Zulu" in strRange

    println("inRange: $inRange, outOfRange: $outOfRange")

}

class ClassForRange(val value: Int) : Comparable<ClassForRange>
{

    override fun compareTo(other: ClassForRange) : Int{
        return this.value.compareTo(other.value)
    }
}

fun myRangerTest() {
    val mrRange = ClassForRange(10) .. ClassForRange(25)

    val mc = ClassForRange(15)
    val inRange = mc in mrRange

    val mc2 = ClassForRange(46)
    val outOfRange = mc2 in mrRange

    println("inRange: $inRange, outOfRange: $outOfRange")
}

//fun main() {
////    testRanges('%')
////    rangeWithWhen('#')
////    stringRanges()
//    myRangerTest()
//}

