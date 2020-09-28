package ttl.introkot.larku.live.service

import ttl.introkot.larku.live.dao.StudentDao
import ttl.introkot.larku.live.domain.Student
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * @author whynot
 */
class StudentService {

    val studentDao = StudentDao()

    fun createStudent(student: Student): Student {
//        if(!validateStudent(student)) {
        if (!student.isValid()) {
            throw RuntimeException("Student is too young: $student")
        }
        val newStudent = studentDao.insert(student)
        return newStudent
    }

    private fun validateStudent(student: Student): Boolean {
        return student.dob.until(LocalDate.now(), ChronoUnit.YEARS) >= 18
    }

    private fun Student.isValid(): Boolean {
        return dob.until(LocalDate.now(), ChronoUnit.YEARS) >= 18
    }
}