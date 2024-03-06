package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.structuredconcurrency


import kotlinx.coroutines.*

/**
 * Global scope doesn't have a job and due to this it can't form hierarchy
 * */

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {

    println("Job of GlobalScope: ${GlobalScope.coroutineContext[Job]}")
    val scope = GlobalScope
    var job:Job? = null
    val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        println("$throwable")
    }
    job = scope.launch(coroutineExceptionHandler) {
        val child = launch {
            delay(50)
            throw RuntimeException()
            println("Still running")
            delay(50)
            println("Still running")
            delay(50)
            println("Still running")
            delay(50)
            println("Still running")
        }
        println("scope is ${scope.coroutineContext[Job]?.children?.contains(child)}")
    }

    scope.launch {
        delay(150)
        println("second coroutine completed")
    }.join()


    delay(100)

    job.cancel()

    delay(500)



}