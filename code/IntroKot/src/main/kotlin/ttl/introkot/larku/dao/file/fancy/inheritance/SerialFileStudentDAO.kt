package ttl.introkot.larku.dao.file.fancy.inheritance

import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Implementation of
 * @author whynot
 */
class SerialFileStudentDAO(val inputFileName: String) : AbstractFileStudentDAO() {

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
    private fun Path.reifyState() = if (Files.exists(this)) {
        val inputStream = Files.newInputStream(this)
        inputStream.use { it: InputStream ->
            val objInputStream = ObjectInputStream(inputStream);
            objInputStream.use {
                objInputStream.readObject() as AbstractFileStudentDAO.State
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
        outputStream.use {
            val objOutputStream = ObjectOutputStream(outputStream);
            objOutputStream.use {
                objOutputStream.writeObject(state)
            }
        }
    }
}
