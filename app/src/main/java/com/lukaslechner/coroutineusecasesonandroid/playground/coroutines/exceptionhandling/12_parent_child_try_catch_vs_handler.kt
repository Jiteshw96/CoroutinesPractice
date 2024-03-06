package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Behaviour for parent child
 * block #1 try catch will be printed and parent will be notified about the exception as there is no handler it main thread will handle it
 * block #2 Parent will be notified about the exception and handler statement will be printed
 * block #3 Try catch will be printed and handler will be printed as child is failed and it will notify the parent
 * */
suspend fun main()  {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Exception caught in handler ${throwable.toString()}")
    }

    val scope = CoroutineScope(Job())

    //#1
    /*scope.launch {
        try {
            async {
                delay(100)
                throw IllegalArgumentException("Something went wrong")
            }.await()
        }catch (exception:Exception){
            println("exception caught in try catch $exception")
        }
    }*/

    //#2
    /*scope.launch(exceptionHandler) {
        async {
            delay(100)
            throw IllegalArgumentException("Something went wrong")
        }.await()
    }*/

    //#3
   val job = scope.launch(exceptionHandler) {
        try {
           val asyncJob =  async {
                delay(100)
                throw IllegalArgumentException("Something went wrong")
            }
            asyncJob.await()
        } catch (e: Exception) {
            println("exception caught in try catch $e")
        }
    }

     //#4 same as #3
     /*scope.launch(exceptionHandler) {
        try {
            async {
                delay(100)
                throw IllegalArgumentException("Something went wrong")
            }.await()
        } catch (e: Exception) {
            println("exception caught in try catch $e")
        }
    }*/



    Thread.sleep(4000)
}