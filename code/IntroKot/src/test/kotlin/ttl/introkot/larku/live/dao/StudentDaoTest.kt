package ttl.introkot.larku.live.dao

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ttl.introkot.larku.live.dao.StudentDao
import ttl.introkot.larku.live.domain.Student
import java.time.LocalDate

/**
 * @author whynot
 */
class StudentDaoTest {

    lateinit var studentDao: StudentDao

    @BeforeEach
    fun init() {
        studentDao = StudentDao()
    }

    @Test
    fun testInsert() {
        val student = Student(0, "Markus", LocalDate.of(2000, 10, 20))
        val newStudent = studentDao.insert(student)

        assertEquals(1, newStudent.id)
    }

    @Test
    fun testDelete() {
        val student = Student(0, "Markus", LocalDate.of(2000, 10, 20))
        studentDao.insert(student)

        val newStudent = studentDao.get(1)  ?: fail("New Student should not be null")

        assertEquals("Markus", newStudent.name)

        val b = studentDao.delete(1)

        assertTrue(b)
        assertEquals(0, studentDao.getAll().size)
    }
}