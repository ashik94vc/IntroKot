package ttl.introkot.larku.dao.file.fancy.composition

import ttl.introkot.larku.domain.Course

/**
 * Here we have done away with the interface.  This class takes in two
 * functions for the two strategies.  It provides defaults such that
 * serialize does nothing and the reify returns a new State object.
 * That is, the default behaviour is to become an InMemory DAO!
 * @author whynot
 */
open class ALaCarteStrategyCourseDAO(
        serialize: (State<Course>) -> Unit = {},
        reify: () -> State<Course> = { State() })
                                : ALaCarteAbstractDAO<Course> (serialize, reify){

    open override fun insert(input: Course): Course {
        val nid = state.nextId.getAndIncrement()
        val studentWithId = input.copy(id = nid)
        state.items[nid] = studentWithId

        return studentWithId;
    }

}
