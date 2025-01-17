package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main(){
    val flow = flow {
        println("emit first value")
        emit(1)

        delay(100)
        println("emit second value")
        emit(2)
    }

    runBlocking {
        flow.collect {emittedValue ->
            println("Collected $emittedValue")
        }
    }
}