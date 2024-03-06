package com.lukaslechner.coroutineusecasesonandroid.playground.flow.builders

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

suspend fun main(){

    val firstFlow = flowOf(1).collect{collectedValue ->
        println("first flow : $collectedValue")
    }

    val secondFlow = flowOf<Int>(10,2,3)

    secondFlow.collect{ collectedValue ->
        println("second flow : $collectedValue")
    }

    listOf(1,2,3).asFlow().collect {collectedValue ->
        println("third flow : $collectedValue")
    }

    flow {
       delay(1000)
       emit("item emitted after 1000ms")

       emitAll(secondFlow)
    }.collect { emittedValue ->
        println("fourth flow $emittedValue")

    }

}