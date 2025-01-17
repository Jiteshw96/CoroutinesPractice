package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf

suspend fun main(){
    flowOf(1,2,4,5,6,7,7,8,9,2,3)
        .distinctUntilChanged()
        .collect{
            println("$it")
        }
}