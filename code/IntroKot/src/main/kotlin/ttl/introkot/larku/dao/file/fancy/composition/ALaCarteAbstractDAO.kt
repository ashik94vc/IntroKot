package ttl.introkot.larku.dao.file.fancy.composition

import ttl.introkot.larku.dao.BaseDAO
import ttl.introkot.larku.domain.Course
import ttl.introkot.larku.domain.Student

/**
 * Here we have done away with the interface.  This class is an abstract
 * Generic BaseDAO class. It takes in two functions as agruments for the two strategies.
 * It provides defaults such that serialize does nothing and the reify returns a new State object.
 * That is, the default behaviour is to become an InMemoryStudentDAO!
 *
 * It provides all BaseDAO functionality *except* for create and update, which require
 * knowledge of actual type to get at the id field of a student.
 * @author whynot
 */
abstract class ALaCarteAbstractDAO<T>(
        val serialize: (State<T>) -> Unit = {},
        reify: () -> State<T> = { State() }) : BaseDAO<T> {

    protected var state: State<T> = reify()


    open override fun update(id: Long, input: T): Boolean {
        if (state.items.containsKey(id)) {
            state.items[id] = input;
            return true;
        }

        return false;
    }

    open override fun delete(id: Long): Boolean {
        if (state.items.containsKey(id)) {
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

    open override fun findAll(): List<T> {
        return state.items.values.toList()
    }

    open override fun close() {
        serialize(state);
    }
}
