package ttl.introkot.larku.dao;

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import ttl.introkot.larku.domain.Email
import ttl.introkot.larku.domain.Student
import java.time.LocalDate

/**
 * @author whynot
 */

class SerializableStudentDAOTest
{
    lateinit var studentDAO: BaseDAO<Student>

    @BeforeEach
    fun init() {
        studentDAO = DAOFactory.fileStudentDAO();
    }

    @AfterEach
    fun shutdown() {
        studentDAO.clear()
        studentDAO.close()
    }

    @Test
    fun testCreateStudentAllGood() {
        val newStudent = Student(0, "Joe Zawinal", "383 939 9393", LocalDate.of(1960, 8, 10), Email("abc@d.com"))

        val oldCount = studentDAO.count()

        val savedStudent = studentDAO.insert(newStudent);

        assertEquals(oldCount, studentDAO.count() - 1)

        println("savedStudent: $savedStudent");
    }

    @Test
    fun testCreateStudentBadEmail() {
        assertThrows(RuntimeException::class.java, Executable{
            val newStudent = Student(0, "Joe Zawinal", "383 939 9393", LocalDate.of(1960, 8, 10), Email("@d.com"))

            val savedStudent = studentDAO.insert(newStudent);

        })
    }

    @Test
    fun testFindStudentGoodId() {
        val newStudent = Student(0, "Joe Zawinal", "383 939 9393", LocalDate.of(1960, 8, 10), Email("a@d.com"))
        val savedStudent = studentDAO.insert(newStudent);
        val foundStudent = studentDAO.find(savedStudent.id)
        assertTrue(foundStudent != null)
    }

    @Test
    fun testFindStudentBadId() {
        val foundStudent = studentDAO.find(1000L)
        assertTrue(foundStudent == null)
    }

}

fun main() {
    var dao = DAOFactory.serialStatefulStudentDAO()
//    dao.clear()

    dao.insert(Student(0, "Joey", "383 838 393", LocalDate.of(1960, 10, 25), Email("a@b.com")))
    dao.insert(Student(0, "Sanjay Ghosh", "383 4809 393", LocalDate.of(1970, 8, 25), Email("a@b.com")))
    dao.insert(Student(0, "Nancy Cheng", "+45 383 838 393", LocalDate.of(1954, 10, 25), Email("a@b.com")))
    dao.insert(Student(0, "Jose Rosario", "+84 393899 999", LocalDate.of(1990, 3, 25), Email("a@b.com")))
    var count = dao.count()
    println("count: $count")

    dao.insert(Student(0, "Farkkun", "38383 93", LocalDate.now().minusYears(20), Email("a@b.com")))

    dao.close()

    dao = DAOFactory.fileStudentDAO()

    count = dao.count()
    println("2nd count: $count")

    val student = dao.find(5L)
    println("new student $student")
    dao.close()

    dao = DAOFactory.fileStudentDAO()

    count = dao.count()
    println("3 count: $count")
    var students = dao.findAll()
    students.forEach(::println)


}
