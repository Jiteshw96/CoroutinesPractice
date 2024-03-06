package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.structuredconcurrency

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{

    val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught exception $throwable")
    }

    val scope = CoroutineScope(Job() +Dispatchers.Default)


    scope.launch(ceh) {
        println("Coroutine 1 starts")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 starts")
        delay(500)
        println("Coroutine 2 Completed")
    }.invokeOnCompletion {throwable->
        if(throwable is CancellationException){
            println("Coroutine 2 cancelled")
        }
    }

    Thread.sleep(1000)
    println("scope got cancelled ${!scope.isActive}")
}