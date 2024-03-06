package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun main()  {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Exception caught in handler ${throwable.toString()}")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    //#1
   /*val job = scope.launch(exceptionHandler) {
        try {
            async {
                delay(1000)
                throw IllegalArgumentException("Exception happened")
            }.await()
        }catch (exception:Exception){
            println("Exception caught in launch try catch")
        }
    }*/

    //#2
   val job =  scope.async() {
        try {
            async {
                delay(1000)
                throw IllegalArgumentException("Exception happened")
            }.await()
        }catch (exception:Exception){
            println("Exception async in launch try catch")
        }
    }

    /*scope.launch(exceptionHandler) {
        job.await()
        println("isActive ${job.isCancelled}");
    }*/


    Thread.sleep(3000)
}