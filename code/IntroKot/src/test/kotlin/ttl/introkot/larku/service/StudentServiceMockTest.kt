package ttl.introkot.larku.service

import io.mockk.Answer
import io.mockk.Call
import io.mockk.called
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import ttl.introkot.larku.dao.inmemory.InMemoryStudentDAO
import ttl.introkot.larku.domain.Email
import ttl.introkot.larku.domain.Student
import java.time.LocalDate

/**
 * @author whynot
 */
class StudentServiceMockTest {

    val mockDAO = mockk<InMemoryStudentDAO>()

    @InjectMockKs
    val studentService = StudentService(mockDAO)

    val goodId = 10L
    val badId = 100000L

    val otherId = 3838L

    val foundStudent = Student(goodId, "Joe", "282 9839 9393", LocalDate.of(1999, 10, 10))

    @BeforeEach
    fun init() {
        clearAllMocks()
//        every{mockDAO.find(any())} answers { it: Call ->
//            //Can create  return object dynamically
//            val id = it.invocation.args[0] as Long
//            Student(id, "eie", "e939 9393", LocalDate.now())
//        }
//
//        every {mockDAO.find(badId) } returns null
//        every {mockDAO.find(goodId) } returns foundStudent


    }

    @Nested
    inner class FindTests {

        @Test
        fun findAllStudents() {
            every{mockDAO.findAll()} returns listOf(foundStudent)
            val students = studentService.getAllStudents()
            assertEquals(1, students.size)

            verify(exactly = 1) {mockDAO.findAll()}

            confirmVerified()
        }

        @Test
        fun findStudentGoodId() {
            every { mockDAO.find(goodId) } returns foundStudent

            val s = studentService.findById(goodId)
            assertNotEquals(null, s)
            assertEquals(goodId, s?.id)

            verify {
                mockDAO.find(goodId)
            }

            confirmVerified(mockDAO)
        }

        @Test
        fun testFindStudentBadId() {
            every { mockDAO.find(badId) } returns null

            val s = studentService.findById(badId)
            assertEquals(null, s)

            verify {
                mockDAO.find(badId)
            }

            confirmVerified(mockDAO)
        }
    }

    @Nested
    inner class CreateTests {
        @Test
        fun createStudentGood() {
            val student = Student(0, "Joey", "383 838 393", LocalDate.now().minusYears(19),
                    Email("a@b.com"))

            every { mockDAO.insert(student) } answers {
                val student2 = student.copy(id = goodId)
                student2
            }
            val newStudent = studentService.createStudent(student);

            assertEquals(goodId, newStudent.id)

            verify(exactly = 1) { mockDAO.insert(student) }

            confirmVerified()
        }

        //Throw a "Too Young" Exception
        //This one doesn't need the Mock to be initialized at all
        //because the Exception should get thrown before we hit the DAO
        @Test
        fun createStudentTooYoung() {
            Assertions.assertThrows(RuntimeException::class.java) {
                val student = Student(0, "Joey", "383 838 393", LocalDate.now().minusYears(15), Email("a@b.com"))
                studentService.createStudent(student)
            }
        }
    }

    @Nested
    inner class UpdateTests {
        @Test
        fun updateStudentGood() {
            val student = foundStudent

            every { mockDAO.update(student.id, student) } answers {
                true
            }
            val done = studentService.updateStudent(student);

            assertEquals(done, true)

            verify(exactly = 1) { mockDAO.update(student.id, student) }

            confirmVerified()
        }

        /**
        * Shows another way to deal with Exceptions -
        * In this case, the exception will be thrown before
        * we hit the Mock, so we want to verify that the
        * Mock was not called
        */
        @Test
        fun updateInvalidStudent() {
            val student = Student(0, "Joey", "383 838 393", LocalDate.now().minusYears(15), Email("a@b.com"))

            every { mockDAO.update(student.id, student) } answers {
                true
            }
            try {
                val done = studentService.updateStudent(student);

                assertEquals(done, false)
            }catch(e: RuntimeException) {
                verify { mockDAO wasNot called }
                confirmVerified()
            }
        }
    }

    @Nested
    inner class DeleteTests
    {
        @Test
        fun testDeleteGood() {
            every { mockDAO.delete(goodId)} returns true
            val done = studentService.removeStudent(goodId)
            assertTrue(done)

            verify(exactly = 1) {mockDAO.delete(goodId)}
            confirmVerified()
        }

        @Test
        fun testDeleteBadStudent() {
            every { mockDAO.delete(badId)} returns false
            val done = studentService.removeStudent(badId)
            assertFalse(done)

            verify(exactly = 1) {mockDAO.delete(badId)}
            confirmVerified()
        }
    }
}