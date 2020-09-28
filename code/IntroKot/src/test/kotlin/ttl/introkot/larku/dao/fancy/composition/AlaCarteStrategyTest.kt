package ttl.introkot.larku.dao.fancy.composition

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ttl.introkot.larku.dao.file.fancy.composition.*
import ttl.introkot.larku.domain.Course
import ttl.introkot.larku.domain.Email
import ttl.introkot.larku.domain.Student
import java.nio.file.Paths
import java.time.LocalDate

/**
 * @author whynot
 */
class AlaCarteStrategyTest {

    @Test
    fun testJsonStrategyDAO() {
        val strategy = JsonStrategy("/tmp/strategyStudents2.json")
        val dao = ALaCarteStrategyStudentDAO(strategy::serialize, strategy::reify)

        val oldCount = dao.count()

        dao.insert(Student(0, "Bird", "338 93283", LocalDate.now().minusYears(30), Email("a@b.com")))

        val count = dao.count()
        dao.close();

        Assertions.assertEquals(oldCount, count - 1)

    }

    @Test
    fun testSerialStrategyDAO() {
        val strategy = SerialStrategy("/tmp/strategyStudents2.ser")
        val dao = ALaCarteStrategyStudentDAO(strategy::serialize, strategy::reify)

        val oldCount = dao.count()

        dao.insert(Student(0, "Bird", "338 93283", LocalDate.now().minusYears(30), Email("a@b.com")))

        val count = dao.count()
        dao.close();

        Assertions.assertEquals(oldCount, count - 1)

    }

    @Test
    fun testSerialPrint() {
        val strategy = SerialStrategy("/tmp/strategyStudents2.ser")

        var students = getAll(strategy)
        students.forEach(::println)


        println("Json")
        val js = JsonStrategy("/tmp/strategyStudents2.json")
        students = getAll(js)
        students.forEach(::println)


    }

    fun getAll(strategy: PersistenceStrategy<Student>) : List<Student>{
        val dao = ALaCarteStrategyStudentDAO(strategy::serialize, strategy::reify)
        val students = dao.findAll()
        return students;
    }

    @Test
    fun testOnTheFlyStrategy() {
        val serialize: (State<*>) -> Unit = {state ->
            println(state.toString())
        }

        val reify: () -> State<Student> = {State()}

        val dao = ALaCarteStrategyStudentDAO(serialize, reify)

        val oldCount = dao.count()

        dao.insert(Student(0, "Bird", "338 93283", LocalDate.now().minusYears(30), Email("a@b.com")))

        val count = dao.count()

        assertEquals(count, oldCount + 1)
        dao.close();

        val students = dao.findAll()
        students.forEach(::println)

    }

    @Test
    fun testCourseInMemory() {
        val course = Course(0L, "Intro to Math", "Math-101", LocalDate.now())
        val dao = ALaCarteStrategyCourseDAO()
        dao.insert(course)
        dao.insert(Course(0L, "Intro to Math", "Math-101", LocalDate.now()));

        val courses = dao.findAll()
        courses.forEach(::println)

        assertEquals(2, courses.count())
    }

    @Test
    fun testCourseToFile() {
        val course = Course(0L, "Intro to Math", "Math-101", LocalDate.now())
        val fileName =  "/tmp/geeedyap.json"
        val reifier = {
            val path = Paths.get(fileName)
            path.reifyStateJson<Course>(fileName)
        }

        val serialize = {state: State<Course> ->
            val path = Paths.get(fileName)
            path.serializeStateJson(fileName, state)
        }

        val dao = ALaCarteStrategyCourseDAO(serialize = serialize, reify = reifier)
        dao.clear()
        dao.insert(course)
        dao.insert(Course(0L, "Intro to Math", "Math-101", LocalDate.now()));

        val courses = dao.findAll()
        courses.forEach(::println)

        dao.close()
        assertEquals(2, courses.count())
    }
}