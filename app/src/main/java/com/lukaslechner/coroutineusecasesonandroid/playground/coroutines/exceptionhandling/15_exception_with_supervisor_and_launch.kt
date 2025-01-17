package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope


fun main(){
    val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught using handler $throwable in CoroutineExceptionHandler")
    }
    val scope = CoroutineScope(SupervisorJob())

    scope.launch(ceh) {
       // supervisorScope {
           launch {
               println("Launching first coroutine")
               delay(1000)
               println("Completed First")
           }

            launch {
                println("Launching second coroutine")
                delay(100)
                throw  RuntimeException("Something went wrong")
                println("Completed Second")
            }

            launch {
                println("Launching third coroutine")
                delay(1000)
                println("Completed Third")
            }
      //  }
    }
    Thread.sleep(10000)

}