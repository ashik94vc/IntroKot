package ttl.introkot.larku.dao.file.fancy.composition

import ttl.introkot.larku.dao.jMapper
import ttl.introkot.larku.domain.Student
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap
import javax.swing.UIManager.put

/**
 * @author whynot
 */

fun <T> jsonSerializer(fileName: String) : (state: State<T>) -> Unit {
    val result: (State<T>) -> Unit  = { state ->
        val path = Paths.get(fileName)
        path.serializeStateJson(fileName, state)
    }

    return result;
}

fun jsonReifier(fileName: String) : () -> State<Student> {
    val result: () -> State<Student>  = {
        val path = Paths.get(fileName)
        path.reifyStateJson(fileName)
    }

    return result;
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
fun <T> Path.reifyStateJson(inputFileName: String) =
    if (Files.exists(this)) {
        val p = Paths.get(inputFileName)
        val fileReader = FileReader(inputFileName)
        val state = jMapper.readValue(fileReader, State::class.java)

        @Suppress("UNCHECKED_CAST")
        state as State<T>
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
fun <T> Path.serializeStateJson(inputFileName: String, state: State<T>) {
    val mapper = jMapper
//    val json = mapper.writeValueAsString(state)

    val fileWriter = FileWriter(this.toString())
    fileWriter.use {
        val json = mapper.writeValue(it, state)
    }
}


/**
 * Allow us to initialize a
 */
fun <K, V> ConcurrentHashMap<K, V>.putPairs(vararg pairs: Pair<K, V>) {
    for(p in pairs) {
        put(p.first, p.second)
    }
}
