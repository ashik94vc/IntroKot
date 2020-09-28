package ttl.introkot.course._5CollectionOps

import org.junit.jupiter.api.Test

/**
 * @author whynot
 */


class TestReduction {
    @Test
    fun reducer() {
        val l1 = listOf(1, 4, 0, 696, -33)

        val sum = l1.sum()

        val empty = listOf<Int>()
        //fold takes an initial value
        val foldSum = empty.fold(0) { acc, next -> next + acc }

        //reduce uses the first and second values as the seed
        val redSum = l1.reduce { acc, next -> acc + next }

        println("sum: $sum, foldSum: $foldSum, redSum = $redSum")

        //Will blow up
//        val redSum2 = empty.reduce { acc, next -> acc + next }

        val justOne = listOf<Int>(1)
        val justOneSum = justOne.reduce { acc, next -> acc + next }

        println("redSum2: $justOne")

    }
}
