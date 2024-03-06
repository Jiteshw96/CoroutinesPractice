package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() =  runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)

    scope.launch {
        delay(100)
        println("Coroutine completed")
    }

    delay(200)
}