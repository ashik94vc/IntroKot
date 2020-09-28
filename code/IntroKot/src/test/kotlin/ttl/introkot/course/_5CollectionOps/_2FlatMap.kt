package ttl.introkot.course._5CollectionOps

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * @author whynot
 */

fun getPhoneNumbersForAllPersons() : List<String>{
    val pns = persons.flatMap{p -> p.phoneNumbers}
    return pns
}

fun getNamesOfPersonsWithPhoneNumbers() : List<String> {
    val names = persons.filter{it.phoneNumbers.size > 0}.map(Person::name)
    return names
}


class TestFlatMap {

    @Test
    fun testGetPhoneNumbersForAllPersons() {
        val pns = getPhoneNumbersForAllPersons()
        println(pns)
        assertEquals(8, pns.size)
    }

    @Test
    fun testGetNamesOfPersonsWithPhoneNumbers() {
        val pns = getNamesOfPersonsWithPhoneNumbers()
        println(pns)
        assertEquals(5, pns.size)
    }
}
