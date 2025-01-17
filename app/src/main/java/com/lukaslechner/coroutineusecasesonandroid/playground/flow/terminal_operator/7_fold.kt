package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.runBlocking

fun main() {
    val flow = flowOf(1,2,3,4,5,6,7,8)

    runBlocking {
      val item =  flow.fold(5){ accumulator, emittedItem ->
            accumulator + emittedItem
        }
       println("Received Item $item")
    }

}