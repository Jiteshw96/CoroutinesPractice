package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking


fun main() {
    val flow = flow {
        println("emitting first value")
        emit(1)
        println("emitting second value")
        emit(2)
        println("emitting third value")
        emit(3)
    }

    runBlocking {
       val item = flow.toSet()
       println("Received set $item")
    }

    runBlocking {
        val item = flow.toList()
        println("Received list $item")
    }
}