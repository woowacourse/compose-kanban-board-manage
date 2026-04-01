package woowacourse.kanban.board.study

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import kotlin.time.measureTime

class SequenceTest {

    @Test
    fun sequence1() {
        // given
        val numbers = listOf(1, 2, 3, 4, 5)
        // when
        val result = numbers
            .asSequence()
            .map { it * 2 }
            .filter { it > 5 }
            .toList()
        // then
        assertThat(result).containsExactly(6, 8, 10)
    }

    @Test
    fun sequence2() {
        // given
        val numbers = List(100_000) { it }
        var executeCount = 0
        // when
        val result = numbers
            .asSequence()
            .map {
                it * 2
                executeCount++
            }
            .take(2)
            .toList()
        // then
        assertThat(executeCount).isEqualTo(2)
    }

    @Test
    fun sequence3() {
        // given
        val numbers = List(100_000) { it }
        var executeCount = 0
        // when
        val result = numbers
            .asSequence()
            .map { // Stateless
                it * 2
                executeCount++
            }
            .sorted() // Stateful
            .take(2)
            .toList()
        // then
        assertThat(executeCount).isEqualTo(100_000)
    }
    @Test
    fun sequence4() {
        // given
        val numbers = List(100_000) { it }
        var executeCount = 0
        // when
        val result = numbers
            .sorted() // Stateful
            .asSequence()
            .map { // Stateless
                it * 2
                executeCount++
            }
            .take(2)
            .toList()
        // then
        assertThat(executeCount).isEqualTo(2)
    }

    @Test
    fun sequence5() {
        // given
        val numbers = List(100_000) { it }
        // when
        val sequenceTime = measureTime {
            numbers
                .asSequence()
                .map {
                    it * 2
                }
                .take(2)
                .toList()
        }
        val listTime = measureTime {
            numbers
                .map {
                    it * 2
                }
                .take(2)
        }
        // then
        println("sequenceTime: $sequenceTime")
        println("listTime: $listTime")
        assertThat(sequenceTime).isLessThan(listTime)
    }
    @Test
    fun sequence6() {
        // given
        val numbers = List(100_000) { it }
        // when
        val mapThenSort = measureTime {
            numbers
                .asSequence()
                .map {
                    it * 2
                }
                .sorted()
                .take(2)
                .toList()
        }
        val sortThenMap = measureTime {
            numbers
                .sorted()
                .asSequence()
                .map {
                    it * 2
                }
                .take(2)
                .toList()
        }
        // then
        println("map -> Sort: $mapThenSort")
        println("sort -> Map: $sortThenMap")
        assertThat(mapThenSort).isGreaterThan(sortThenMap)
    }
}
