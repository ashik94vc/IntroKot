package ttl.introkot.course._2ClassesEtc

/**
 * @author whynot
 */

interface Component {
    val name: String
    val coordinates: Coordinates
    fun draw()

}

/**
 * An Interface can not have state, or a Constructor.  So,
 * even though the state is declared in the Interface, it is
 * actually "implemented" in each concrete class.  This means
 * that each class has to manage the state itself.  This can
 * lead to duplicated code.  If the code is complex enough, it
 * may be useful to stick an Abstract Class in front of the
 * Interface, as shown in _7AbstractClasses.kt
 */
class Button(override val name: String, override val coordinates: Coordinates) : Component {
    init {
        if (coordinates.x < 0 || coordinates.y < 0) {
            throw RuntimeException("Coordinates have to be +ve: $coordinates")
        }
    }

    override fun draw() {
        println("$name: I'm a Button at $coordinates")
    }
}

class TextField(override val name: String, override val coordinates: Coordinates) : Component {
    init {
        if (coordinates.x < 0 || coordinates.y < 0) {
            throw RuntimeException("Coordinates have to be +ve: $coordinates")
        }
    }

    override fun draw() {
        println("$name: I'm a TextField at $coordinates")
    }
}

data class Coordinates(val x: Int, val y: Int)

fun main() {
    val comps = mutableListOf<Component>(
            Button("OK", Coordinates(10, 20)),
//            Button("Not Ok", Coordinates(-10, 40)),
            TextField("Name", Coordinates(10, 40))
    )

    comps.forEach(Component::draw)
}
