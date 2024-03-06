package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
/**
 * Only for parent coroutine
 * #1 only try catch will be printed
 * #2 only exception handler will be printed
 * */
suspend fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Exception caught in handler ${throwable.toString()}")
    }

    val scope = CoroutineScope(Job())


    // #1
    scope.launch {
        try {
            delay(100)
            throw IllegalArgumentException("Something went wrong")
        }catch (exception:Exception){
            println("exception caught in try catch $exception")
        }
    }

    // #2
    scope.launch(exceptionHandler) {
        delay(100)
        throw IllegalArgumentException("Something went wrong")
    }

    Thread.sleep(4000)
}