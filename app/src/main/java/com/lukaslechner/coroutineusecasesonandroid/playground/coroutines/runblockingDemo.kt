package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * This will block the main thread and main continues will be printed after runblocking finished it's execution
 * Both Launch Will Run In Concurrently
 * */
fun main() {
   logTimber("Main Started")
   runBlocking {
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
    Thread.sleep(4000)
}

private  fun logTimber(message:String){
    println(message)
}

