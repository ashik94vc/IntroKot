package ttl.introkot.larku.dao.file.fancy.inheritance

import ttl.introkot.larku.dao.jMapper
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author whynot
 */
class JSONFileStudentDAO(val inputFileName: String) : AbstractFileStudentDAO() {

    init {
        //Open the file and read any saved students.
        //If the file does not exist, then create it
        val filePath = Paths.get(inputFileName)
        state = filePath.reifyState()
    }

    override fun close() {
        val filePath = Paths.get(inputFileName)
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
    private fun Path.reifyState(): State {
        if (Files.exists(this)) {
            val p = Paths.get(inputFileName)
            val fileReader = FileReader(inputFileName)
            val state = jMapper.readValue(fileReader, State::class.java)

            return state
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
    private fun Path.serializeState(state: State) {
        val mapper = jMapper
        val json = mapper.writeValueAsString(state)

        val fileWriter = FileWriter(inputFileName)
        fileWriter.use {
            val json = mapper.writeValue(it, state)
        }
    }
}
