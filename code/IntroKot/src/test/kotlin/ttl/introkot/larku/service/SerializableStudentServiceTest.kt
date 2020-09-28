package ttl.introkot.larku.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import ttl.introkot.larku.dao.DAOFactory
import ttl.introkot.larku.domain.Email
import ttl.introkot.larku.domain.Student
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate

/**
 * @author whynot
 */
class SerializableStudentServiceTest {

    lateinit var studentService: StudentService

    val fileName = "/tmp/simpleserial.ser"
    @BeforeEach
    fun init() {
        studentService = DAOFactory.fileStudentService(fileName)
        studentService.dao.clear()
        //create some students.  Kind of defeats the purpose of
        //the createStudent test below, but what the heck
        studentService.createStudent(Student(0, "Joey", "383 838 393", LocalDate.of(1960, 10, 25), Email("a@b.com")))
        studentService.createStudent(Student(0, "Sanjay Ghosh", "383 4809 393", LocalDate.of(1970, 8, 25), Email("a@b.com")))
        studentService.createStudent(Student(0, "Nancy Cheng", "+45 383 838 393", LocalDate.of(1954, 10, 25), Email("a@b.com")))
        studentService.createStudent(Student(0, "Jose Rosario", "+84 393899 999", LocalDate.of(1990, 3, 25), Email("a@b.com")))

    }

    @Test
    fun testCreateFileOnClose() {
        //Delete the file
        val path = Paths.get(fileName)
        Files.deleteIfExists(path);

        val student = Student(0, "Joey", "383 838 393", LocalDate.now().minusYears(19), Email("a@b.com"))

        val newStudent = studentService.createStudent(student);

        assertNotEquals(0, newStudent.id)
        studentService.close();

        assertTrue(Files.exists(path) && Files.size(path) > 0)
    }

    @Test
    fun createStudent() {
        val student = Student(0, "Joey", "383 838 393", LocalDate.now().minusYears(19), Email("a@b.com"))

        val newStudent = studentService.createStudent(student);

        assertNotEquals(0, newStudent.id)
    }

    //Throw a "Too Young" Exception
    @Test
    fun createStudentTooYoung() {
        assertThrows(RuntimeException::class.java, Executable {
            val student = Student(0, "Joey", "383 838 393", LocalDate.now().minusYears(15), Email("a@b.com"))
            studentService.createStudent(student)

        })
    }

    /**
     * Shows a technique for failing if we get a null when we are not expecting it.
     * Has the great side benefit that our variables are always Non Nullable even
     * when calling functions returning Nullable types.
     */
    @Test
    fun updateStudent() {
        val oldStudent = studentService.findById(1) ?: fail("OldStudent should not be null")

        oldStudent.name = "Firoza"
        val bool = studentService.updateStudent(oldStudent)

        val newStudent = studentService.findById(oldStudent.id) ?: fail("NewStudent should not be null")

        assertEquals("Firoza", newStudent.name)
    }


    @Test
    fun deleteStudent() {
        val oldStudent = studentService.findById(1) ?: fail("OldStudent should not be null")

        val bool = studentService.removeStudent(oldStudent.id);

        assertTrue(bool);
    }

    @Test
    fun deleteNonExistingStudent() {
        val bool = studentService.removeStudent(1000)

        assertFalse(bool);
    }
}