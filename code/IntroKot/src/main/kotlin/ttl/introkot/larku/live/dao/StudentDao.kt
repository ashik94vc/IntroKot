package ttl.introkot.larku.live.dao

import ttl.introkot.larku.live.domain.Student
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author whynot
 */
class StudentDao {

    val students = ConcurrentHashMap<Int, Student>()
    val nextId = AtomicInteger(1)

    fun insert(student: Student ) : Student {
        val newId = nextId.getAndIncrement()
        val newStudent = student.copy(id = newId)
        students.put(newId, newStudent)

        return newStudent
    }

    fun delete(id: Int) : Boolean {
        return students.remove(id) != null
    }

    fun update(student: Student) : Boolean {
        return students.computeIfPresent(student.id) {id, st -> student} != null
    }

    fun get(id: Int) : Student? {
        return students.get(id)
    }

    fun getAll() : List<Student> {
        return ArrayList<Student>(students.values)
    }
}