package ttl.introkot.larku.dao.fancy.composition

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ttl.introkot.larku.dao.file.fancy.composition.JsonStrategy
import ttl.introkot.larku.dao.file.fancy.composition.PersistenceStrategy
import ttl.introkot.larku.dao.file.fancy.composition.SerialStrategy
import ttl.introkot.larku.dao.file.fancy.composition.StrategicStatefulStudentDAO
import ttl.introkot.larku.domain.Email
import ttl.introkot.larku.domain.Student
import java.time.LocalDate

/**
 * @author whynot
 */

class StrategicDAOTest {

    @Test
    fun testJsonStrategyDAO() {
        val strategy = JsonStrategy("/tmp/strategyStudents.json")
        val dao = StrategicStatefulStudentDAO(strategy)

        val oldCount = dao.count()

        dao.insert(Student(0, "Bird", "338 93283", LocalDate.now().minusYears(30), Email("a@b.com")))

        val count = dao.count()
        dao.close();

        assertEquals(oldCount, count - 1)

    }

    @Test
    fun testSerialStrategyDAO() {
        val strategy = SerialStrategy("/tmp/strategyStudents.ser")
        val dao = StrategicStatefulStudentDAO(strategy)

        val oldCount = dao.count()

        dao.insert(Student(0, "Bird", "338 93283", LocalDate.now().minusYears(30), Email("a@b.com")))

        val count = dao.count()
        dao.close();

        assertEquals(oldCount, count - 1)

    }

    @Test
    fun testSerialPrint() {
        val strategy = SerialStrategy("/tmp/strategyStudents.ser")

        var students = getAll(strategy)
        students.forEach(::println)


        println("Json")
        val js = JsonStrategy("/tmp/strategyStudents.json")
        students = getAll(js)
        students.forEach(::println)


    }

    fun getAll(strategy: PersistenceStrategy<Student>) : List<Student>{
        val dao = StrategicStatefulStudentDAO(strategy)
        val students = dao.findAll()
        return students;
    }
}
