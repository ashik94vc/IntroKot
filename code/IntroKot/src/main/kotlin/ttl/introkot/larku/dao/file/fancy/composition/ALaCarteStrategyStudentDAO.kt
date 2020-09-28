package ttl.introkot.larku.dao.file.fancy.composition

import ttl.introkot.larku.domain.Student

/**
 * Here we have done away with the interface.  This class takes in two
 * functions for the two strategies.  It provides defaults such that
 * serialize does nothing and the reify returns a new State object.
 * That is, the default behaviour is to become an InMemoryStudentDAO!
 * @author whynot
 */
open class ALaCarteStrategyStudentDAO(
        serialize: (State<Student>) -> Unit = {},
        reify: () -> State<Student> = { State() }) : ALaCarteAbstractDAO<Student> (serialize, reify){

    open override fun insert(student: Student): Student {
        val nid = state.nextId.getAndIncrement()
        val studentWithId = student.copy(id = nid)
        state.items[nid] = studentWithId

        return studentWithId;
    }
}
