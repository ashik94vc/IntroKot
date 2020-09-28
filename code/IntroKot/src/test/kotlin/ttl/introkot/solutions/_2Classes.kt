package ttl.introkot.solutions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalTime
import kotlin.reflect.full.memberProperties

/**
 * @author whynot
 */


class ClassesTasks {

    /*********************************************Task 1*************************************/
    //TODO Task 1
    //You have to make this class a well implemented Kotlin class.
    //Use all tricks you know.
    class ThumbTack(val radius: Double, val pinLength: Double)

    //TODO
    fun task1(): Double {
//        val thumbTack = ThumbTack()
        //Uncomment and fix.  You can make whatever changes to
        //the ThumbTack class you want to make it a good Kotlin class
//          thumbTack.radius = 6.0
//          thumbTack.pinLength = 4.2

        val thumbTack = ThumbTack(6.0, 4.2)
        return thumbTack.radius

//        TODO()
    }

    /*********************************************Task 2*************************************/

    class ThumbTack2(val radius: Double, val pinLength: Double = 4.5, val name: String = "")

    //TODO
    fun task2(): Double {
        //Add a "name" property to the ThumbTack class.
        //Set up the ThumbTack class so that the radius is required, the pinLength
        // defaults to 4.5 and the name is optional.
        //Create a thumbtack specifying a radius and a name.
        //Make the lines below work
        val result = ThumbTack2(6.2, name = "Sharpie")
        return result.radius

//        TODO()
    }

    /*********************************************Task 3*************************************/

    //TODO
    //Implement the 'ct' property to return the current time
    //whenever it is accessed
    val ct: LocalTime
        get() {
            return LocalTime.now()
        }

    //TODO
    fun task3(): LocalTime {
        //Uncomment this code
        println("Current Time: $ct")
        return ct

//        TODO()
    }

    /*********************************************Task 4*************************************/
    class Position

    //Note - Have to 'open' the class
    open class Shape(val id: Int, val position: Position, val name: String = "") {
        //Note - have to 'open' the function to allow overriding
        open fun draw() {
            println("shape.draw")
        }
    }

    //TODO
    //A Circle *is a* Shape. Remove the comments
    //and implement it properly.
    //Note - have to call constructor of the super class
    class Circle(id: Int, position: Position, name: String = "") : Shape(id, position, name) {
        //Note - Have to add 'override'
        override fun draw() {
            println("circle.draw")
        }
    }


    //TODO
    fun task4(): Circle {
        //Make this code compile and run
            val circle = Circle(10, Position())
            return circle

        TODO()
    }

    /*********************************************Task 5*************************************/
    //TODO Uncomment the code in 'testTask5'.  Then add code to this
    // class to make 'testTask5' succeed.
    //Note - The function in the task is called "statically".  So it needs to
    // be in a companion object
    companion object {

        //Note - @JvmStatic will allow this function to be called statically
        // from Java
        @JvmStatic
        fun greeting() = "Hola"
    }

    /*********************************************Task 6*************************************/
    //TODO - Complete the test in _2ClassesJava.java to call the 'greeting' function
    // you wrote in Task 5

}

/*****************************Start Tests******************************************/
class ThumbTackTests {

    val classesTasks = ClassesTasks()

    @Test
    fun testTask1() {
        val result = classesTasks.task1()
        assertEquals(6.0, result)
    }

    @Test
    fun testTask2() {
        val result = classesTasks.task2()
        assertEquals(6.2, result)
    }

    @Test
    fun testTask3() {
        val result = classesTasks.task3()
        assertTrue(result.hour >= 0)
    }

    @Test
    fun testTask4() {
        val result = classesTasks.task4()
        val kClass = result.javaClass.kotlin
        assertNotNull(kClass.memberProperties.firstOrNull { it.name == "position" })
    }

    //TODO - uncomment the code in this test and change
    // ClassesTasks such that this test succeeds.
    // Do not change any code in the test.
    @Test
    fun testTask5() {
        val result = ClassesTasks.greeting()
        assertEquals("Hola", result)
    }
}
