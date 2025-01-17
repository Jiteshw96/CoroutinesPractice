package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.coroutines.EmptyCoroutineContext


suspend fun main(){
    val scope = CoroutineScope(EmptyCoroutineContext)

    /** everything in the core coroutines library checks for cancellation automatically,
    but if you are doing something that emits values and nowhere in the Flow’s chain do any methods from
    the core coroutines get called,
    cancellable operator will step in an check for cancellation so you don’t have to manually.*/

    scope.launch {
        flowOf(1,2,3)
            .onCompletion { throwable->
                if(throwable is CancellationException){
                    println("Coroutine is cancelled")
                }
            }.cancellable()
            .collect{emittedValue->
                println("collected value $emittedValue")

                if(emittedValue == 2){
                    cancel()
                }
            }
    }.join()


}
