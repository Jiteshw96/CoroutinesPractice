package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    val scope = CoroutineScope(Dispatchers.Default)

    //cancelling the parent will cancel all it's children
    scope.coroutineContext[Job]!!.invokeOnCompletion {
        if(it is CancellationException){
            println("Parent is cancelled")
        }
    }

    val job1 = scope.launch {
        delay(1000)
        println("job1 completed")
    }

    job1.invokeOnCompletion {
        if(it is CancellationException){
            println("Coroutine 1 was cancelled!")
        }
    }

    val job2 = scope.launch {
        delay(1000)
        println("job2 completed")
    }.invokeOnCompletion {
        if(it is CancellationException){
            println("Coroutine 2 was cancelled!")
        }
    }

    delay(800)

    scope.coroutineContext[Job]!!.cancelAndJoin()
}