package ttl.introkot.larku.dao.file.fancy.composition

import ttl.introkot.larku.dao.BaseDAO
import ttl.introkot.larku.domain.Student

/**
 * @author whynot
 */
open class StrategicStatefulStudentDAO(val strategy: PersistenceStrategy<Student>) : BaseDAO<Student> {

    private var state: State<Student> = strategy.reify()

    open override fun insert(student: Student): Student {
        val nid = state.nextId.getAndIncrement()
        val studentWithId = student.copy(id = nid)
        state.items[nid] = studentWithId

        return studentWithId;
    }

    open override fun update(id: Long, student: Student): Boolean {
        if(state.items.containsKey(student.id)) {
            state.items[student.id] = student;
            return true;
        }

        return false;
    }


    open override fun delete(id: Long): Boolean {
        if(state.items.containsKey(id)) {
            state.items.remove(id);
            return true;
        }
        return false;
    }

    open override fun find(id: Long) = state.items[id];

    open override fun count() = state.items.size.toLong()

    open override fun clear() {
        state.items.clear()
        state.nextId.set(1)
    }

    open override fun findAll() : List<Student> {
        return state.items.values.toList()
    }

    open override fun close() {
        strategy.serialize(state);
    }
}
