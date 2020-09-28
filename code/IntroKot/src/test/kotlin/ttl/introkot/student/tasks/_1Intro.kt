package ttl.introkot.student.tasks

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * @author whynot
 */


/****************************Task 1*************************************/

//TODO
fun task1() : String {
    //Uncomment out the lines below and add
    //any necessary code to make them compile

//    val result = getAString(10)
//    return result

    return TODO()
}

/****************************Task 2*************************************/

//niceness and alias can be any value and are not required
//Don't change the types of the parameters
fun someFun(prodName: String, niceness: Int = 0, price: Double, alias: String = ""): String {

    return prodName;
}

//TODO
fun task2() : String {
    //Make changes so the following code compiles.
    //Don't change the types of the arguments

    //val result = someFun("ShinyThing", 22.2)
    //return result

    return TODO()
}

/****************************Task 3*************************************/

fun listAdder(input: List<Int>) {
    val local = input
    //Make changes to make this code compile
//    local.add(10)

    return TODO()
}

//TODO
//Make changes in 'listAdder' above to make the test run successfully
fun task3() : List<Int> {
    val list = mutableListOf<Int>()
    listAdder(list)
    return list
}


/****************************Task 4*************************************/
class LTHolder {
    var dbLoc: String = TODO() //Comment this out
    //TODO
    //Modify so this declaration compiles
    //val dbLoc: String

    fun initDbLoc() {
        dbLoc = "xyz.com/db"
    }

    //TODO
    //Modify the dbLoc declaration above
    //testTask4 run
    fun task4(): String {
        initDbLoc()
        return dbLoc
    }
}

/****************************Task 5*************************************/
val skill = ""

//TODO
fun initSkill(newSkill: String?) {
    //Make this next line line work without
    //changing any other code in this function.
    //skill = newSkill
}

//TODO
fun task5() : String?{
    initSkill("carpenter")
    return skill
}

/****************************Task 6*************************************/

class WHolder {
    enum class Priority {
        HIGH,
        MEDIUM,
        LOW
    }

    //TODO
    fun findPriorityWithPoints(priority: Priority) : Pair<Priority, Int> {
        //Write code to return a Pair based on Priority
        //HIGH => 10
        //MEDIUM => 5
        //LOW => 2

        TODO()
    }

    fun task6() : Pair<Priority, Int>{
        val result = findPriorityWithPoints(Priority.HIGH)
        return result
    }
}

/****************************Task 7****************************************/
//TODO - Write a function to calculate the sum and average of a list of Integers.
// The function should return BOTH values.  Write a test to verify your
// function.

/****************************End Tasks*************************************/



/****************************Start Tests*************************************/

class TestIntroTasks {

    @Test
    fun testTask1() {
        val result = task1()
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testTask2() {
        val result = task2()
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testTask3() {
        val result = task3()
        assertEquals(1, result.size)
    }

    @Test
    fun testTask4() {
        val ltHolder = LTHolder()
        val result = ltHolder.task4()
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testTask5() {
        val result = task5()
        assertTrue(!result.isNullOrEmpty())
    }

    @Test
    fun testTask6() {
        val wh = WHolder()
        val result = wh.task6()
        assertTrue(result.first == WHolder.Priority.HIGH && result.second == 10)
    }

}
