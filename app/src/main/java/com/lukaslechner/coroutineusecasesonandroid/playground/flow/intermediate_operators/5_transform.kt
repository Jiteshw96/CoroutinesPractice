package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform


suspend fun main(){
    flowOf(1,2,3,4,5,6,7,8,9,10)
        .transform {
            emit(it)
            emit(it *10)
        }.collect{
            println("$it")
        }

}