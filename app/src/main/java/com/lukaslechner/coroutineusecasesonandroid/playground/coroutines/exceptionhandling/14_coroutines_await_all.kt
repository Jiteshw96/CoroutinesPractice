package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope


fun main() = runBlocking {

    val scope = CoroutineScope(Job())

    val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught using handler $throwable in CoroutineExceptionHandler")
    }


    scope.launch(Job() +ceh) {
        coroutineScope {
            awaitAll(
                async {
                    println("1st Async")
                    delay(100)
                    println("1st Async Completed")
                },

                async {
                    println("2nd Async")
                    try{
                        throw ArithmeticException("Crash happened")
                    }catch (e:Exception){

                    }
                    delay(100)
                },

                async {
                    println("3rd Async")
                    delay(100)
                    println("3rd Async Completed")
                }
            )
        }
    }
    Thread.sleep(2000)
}