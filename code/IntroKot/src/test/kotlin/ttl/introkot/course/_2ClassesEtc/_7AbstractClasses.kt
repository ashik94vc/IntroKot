package ttl.introkot.course._2ClassesEtc

/**
 * @author whynot
 */

interface AbComponent
{
    val name: String
    val coordinates: Coordinates
    fun draw()
}

/**
 * One advantage that an Abstract Class has over just an Interface
 * is that it can maintain the common state for the hierarchy.
 * So you can write code to manage that state in one place.
 */

abstract class AbstractComponent(override val name: String, override val coordinates: Coordinates)
    : AbComponent {
    init {
        if (coordinates.x < 0 || coordinates.y < 0) {
            throw RuntimeException("Coordinates have to be +ve: $coordinates")
        }
    }
}


class AbButton(name: String, coordinates: Coordinates) : AbstractComponent(name, coordinates)
{
    override fun draw() {
        println("$name: I'm a Button at $coordinates")
    }
}

class AbTextField(name: String, coordinates: Coordinates) : AbstractComponent(name, coordinates)
{
    override fun draw() {
        println("$name: I'm a TextField at $coordinates")
    }
}

data class AbCoordinates(val x: Int, val y: Int)

fun main() {
    val comps = mutableListOf<AbComponent>(
            AbButton("OK", Coordinates(10, 20)),
            AbTextField("Name", Coordinates(10, 40))
    )

    comps.forEach(AbComponent::draw)
}
