package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

/**
 * This will block the main thread and main continues will be printed after runblocking finished it's execution
 * Both Launch Will Run In Concurrently
 * */
fun main() {
   logTimber("Main Started")
    /**
     * Launch will not block the main thread
     * RunBlocking will block the main thread
     * */
    /*val scope = CoroutineScope(EmptyCoroutineContext)
    scope.launch {
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
        delay(4000)
        logTimber("runBlocking Finished")
    }*/
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
        delay(4000)
        logTimber("runBlocking Finished")
    }
    logTimber("Main Continues")
}

private  fun logTimber(message:String){
    println(message)
}

