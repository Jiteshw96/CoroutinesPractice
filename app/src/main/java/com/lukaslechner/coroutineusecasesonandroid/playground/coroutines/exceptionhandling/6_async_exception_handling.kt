package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling


import kotlinx.coroutines.*
/**
 * - In Below Example statement 1 will throw the exception and it will be uncaught as it is under
 * runBlocking scope and not in CoroutineScope (scope)
 * - Statement 2 will throw the exception and it will be caught as it will be inside Coroutine scope which has
 * the handler installed
 * */

fun main() = runBlocking {

    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        println("Caught $exception in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job() + exceptionHandler)

    val job =  scope.async(exceptionHandler) {
        val deferred = async {
            delay(200)
            throw RuntimeException()
        }
        deferred.await()
    }
    //1
   // job.await()
    //2
    scope.launch {
        job.await()
    }

    Thread.sleep(1000)

}