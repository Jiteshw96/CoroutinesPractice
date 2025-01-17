package com.lukaslechner.coroutineusecasesonandroid.playground.flow.hot_cold_state_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

suspend fun main(){
    /**
     * Shared Flow emits the data even when there is no collector
     * If 2nd collector starts collecting after 500ms then it will not receive all the values
     * Cancellation from the collector doesn't effect the emitting as both are in different scope
     * */
    val sharedFlow = MutableSharedFlow<Int>()

    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        repeat(5){
            println("SharedFlow emits $it")
            sharedFlow.emit(it)
            delay(200)
        }
    }

    scope.launch {
        sharedFlow.collect{
          //  cancel()
            println("Collector 1st received $it")
        }
    }

    scope.launch {
      //  delay(400)
        sharedFlow.collect{
           // cancel()
            println("Collector 2nd received $it")
        }
    }

    Thread.sleep(1500)

}