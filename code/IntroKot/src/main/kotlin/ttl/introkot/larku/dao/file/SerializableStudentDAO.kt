package ttl.introkot.larku.dao.file

import ttl.introkot.larku.dao.BaseDAO
import ttl.introkot.larku.domain.Student
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

/**
 * A DAO which will save the entire state to a file as a java.io.Serialized object
 * when 'close' is called.
 * On startup, it will initialize the State from the file if it exists, else start
 * with a new State
 * @author whynot
 */
class SerializableStudentDAO(val fileName: String) : BaseDAO<Student> {
    private data class State(val students: ConcurrentHashMap<Long, Student> = ConcurrentHashMap(),
                             val nextId: AtomicLong = AtomicLong(1)) : Serializable

    private var state: State

    init {
        //Initial our state.
        val filePath = Paths.get(fileName)
        state = filePath.reifyState()
    }

    override fun insert(input: Student): Student {
        val nid = state.nextId.getAndIncrement()
        val studentWithId = input.copy(id = nid)
        state.students[nid] = studentWithId

        return studentWithId;
    }

    override fun update(id: Long, input: Student): Boolean {
        if (state.students.containsKey(input.id)) {
            state.students[input.id] = input;
            return true;
        }

        return false;
    }


    override fun delete(id: Long): Boolean {
        if (state.students.containsKey(id)) {
            state.students.remove(id);
            return true;
        }
        return false;
    }

    override fun find(id: Long) = state.students[id];

    override fun findAll(): List<Student> {
        return state.students.values.toList()
    }

    override fun count() = state.students.size.toLong()

    override fun clear() {
        state = State()
    }

    override fun close() {
        val filePath = Paths.get(fileName)
        filePath.serializeState(state)
    }

    /**
     * The file is the one connected to the Path
     * on which this function has been called, i.e. 'this'
     *
     * If the file exists, then open and read it.
     * The return of the if will be the return of the
     * inputStream.use, which in turn will be the
     * return of the outputStream.use, which
     * will, hopefully, but the State object read in.
     * The 'else' branch will return a new State
     *
     * @receiver Path object pointing to file
     * @return Either the old State if it exists, or a new State
     */
    private fun Path.reifyState() = if (Files.exists(this)) {
        val inputStream = Files.newInputStream(this)
        inputStream.use { it: InputStream ->
            val objInputStream = ObjectInputStream(inputStream);
            objInputStream.use {
                objInputStream.readObject() as State
            }
        }
    } else {
        State()
    }

    /**
     * Write the give State out the the file that is connected
     * to the Path on which this method has been called
     *
     * @receiver Path object pointing to the file
     * @param state The State to be serialized
     */
    private fun Path.serializeState(state: State) {
        val outputStream = Files.newOutputStream(this)
        //use is the kotlin way of doing try with resources
        outputStream.use {
            val objOutputStream = ObjectOutputStream(outputStream);
            objOutputStream.use {
                objOutputStream.writeObject(state)
            }
        }
    }
}
