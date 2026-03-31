package woowacourse.kanban.board.study

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import kotlin.time.measureTime

class SequenceTest {
    @Test
    fun sequence1() {
        // given
        val numbers = listOf(1, 2, 3, 4, 5)
        var executeCount = 0
        // when
        val result = numbers
            .asSequence()
            .map { // Stateless
                it * 2
                executeCount++
            }
            .take(2)
            .toList()
        //then
        assertThat(executeCount).isEqualTo(2)
    }

    @Test
    fun sequence2() {
        // given
        val numbers = listOf(1, 2, 3, 4, 5)
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
        //then
        println("map -> sort: $mapThenSort")
        println("sort -> map: $sortThenMap")
        assertThat(mapThenSort).isGreaterThan(sortThenMap)
    }
}
