package ttl.introkot.larku.service.fancy.composition

import org.junit.jupiter.api.Test
import ttl.introkot.larku.dao.file.fancy.composition.ALaCarteStrategyStudentDAO
import ttl.introkot.larku.dao.file.fancy.composition.State
import ttl.introkot.larku.dao.file.fancy.composition.jsonSerializer
import ttl.introkot.larku.dao.file.fancy.composition.putPairs
import ttl.introkot.larku.domain.Email
import ttl.introkot.larku.domain.Student
import ttl.introkot.larku.service.StudentService
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

/**
 * @author whynot
 */
class FabuloutStudentServiceTest {

    @Test
    fun testFabulousService() {

        //A reifier to initialize the dao
        val reifier: () -> State<Student> = {
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

        val fileName =  "/tmp/geeedyap.json"
        val serialize = jsonSerializer<Student>(fileName)

        val dao = ALaCarteStrategyStudentDAO(serialize = serialize, reify = reifier)
        val studentService = StudentService(dao)

        val students = studentService.getAllStudents()
        students.forEach(::println)


        studentService.close()
    }
}