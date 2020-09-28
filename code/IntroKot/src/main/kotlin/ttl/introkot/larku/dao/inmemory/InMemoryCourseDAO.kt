package ttl.introkot.larku.dao.inmemory

import ttl.introkot.larku.dao.BaseDAO
import ttl.introkot.larku.domain.Student
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

/**
 * @author whynot
 */
class InMemoryStudentDAO : BaseDAO<Student> {
    private val students = ConcurrentHashMap<Long, Student>()
    private val nextId = AtomicLong(1);

    override fun insert(student: Student): Student {
        val nid = nextId.getAndIncrement()
        val studentWithId = student.copy(id = nid)
        students[nid] = studentWithId

        return studentWithId;
    }

    override fun update(id: Long, student: Student): Boolean {
        if(students.containsKey(student.id)) {
            students[student.id] = student;
            return true;
        }

        return false;
    }


    override fun delete(id: Long): Boolean {
        if(students.containsKey(id)) {
            students.remove(id);
            return true;
        }
        return false;
    }

    override fun find(id: Long) = students[id];

    override fun count() = students.size.toLong()

    override fun clear() = students.clear()

}
