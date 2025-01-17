package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hot_cold_state_flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

suspend fun main(){

    /**
     * Mutable State Flow is Shared Flow with
     * Initial Value
     * Repeat Value to 1
     * DistinctUntilChanged
     * */

    val counter = MutableStateFlow(0)

    print(counter.value)


    coroutineScope {
        repeat(10_000){
            launch {
                counter.update {currentValue ->
                    currentValue + 1
                }
            }
        }
    }
    println(counter.value)

}