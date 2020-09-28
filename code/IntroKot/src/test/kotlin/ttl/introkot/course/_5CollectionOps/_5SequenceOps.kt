package ttl.introkot.course._5CollectionOps

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */


class SeqOps {

    @Test
    fun simpleSequence() {
        val list = listOf("one", "two", "three", "four", "six hundred and fifty seven")
        val result = list.asSequence()
                .filter{it.length > 4}
                .map(String::length)
                .toList()
        println("results:")
        result.forEach(::println)
    }

    @Test
    fun testSequenceGeneration() {
        val result = generateSequence(0) { it + 2}
                .takeWhile{it < 20}
                .map{it * 2}
                .toList()
        println("results:")
        result.forEach(::println)
    }

    @Test
    fun testSequenceGenerationWithSeedFunction() {
        val result = generateSequence({
            //Do a complicated initialization of the seed here
            0
        }) { it + 2}
                .takeWhile{it < 20}
                .map{it * 2}
                .toList()
        println("results:")
        result.forEach(::println)
    }

}