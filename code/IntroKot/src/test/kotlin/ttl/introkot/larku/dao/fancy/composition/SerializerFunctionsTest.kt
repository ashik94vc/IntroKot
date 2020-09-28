package ttl.introkot.larku.dao.fancy.composition

import org.junit.jupiter.api.Test
import ttl.introkot.larku.dao.file.fancy.composition.*
import ttl.introkot.larku.domain.Course
import ttl.introkot.larku.domain.Email
import ttl.introkot.larku.domain.Student
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * @author whynot
 */

class SerializerFunctionsTest
{
    @Test
    fun testFuntionalizing() {
        val fileName = "/tmp/really.json"
        val serializer = jsonSerializer<Student>(fileName)

        val reifier = jsonReifier(fileName)

        //An "empty" reifier, which will give you a new State
        val emptyReifier:() -> State<Student> = {State()}

        val dao = ALaCarteStrategyStudentDAO()
//        val dao = ALaCarteStrategyStudentDAO(serializer, reifier)
//        val dao = StrategyALaCarteStudentDAO(serializer, emptyReifier)

        dao.insert(Student(0, "Cimrun", "3838 30 -595", LocalDate.of(1487, 10, 2), Email("bc@xyz.gov")))

        val students = dao.findAll()
        students.forEach(::println)

        dao.close()
    }

    @Test
    fun testInitializeDAO() {
        //A reifier to initialize the dao
        val reifier:() -> State<Student> = {
            val students = ConcurrentHashMap<Long, Student>()
            students.putPairs(
                    1L to Student(0, "Cimrun", "3838 30 -595", LocalDate.of(1487, 10, 2), Email("bc@xyz.gov")),
                    2L to Student(0, "Shirly", "3838 30 -595", LocalDate.of(1978, 10, 2), Email("bc@xyz.gov")),
                    3L to Student(0, "Carziek", "3838 30 -595", LocalDate.of(1988, 10, 2), Email("bc@xyz.gov")),
                    4L to Student(0, "Volander", "3838 30 -595", LocalDate.of(2002, 10, 2), Email("bc@xyz.gov"))
            )

            val nextId = AtomicLong(5L)
            State(students, nextId)
        }

        val dao = ALaCarteStrategyStudentDAO(reify = reifier)

        val students = dao.findAll()
        students.forEach(::println)

        dao.close()
    }

    @Test
    fun testInitializeCourseDAO() {
        //A reifier to initialize the dao
        val reifier:() -> State<Course> = {
            val courses = ConcurrentHashMap<Long, Course>()
            courses.putPairs(
                    1L to Course(0L, "Cimrun", "Cim-101", LocalDate.of(1487, 10, 2)),
                    2L to Course(0L, "Astronomy for Dummies", "Astro-101", LocalDate.of(1487, 10, 2)),
                    3L to Course(0L, "Advanced Calculus", "Calc-404", LocalDate.now().minusYears(2)),
                    4L to Course(0L, "Basket Weaving", "Bktw-101", LocalDate.now().minusMonths(5))
            )

            val nextId = AtomicLong(5L)
            State(courses, nextId)
        }

        val dao = ALaCarteStrategyCourseDAO(reify = reifier)

        val courses = dao.findAll()
        courses.forEach(::println)

        dao.close()
    }

}