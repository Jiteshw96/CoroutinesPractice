package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)

    val parentCoroutineJob = scope.launch {
        launch {
            delay(1000)
            println("Child coroutine 1 completed")
        }
        launch {
            delay(1000)
            println("Child coroutine 2 completed")
        }
    }

    parentCoroutineJob.invokeOnCompletion {
        println("parent coroutine has completed")
    }

    Thread.sleep(2000)

}

