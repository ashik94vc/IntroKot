package ttl.introkot.larku.dao.file.fancy.inheritance

import ttl.introkot.larku.dao.BaseDAO
import ttl.introkot.larku.domain.Student
import java.io.Serializable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

/**
 * An Abstract class that deal with most of what it means to be a
 * BaseDAO<Student>, including maintaining the State.
 * But it delegates persistence to sub classes.
 * @author whynot
 */
open abstract class AbstractFileStudentDAO : BaseDAO<Student> {
    protected class State : Serializable {
        val students = ConcurrentHashMap<Long, Student>()
        val nextId = AtomicLong(1);
    }

    protected lateinit var state: State

    override fun insert(input: Student): Student {
        val nid = state.nextId.getAndIncrement()
        val studentWithId = input.copy(id = nid)
        state.students[nid] = studentWithId

        return studentWithId;
    }

    override fun update(id: Long, input: Student): Boolean {
        if(state.students.containsKey(input.id)) {
            state.students[input.id] = input;
            return true;
        }

        return false;
    }


    override fun delete(id: Long): Boolean {
        if(state.students.containsKey(id)) {
            state.students.remove(id);
            return true;
        }
        return false;
    }

    override fun find(id: Long) = state.students[id];

    override fun count() = state.students.size.toLong()

    override fun clear() {
        state.students.clear()
        state.nextId.set(1)
    }

    override fun findAll() : List<Student> {
        return state.students.values.toList()
    }

    abstract override fun close()
}
