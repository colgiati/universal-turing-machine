package universal.turing.machine

data class UniversalTM(val rightSide: MutableList<Char>, val leftSide: MutableList<Char> = mutableListOf(),
                       val tmCode: String, val stepMode: Boolean = false) {

    private var currentValue: Char = '_'
    private var currentState: Int = 0
    private var transitionCount: Int = 0
    private var transitions: Map<Pair<Int, Char>, Triple<Int, Char, Int>> = mapOf()
    private val mappings: Map<Int, Char> = mapOf(1 to '0', 2 to '1', 3 to '_')

    private fun parseCode() {
        val transitionList = tmCode.toBigInteger().toString(2).removePrefix("1").split("11")
        val transitionMap = transitionList.map { transition -> transition.split("1") }
        transitions =  transitionMap.map { (currentState, read, nextState, write, direction) ->
            Pair(currentState.length - 1, mappings.getOrDefault(read.length, '_')) to
                    Triple(nextState.length - 1, mappings.getOrDefault(write.length, '_'), direction.length - 1)
        }.toMap()
    }

    private fun moveRight() {
        leftSide.add(0, currentValue)
        currentValue = if (rightSide.isNotEmpty()) rightSide.removeAt(0) else '_'
    }

    private fun moveLeft() {
        rightSide.add(0, currentValue)
        currentValue = if (leftSide.isNotEmpty()) leftSide.removeAt(0) else '_'
    }

    private fun makeTransition(transitionKey: Pair<Int, Char>) {
        val (nextState, write, direction) = transitions.getValue(transitionKey)
        currentState = nextState
        currentValue = write
        when (direction) {
            0 -> moveLeft()
            1 -> moveRight()
        }
        transitionCount++
    }

    fun run() {
        parseCode()
        moveRight()
        printStep()
        while (transitions.containsKey(Pair(currentState, currentValue))) {
            makeTransition(Pair(currentState, currentValue))
            if (stepMode) printStep()
        }
        printResult()
    }

    private fun printStep() {
        println("Current state: $currentState")
        println("Tape: ${toLengthFifteen(leftSide, true)} $currentValue ${toLengthFifteen(rightSide)}")
        println("Current position:                   ^")
        println("Transition count: $transitionCount \n")
    }

    private fun toLengthFifteen(list: MutableList<Char>, reverse: Boolean = false): String {
        val tempList = list.toMutableList()
        while (tempList.size != 15) {
            if (tempList.size > 15) tempList.removeAt(15)
            else tempList.add('_')
        }
        if (reverse) tempList.reverse()
        return tempList.toString().filter { setOf('0', '1', ' ', '_').contains(it) }
    }

    private fun printResult() {
        print("Unary result: ")
        rightSide.forEach(::print)
        println("\nDecimal result: ${rightSide.size}")
    }
}

/**
 * Starts the program. Set stepMode to false if you only want to see the final result.
 * rightSide represents the starting state of the tape. The large number is the coded TM.
 */
fun main() {
    val utm = UniversalTM(tmCode = "81674512232238591408904823617637055087764453572221080810000448372556826544927522424830633551796455204968828408454807771424858041261628927130256499084767704751648519620657220",
            rightSide = "_0000000".toCharArray().toMutableList(), stepMode = true)
    utm.run()
}
