package ttl.introkot.course._4FunctionalFun

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ttl.larku.domain.Student
import java.util.function.Predicate

/**
 * @author whynot
 */

class FunctionTypes {
    fun foo(input: Int) {
        println("input: $input")
    }

    fun bar(input: String, id: Int): Double {
        return input.length * id.toDouble()
    }

    //Function Type declaration.  And method reference.
    val fooVar: (Int) -> Unit = ::foo

    val barVar: (String, Int) -> Double = ::bar

    fun lambdaFun() {
        //Initialize with Lambda
        val otherBarVar: (String) -> Int = { s: String -> s.length }
        val obv = otherBarVar("hello")
        println(obv)
    }
}

class BaseDAO {
    fun findById(id: Long): Student = Student("Joe", "383 838 8383", Student.Status.FULL_TIME)
    fun findAll(): List<Student> = listOf(
            Student("Joe", "383 838 8383", Student.Status.FULL_TIME),
            Student("Mistry", "383 9659 8383", Student.Status.HIBERNATING),
            Student("Tomaz", "400 9659 8383", Student.Status.HIBERNATING)
    )


    //Bad way of implementing findBy... Need a separate method
    //for each search criteria
    fun findByNameStartsWith(prefix: String): List<Student> {
        val result = mutableListOf<Student>()
        for (s in findAll()) {
            if (s.name.startsWith(prefix)) {
                result.add(s)
            }
        }
        return result
    }

    //Much better way of implementing findBy functionality.  Hide
    //the thing that varies, i.e. the search criteria, behind a
    //function abstraction.  Here, we get a function argument
    //that can take a Student and return a Boolean, which we
    //can pass to the filter method directly.
    //The findBy method itself returns a List<Student>
    fun findBy(pred: (Student) -> Boolean): List<Student> {
        val result = findAll()
                .filter(pred)
        return result

    }

    fun findByExternalIteration(pred: (Student) -> Boolean): List<Student> {
        val result = mutableListOf<Student>()
        for (s in findAll()) {
            if (pred(s)) {
                result.add(s)
            }
        }
        return result

    }
}

class TestFunctionTypes {
    @Test
    fun testFunctionTypes() {
        val ft = FunctionTypes()
        ft.foo(10)
        ft.fooVar(20)

        println(ft.barVar("A really ", 10))

    }

    @Test
    fun testFindBy() {
        val bd = BaseDAO()
        val students = bd.findByNameStartsWith("M")
        println("st: $students")

        //When the last argument to a function is a function that will be
        //implemented as a lambda, the parentheses can be omitted.
        val students1 = bd.findBy( { student -> student.name.startsWith("M") })
        val students2 = bd.findBy { student -> student.name.startsWith("M") }
        val students3 = bd.findBy { student -> student.status == Student.Status.HIBERNATING }
        val students4 = bd.findBy { student -> student.phoneNumber.endsWith("390")}

        val students5 = bd.findBy { it.phoneNumber.endsWith("390")}

        println("s4: $students4")
        assertEquals(0, students4.size)

        val x: Predicate<Student> = Predicate<Student> {
            student -> student.name.startsWith("M")
        }.and {
            student -> student.status == Student.Status.HIBERNATING
        }

    }
}