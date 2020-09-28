package ttl.introkot.larku.live.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.function.Executable
import ttl.introkot.larku.live.domain.Student
import java.time.LocalDate

/**
 * @author whynot
 */
class StudentServiceTest {

    @Test
    fun testCreateStudent() {
        val studentService = StudentService()
        val student = Student(0, "Mary", LocalDate.of(2000, 10, 10))
        val newStudent = studentService.createStudent(student)

        assertEquals(1, newStudent.id)
    }

    @Test
    fun testCreateInvalidStudent() {
        assertThrows(RuntimeException::class.java) {
            val studentService = StudentService()
            val student = Student(0, "Mary", LocalDate.of(2010, 10, 10))
            val newStudent = studentService.createStudent(student)

            assertEquals(1, newStudent.id)
        }
    }
}