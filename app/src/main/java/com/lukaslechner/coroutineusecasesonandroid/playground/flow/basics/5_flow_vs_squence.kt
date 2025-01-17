package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics


import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun simple() = sequence {
    (1..3).forEach { Thread.sleep(100); yield(it) }
}

fun main()  {
    //This will create different thread for launch and simple()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->  }
    val scope = CoroutineScope(exceptionHandler)
    scope.launch {
        for (k in 1..3) {
            println("From main $k")
            delay(100)
        }
    }
    simple().forEach { value -> println("From sequence $value") }
    println("End of program")
}


