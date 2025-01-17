package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hot_cold_state_flow

import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

suspend fun main():Unit = coroutineScope{
    /**
     * It emits the data only when collector is active (if we use cancel it will cancel the emitting )
     * Both Flows Will trigger the flow to emit data twice
     * Third collector is not attached hence it won't trigger the flow
     * */
    launch {
        coldFlow()
            .collect{
              //  cancel()
                println("Collector 1 collects $it")
            }
    }

    /**
     * If collector 2 starts collecting after 1500 seconds it will still get all the values
     * */
    //delay(1500)
    launch {
        coldFlow()
            .collect{
               // cancel()
                println("Collector 2 collects $it")
            }
    }

    launch {
        coldFlow()
    }
}


fun coldFlow() = flow{
    println("Emitting First Value")
    emit(1)
    delay(1000)

    println("Emitting Second Value")
    emit(2)
    delay(1000)

    println("Emitting Third Value")
    emit(3)
    delay(1000)
}