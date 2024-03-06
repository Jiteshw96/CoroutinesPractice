package com.lukaslechner.coroutineusecasesonandroid.playground.flow.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.math.BigInteger
import kotlin.coroutines.EmptyCoroutineContext


suspend fun main() {
    val scope = CoroutineScope(EmptyCoroutineContext)

    scope.launch {
        initFlow()
            .onCompletion { throwable ->
                if (throwable is CancellationException) {
                    println("Coroutine is cancelled")
                }
            }
            .collect { emittedValue ->
                println("Collected $emittedValue")

                if (emittedValue == 2) {
                    cancel()
                }
            }
    }.join()

}

private fun initFlow() = flow {
    emit(1)
    emit(2)
    println("Start calculation")
    calculateFactorialOf(5)
    print("Calculation finished")
    emit(5)
}

private suspend fun calculateFactorialOf(number: Int): BigInteger = coroutineScope {
    var factorial = BigInteger.ONE
    for (i in 1..number) {
        println("calculating factorial $i")
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        ensureActive()
    }
    factorial
}