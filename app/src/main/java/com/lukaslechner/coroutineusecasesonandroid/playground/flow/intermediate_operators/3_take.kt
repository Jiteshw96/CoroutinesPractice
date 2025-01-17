package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile

suspend fun main ()  {
    flowOf(1,2,3,4,5,6,7,8)
        .takeWhile { it < 4 }
        .collect {
            println("$it")
        }
}