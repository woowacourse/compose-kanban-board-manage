package woowacourse.kanban.board.study

data class Car(val name: String, val position: Int) {
    fun move(): Car {
        if (moveStrategy.isMovable) {
            return copy(position = position + 1)
        }
        return this
    }
}

interface MoveStrategy {
    val isMovable: Boolean
}

val numbers: List<Int> = listOf(1, 2, 3, 4, 5, 6)


fun Sum(numbers: List<Int>, function: (Int) -> Boolean) {
    var total = 0

    numbers.filter {
        function ->
    }
}

fun sumAll(numbers: List<Int>): Int {
    for (number in numbers) {
        total += number
    }
    return total
}

fun sumAllEven(numbers: List<Int>): Int {

    numbers
        .filter(number % 2 == 0)
        .map {

        }


    total += number

}

fun sumAllOverThree(numbers: List<Int>): Int {
    var total = 0
    for (number in numbers) {
        if (number > 3) {
            total += number
        }
    }
    return total
}

val nameAndCart = listOf(

)
val cart = listOf(1000,2000,3000)
val totalPrice = cart.sum() // 6000

