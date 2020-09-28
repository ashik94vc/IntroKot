package ttl.introkot.larku.dao.file.fancy.composition

import ttl.introkot.larku.dao.jMapper
import ttl.introkot.larku.domain.Student
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author whynot
 */
class JsonStrategy(val inputFileName: String) : PersistenceStrategy<Student> {

    override fun serialize(state: State<Student>) {
        val filePath = Paths.get(inputFileName)
        filePath.serializeState(state)
    }

    override fun reify() : State<Student> {
        val filePath = Paths.get(inputFileName)
        val state = filePath.reifyState<Student>()
        return state
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
    private fun <T>Path.reifyState(): State<T> {
        if (Files.exists(this)) {
            val p = Paths.get(inputFileName)
            val fileReader = FileReader(inputFileName)
            val state = jMapper.readValue(fileReader, State::class.java)

            return state as State<T>
        } else {
            return State()
        }
    }

    /**
     * Write the give State out the the file that is connected
     * to the Path on which this method has been called
     *
     * @receiver Path object pointing to the file
     * @param state The State to be serialized
     */
    private fun <T> Path.serializeState(state: State<T>) {
        val mapper = jMapper
        val json = mapper.writeValueAsString(state)

        val fileWriter = FileWriter(inputFileName)
        fileWriter.use {
            val json = mapper.writeValue(it, state)
        }
    }
}
