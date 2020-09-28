package ttl.introkot.course._5CollectionOps

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * @author whynot
 */


// Return true if the Person is from the given zip
fun Person.isFrom(zip: String): Boolean {
    return address.zipCode == zip
    TODO()
}

// Return true if all persons are from the given zip
fun checkAllPersonsAreFrom(zip: String): Boolean {
    return persons.all{p -> p.address.zipCode == zip}
    TODO()
}

// Return true if there is at least one person from the given zip
fun hasPersonFromZip(zip: String): Boolean {
    return persons.any{ p -> p.address.zipCode == zip}
    TODO()
}

// Return the number of persons from the given zip
fun countPersonsFrom(zip: String): Int {
    return persons.filter{ p -> p.address.zipCode == zip}.count()
    kotlin.TODO()
}

// Return a person who lives in the given zip, or null if there is none
fun findAnyPersonFrom(zip: String): Person? {
    return persons.firstOrNull{p -> p.address.zipCode == zip}
    kotlin.TODO()
}

fun kotiteration() {
    val personsIn1919: List<String> = persons
            .filter {
                it.address.zipCode == "19191"
            }.map {it.name}

    val p2: List<String> = persons.asSequence()
//    val p2: Sequence<String> = persons.asSequence()
            .filter {
                it.address.zipCode == "19191"
            }.map {it.name}
            .toList()
}

class TestCollectionOps
{
    @Test
    fun testFilterMap() {
        val list = persons.filter{
            it.name.startsWith("M")
        }

        val list2 = persons.filter{
            it.name.startsWith("S")
        }.map{ p -> p.name}

        assertEquals(2, list2.size)

        println(list2)
    }

    @Test
    fun testAnyAllOps() {
        val zipToTest = "19191"
        val p = persons[0]
        val x = p.isFrom(zipToTest)
        assertTrue(x)

        val b1 = checkAllPersonsAreFrom(zipToTest)
        assertFalse(b1)

        val b2 = hasPersonFromZip(zipToTest)
        assertTrue(b2)

        val b3 = countPersonsFrom(zipToTest)
        assertEquals(3, b3)

        val b4 = findAnyPersonFrom(zipToTest)
        assertNotNull(b4)

        val b5 = findAnyPersonFrom("99999")
        assertNull(b5)
    }

}
