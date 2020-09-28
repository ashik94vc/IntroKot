package ttl.introkot.course._5CollectionOps

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty1

/**
 * @author whynot
 */

fun groupByZipCode(): Map<String, List<Person>> {
    val map = persons.groupBy {
        it.address.zipCode
    }
    return map
}

/**
 * Groupingby creates a Grouping object, which then has various ways to
 * further manipulate the grouped objects.  Equivalent to the 'downstream'
 * collector in Streams.
 */
fun groupingByZipCode(): Map<String, Int> {
    val map = persons.groupingBy {
        it.address.zipCode
    }.eachCount()

    //Do eachCount with a fold
    val map2 = persons.groupingBy {
        it.address.zipCode
    }.fold(0, {acc, person -> acc + 1})
    return map2
}

/**
 * Aggregation.  Here we will get a map of
 * zipCode by the sum of the lengths of all the names
 * of people in that zip code.
 */
fun groupingByAggregation(): Map<String, Int?> {
    val map: Map<String, Int?> = persons.groupingBy {
        it.address.zipCode
    }.aggregate { key, accumulator, currPerson, isFirst ->
        if (!isFirst) { //if(accumulator != null) {
            accumulator?.plus(currPerson.name.length)
        } else {
            currPerson.name.length
        }
    }
    println(map)
    return map
}


/**
 * Partition into a Pair of two Lists, each having
 * members that match or do not match the predicate
 * respectively.
 */
fun partitionByHavePhoneNumber(): Pair<List<Person>, List<Person>> {
    val map = persons.partition {
        it.phoneNumbers.size > 0
    }
    return map
}

/**
 * Make a simple Map.  No lists, and in case
 * of duplicates, the last Value encountered wins.
 */
fun associateByName(): Map<String, Person> {
    val map = persons.associateBy(Person::name)
    return map
}

fun associateByZip(): Map<String, Person> {
    val map = persons.associateBy { it.address.zipCode }
    return map
}

class TestGrouping {
    @Test
    fun testGroupByZipCode() {
        val m = groupByZipCode()
        println("GroupByZipCode")
        println(m)

        assertEquals(3, m["19191"]?.size)

    }

    @Test
    fun testGroupingByZipCode() {
        val m2 = groupingByZipCode()
        println("groupingByZipCode")
        println(m2)
        assertEquals(3, m2["19191"])
    }

    @Test
    fun testGroupingByAggregate() {
        val m2 = groupingByAggregation()
        println("aggregateByZipCode")
        println(m2)
        //The sum of the lengths of names for people in zip code 19191
        assertEquals(16, m2["19191"])
    }


    @Test
    fun testPartitionByHavePhoneNumber() {
        val lists = partitionByHavePhoneNumber()
        println("partionPhoneNumber")
        println(lists.first)
        println(lists.second)
        assertEquals(5, lists.first.size)
        assertEquals(2, lists.second.size)
    }

    @Test
    fun testAssociateBy() {
        val map1 = associateByName()
        println("associateBy")
        println(map1)
        assertEquals(7, map1.size)

        val map2 = associateByZip()
        println("associateByZip")
        println(map2)
        assertEquals(5, map2.size)
    }
}
