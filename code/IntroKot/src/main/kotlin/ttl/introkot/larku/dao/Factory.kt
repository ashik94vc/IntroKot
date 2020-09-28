package ttl.introkot.larku.dao

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ttl.introkot.larku.dao.file.SerializableStudentDAO
import ttl.introkot.larku.dao.file.fancy.composition.JsonStrategy
import ttl.introkot.larku.dao.file.fancy.composition.SerialStrategy
import ttl.introkot.larku.dao.file.fancy.composition.StrategicStatefulStudentDAO
import ttl.introkot.larku.dao.file.fancy.composition.ALaCarteStrategyStudentDAO
import ttl.introkot.larku.dao.file.fancy.inheritance.JSONFileStudentDAO
import ttl.introkot.larku.dao.file.fancy.inheritance.SerialFileStudentDAO
import ttl.introkot.larku.dao.inmemory.InMemoryStudentDAO
import ttl.introkot.larku.domain.Student
import ttl.introkot.larku.service.StudentService

/**
 * @author whynot
 */

class DAOFactory {
    companion object {


        @JvmStatic
        fun studentDAO(): BaseDAO<Student> {
            return InMemoryStudentDAO()
        }

        @JvmStatic
        fun studentService(): StudentService {
            return StudentService(studentDAO());
        }

        val simpleSerialFileName = "/tmp/simpleserial.ser"

        @JvmStatic
        fun fileStudentDAO(fileName: String = simpleSerialFileName): BaseDAO<Student> {
            return SerializableStudentDAO(fileName)
        }

        @JvmStatic
        fun fileStudentService(fileName: String = simpleSerialFileName): StudentService {
            return StudentService(fileStudentDAO(fileName));
        }

        val fancySerialFileName = "/tmp/fancyserial.ser"

        @JvmStatic
        fun serialStatefulStudentDAO(fileName: String = fancySerialFileName): BaseDAO<Student> {
            return SerialFileStudentDAO(fileName)
        }


        @JvmStatic
        fun serialStatefulStudentService(fileName: String = fancySerialFileName): StudentService {
            return StudentService(serialStatefulStudentDAO(fileName));
        }

        val fancyJsonFileName = "/tmp/fancyjson.json"

        @JvmStatic
        fun jsonStatefulStudentDAO(fileName: String = fancyJsonFileName): BaseDAO<Student> {
            return JSONFileStudentDAO(fileName)
        }

        @JvmStatic
        fun jsonStatefulStudentService(fileName: String = fancyJsonFileName): StudentService {
            return StudentService(jsonStatefulStudentDAO(fileName))
        }

        val strategicJsonName = "/tmp/strategic.json"
        fun jsonStrategicStudentDAO(fileName: String = strategicJsonName) : BaseDAO<Student>{
            val jsonStrategy = JsonStrategy(fileName)
            val dao = StrategicStatefulStudentDAO(jsonStrategy)
            return dao
        }

        @JvmStatic
        fun jsonStrategicStudentService(fileName: String = strategicJsonName): StudentService {
            return StudentService(jsonStrategicStudentDAO(fileName))
        }

        val strategicSerialName = "/tmp/strategic.ser"
        fun serialStrategicStudentDAO(fileName: String = strategicSerialName) : BaseDAO<Student>{
            val strategy = SerialStrategy(fileName)
            val dao = StrategicStatefulStudentDAO(strategy)
            return dao
        }

        @JvmStatic
        fun serialStrategicStudentService(fileName: String = strategicSerialName): StudentService {
            return StudentService(serialStrategicStudentDAO(fileName))
        }

        val strat2JsonFileName = "/tmp/strategic2.json"
        fun jsonStrat2StudentDAO(fileName: String = strat2JsonFileName) : BaseDAO<Student>{
            val strategy = JsonStrategy(fileName)
            val dao = ALaCarteStrategyStudentDAO(strategy::serialize, strategy::reify)
            return dao
        }

        @JvmStatic
        fun jsonStrategy2StudentService(fileName: String = strat2JsonFileName): StudentService {
            return StudentService(jsonStrat2StudentDAO(fileName))
        }
    }
}

val jMapper = jacksonObjectMapper().registerModule(JavaTimeModule()).enable(SerializationFeature.INDENT_OUTPUT);

