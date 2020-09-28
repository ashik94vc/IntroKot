package ttl.introkot.larku.dao.file.fancy.composition

import ttl.introkot.larku.domain.Student
import java.io.Serializable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong

/**
 * @author whynot
 */
data class State<T>(val items: ConcurrentMap<Long, T> = ConcurrentHashMap(),
                       val nextId: AtomicLong = AtomicLong(1)) : Serializable
