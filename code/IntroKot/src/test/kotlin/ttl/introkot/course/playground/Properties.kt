package ttl.introkot.course.playground

/**
 * @author whynot
 */


//Lazy property.  Will be initialized once
//on first access.  Or never on no access
class LazyDemo {
    val lazer: String by lazy {
        println("Lazy init")
        "Boo"
    }
}

