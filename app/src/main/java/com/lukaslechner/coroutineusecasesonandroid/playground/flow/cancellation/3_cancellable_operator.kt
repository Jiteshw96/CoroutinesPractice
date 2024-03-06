package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.coroutines.EmptyCoroutineContext


suspend fun main(){
    val scope = CoroutineScope(EmptyCoroutineContext)

    scope.launch {
        initFlow()
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

private fun initFlow() = flow {
    emit(1)
    emit(2)
    println("Start calculation")
    calculateFactorial(1_000)
    println("Calculation finished")
    emit(3)
}

private fun calculateFactorial(number : Int) : BigInteger {
    var factorial = BigInteger.ONE
    for(i in 1..number){
        println("Calculating for $i")
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
    }
    return factorial
}