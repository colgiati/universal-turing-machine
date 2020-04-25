package main.kotlin.universal.turing.machine

fun parseUTM(utmNumberAsString: String): Map<Pair<Int, Int>, Triple<Int, Int, Int>> {
    val transitionList = utmNumberAsString.toBigInteger().toString(2).removePrefix("1").split("11")
    val transitionMap = transitionList.map { transition -> transition.split("1") }
    return transitionMap.map { (currentState, read, nextState, write, direction) ->
        Pair(currentState.length - 1, read.length - 1) to
                Triple(nextState.length - 1, write.length - 1, direction.length - 1) }.toMap()
}