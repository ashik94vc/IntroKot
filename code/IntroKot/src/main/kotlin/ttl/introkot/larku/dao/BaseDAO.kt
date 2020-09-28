package ttl.introkot.larku.dao;

import java.lang.UnsupportedOperationException

/**
 * @author whynot
 */

interface BaseDAO<T>
{
    fun insert(input: T) : T;
    fun update(id: Long, input: T) : Boolean;
    fun delete(id: Long) : Boolean;
    fun find(id: Long) : T?
    fun count() : Long

    //default methods that do nothing
    fun findAll() : List<T> {throw UnsupportedOperationException("Needs implementing")}
    fun clear() {}
    fun close() {}
}

