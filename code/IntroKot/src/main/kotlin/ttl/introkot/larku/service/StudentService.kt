package ttl.introkot.larku.service

import ttl.introkot.larku.dao.BaseDAO
import ttl.introkot.larku.domain.Student
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * @author whynot
 */
class StudentService(val dao: BaseDAO<Student>) {

    fun createStudent(student: Student): Student {
        if (student.isValid()) {
            val newStudent = dao.insert(student);
            return newStudent
        }

        throw RuntimeException("Too Young: $student")
    }


    fun updateStudent(student: Student): Boolean {
        if (student.isValid()) {
            return dao.update(student.id, student)
        }
        return false
    }

    fun removeStudent(id: Long) = dao.delete(id)

    fun findById(id: Long): Student? {
        return dao.find(id);
    }

    fun getAllStudents() : List<Student> {
        return dao.findAll()
    }

    fun close() {
        dao.close()
    }

    /**
     * Extension function on Student to check for valid students.
     * Good example of why you might want to have extension functions
     * for your own types.  So you can keep these business rules
     * outside of your domain class.
     * The advantage you get is being able to say student.isValid()
     */
    fun Student.isValid(): Boolean {
        //check for age
        val now = LocalDate.now().plusDays(1);
        val age = dob.until(now, ChronoUnit.YEARS)
        return age > 18
    }
}
