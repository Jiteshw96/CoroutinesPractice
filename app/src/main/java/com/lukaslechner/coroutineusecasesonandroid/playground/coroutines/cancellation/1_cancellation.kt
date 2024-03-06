package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    val job = launch {
        repeat(10){
            try {
                delay(100)
                println("executing task on thread value -> $it")
            } catch (e: CancellationException) {
                println("coroutine was cancelled")
                throw CancellationException()
            }
        }
    }

    delay(250)
    println("cancelling the coroutine")
    job.cancel()
}
