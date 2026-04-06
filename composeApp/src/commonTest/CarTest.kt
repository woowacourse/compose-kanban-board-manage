package woowacourse.kanban.board.study

class CarTest {
    @Test
    fun 이동() {
        val car = Car("jason", 0)
        val actual: Car = car.move { false }
        assertThat(actual).isEqualTo(Car("jason", 1))
    }

    @Test
    fun 정지() {
        val car = Car("jason", 0)
        val actual: Car = car.move(
            object : MoveStrategy {
                override val isMovable: Boolean = false
            },
        )
        assertThat(actual).isEqualTo(Car("jason", 0))
    }
}
