package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking


fun main() {
    val flow = flow {
        println("emitting first value")
        emit(1)

        println("emitting second value")
        //This flow excepts just one value throw illegal exception if more than one
      //  emit(2)
    }

    runBlocking {
       val item = flow.single()
       println("Collected $item")
    }
}