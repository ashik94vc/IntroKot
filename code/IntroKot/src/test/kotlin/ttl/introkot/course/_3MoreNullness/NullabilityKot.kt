package ttl.introkot.course._3MoreNullness

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

class NullabilityKot
{
    /**
     * This goes along with NullabilityJava.getStuff
     */
    @Test
    fun testNullFromJava() {
        val jc = NullabilityJava()
        val str: String = jc.stuff
        println("str: $str")
    }

    fun xyz(x: String) {

    }

    var name = ""
    fun smartCast(s: String?) {
        //name = s  //Not allowed because of Nullability constraint
       if(s != null) {  //If this is true
           name = s     //then s has been "smart cast" at this point to a non nullable String
       }
    }

    var nonLocalValue: String? = ""

    fun smartCastFails() {
        if(nonLocalValue != null) {
            //Here the smart cast won't work because the
            //nonLocalValue could be changed between the check
            //and the assignment
            //name = nonLocalValue
        }
    }

    fun smartCastWorks() {
        val localValue = nonLocalValue
        if(localValue != null) {
            //This one works because we are looking at the
            //localValue which no one else can change.
            //Note, of course, that nonLocalValue can still
            // change.
            name = localValue
        }
    }

    @Test
    fun testNullTypes() {
        var i: Int? = 10
        var i2 = 10


        println("iclass: ${i!!::class.java}, i2class: ${i2::class.java}")
    }
}