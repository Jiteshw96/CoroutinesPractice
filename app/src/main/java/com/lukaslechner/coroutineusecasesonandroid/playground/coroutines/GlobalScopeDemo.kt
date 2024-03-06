package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This will not block the main thread and main continues will be printed before launch finished it's execution
 * Both Launch Will Run In Concurrently
 * */
fun main() {
    logTimber("Main Started")
    GlobalScope.launch {
        logTimber("Run Blocking Started")
        launch {
            logTimber("Launching First Coroutine")
            delay(3000)
            logTimber("First Coroutine Completes")
        }

        launch {
            logTimber("Launching Second Coroutine")
            delay(500)
            logTimber("Second Coroutine Completes")
        }
        logTimber("runBlocking Finished")
    }
    logTimber("Main Continues")
    Thread.sleep(20000)
}

private fun logTimber(message:String){
    println(message)
}