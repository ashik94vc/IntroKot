package ttl.introkot.larku.dao

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ttl.introkot.larku.domain.Email
import ttl.introkot.larku.domain.Student
import java.io.FileReader
import java.io.FileWriter
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong

/**
 * @author whynot
 */
class JacksonMapperTest {

    protected data class State(val students: ConcurrentMap<Long, Student> = ConcurrentHashMap(),
                               val nextId: AtomicLong = AtomicLong(1)) : Serializable

    private val mapper = jMapper

    @Test
    fun serializeState() {

        val state = State()
        state.students[1L] = Student(1, "Frumtpy", "3838 9", LocalDate.now().minusYears(30), Email("a@b.com"))
        state.nextId.incrementAndGet()

        val json = mapper.writeValueAsString(state)

        println("json: $json")
    }

    @Test
    fun serializeStateToFileAndThenReadItBack() {

        val state = State()
        state.students[1L] = Student(1, "Frumtpy", "3838 9", LocalDate.now().minusYears(30), Email("a@b.com"))
        state.students[2L] = Student(2, "Humpty", "3838 9", LocalDate.now().minusYears(200), Email("a@b.org"))
        state.students[3L] = Student(3, "Dompty", "3838 9", LocalDate.now().minusYears(50), Email("a@b.es"))
        state.nextId.incrementAndGet()

        val json = mapper.writeValueAsString(state)
        println("json: $json")

        val fileName = "/tmp/teststudents.json";

        val fileWriter = FileWriter(fileName)
        fileWriter.use {
            val json = mapper.writeValue(it, state)
        }

        val p = Paths.get(fileName)
        assertTrue(Files.exists(p) && Files.size(p) > 0)

        val newState = reifyState(fileName)

        println("newState: $newState")

        assertEquals(state.nextId.get(), newState.nextId.get())
    }

    private fun reifyState(fileName: String): State {
        val p = Paths.get(fileName)
        if (Files.exists(p)) {
            val fileReader = FileReader(fileName)
            val state = mapper.readValue(fileReader, State::class.java)

            return state
        } else {
            return State()
        }
    }
}