package ttl.introkot.course.playground

/**
 * @author whynot
 */

fun main(args: Array<String>) {
    val s1: String? = null
    val s2: String? = ""
    s1.isEmptyOrNull() eq true
    s1.isNullOrEmpty()
    s2?.isEmptyOrNull() eq true

    val s3 = "   "
    s3?.isEmptyOrNull() eq false
}

//If the receiver is a Nullable, then it
//can be called with a null reference (s1 or s2 above) *without*
//using the ?.  That let's you check for this == null.
//If you do use the ? (s1?.isEmptyOrNull()) then this method
//does *not* get called at all.  Weird little corner.
fun String?.isEmptyOrNull() : Boolean {
    return this == null || this.isEmpty()
}

fun perr(str: String) =  System.err.println(str)

infix fun Any?.eq(other: Any?) : Boolean {
    val b = (this != null) && this == other
    if(!b) {
        perr("$this != $other")
    }
    return b
}