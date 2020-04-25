package main.kotlin.universal.turing.machine

data class UniversalTM(val transitions: Map<Pair<Int, Int>, Triple<Int, Int, Int>>,
                       val leftSide: MutableList<Int> = arrayListOf(), val rightSide: MutableList<Int>,
                       var currentValue: Int, var currentState: Int = 0) {

    @ExperimentalStdlibApi
    fun moveRight() {
        leftSide.add(0, currentValue)
        currentValue = rightSide.removeFirst()
    }

    @ExperimentalStdlibApi
    fun moveLeft() {
        rightSide.add(0, currentValue)
        currentValue = leftSide.removeFirst()
    }

    @ExperimentalStdlibApi
    fun makeTransition(transitionKey: Pair<Int, Int>) {
        val (nextState, write, direction) = transitions.getValue(transitionKey)
        currentState = nextState
        currentValue = write
        when (direction) {
            0 -> moveLeft()
            1 -> moveRight()
        }
    }
}

fun main() {
    val utm = UniversalTM(transitions = parseUTM("1217045072201469412578714245820597635027236544671968829780823713123069689986813927338821662279672905006655217995918035865910973352031467859221814481965580242702565444"),
            rightSide = "0000000100000".split("").filter { it.isNotEmpty() }.map { it.toInt() * -1 }.toMutableList(),
            currentValue = -1)
    println(utm)
}
