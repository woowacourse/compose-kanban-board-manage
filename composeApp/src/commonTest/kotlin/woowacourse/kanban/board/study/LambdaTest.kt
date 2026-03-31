@file:Suppress("NonAsciiCharacters")

package woowacourse.kanban.board.study

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import kotlin.time.measureTime

class LambdaTest {
    @Test
    fun `모두 더하기`() {
        val result = sumAll(numbers)
        assertThat(result).isEqualTo(21)
    }

    @Test
    fun `짝수 더하기`() {
        val result = sumAllEven(numbers)
        assertThat(result).isEqualTo(12)
    }

    @Test
    fun `3이상의 수 더하기`() {
        val result = sumAllOverThree(numbers)
        assertThat(result).isEqualTo(15)
    }

    @Test
    fun `시퀀스와 필터와 함수 비교`() {
        val sequenceThenFilter = measureTime {
            numbers.asSequence().filter { it % 2 == 0 }.sum()
        }
        val filter = measureTime {
            numbers.filter { it % 2 == 0 }.sum()
        }
        val function = measureTime {
            sumByLogic(numbers) { it % 2 == 0 }
        }

        println("sequenceThenFilter: $sequenceThenFilter")
        println("filter: $filter")
        println("function: $function")
    }

    val numbers: List<Int> = listOf(1, 2, 3, 4, 5, 6)

    fun sumAll(numbers: List<Int>): Int {
        return sumByLogic(numbers) { true }
    }

    fun sumAllEven(numbers: List<Int>): Int {
        return numbers.sumOf { if (it % 2 == 0) it else 0 }
    }

    fun sumAllOverThree(numbers: List<Int>): Int {
        return numbers.sumOf { if (it > 3) it else 0 }
    }

    fun sumByLogic(
        numbers: List<Int>,
        condition: (Int) -> Boolean,
    ): Int {
        var total = 0
        for (number in numbers) {
            if (condition(number)) {
                total += number
            }
        }
        return total
    }
}
