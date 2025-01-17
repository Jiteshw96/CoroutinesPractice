package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun main() {
    flowOf(1,2,3,4,5,6,7,8,9)
        .dropWhile {
            it < 5
        }.collect{
            println("$it")
        }
}