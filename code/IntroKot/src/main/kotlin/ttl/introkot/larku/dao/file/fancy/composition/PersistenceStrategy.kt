package ttl.introkot.larku.dao.file.fancy.composition

/**
 * @author whynot
 */
interface PersistenceStrategy<T> {
    fun serialize(state: State<T>)
    fun reify() : State<T>
}