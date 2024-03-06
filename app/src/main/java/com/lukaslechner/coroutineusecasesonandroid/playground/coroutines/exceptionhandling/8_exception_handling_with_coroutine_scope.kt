package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling

import kotlinx.coroutines.*

/**
 *
 * - Coroutine scope will cancel it's children when one of it's children fails
 * - Even when exception for failed children is handled it will lead to cancel entire scope and result 1 and result 3 won't be printed
 * - Cancellation exceptions won't cancel the whole scope and result 1 and result 3 will be printed
 * - deferred4 will throw the exception as even when await is not called as it is unhandled exception
 * - Exception is failed when async is executed and exception is thrown when we try to access result
 * Note : If there is any unhandled exception it will lead to cancellation of whole scope whether it is supervisor or coroutine scope.
 *  */
fun main() = runBlocking {

    val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught using handler $throwable in CoroutineExceptionHandler")
    }
    val scope = CoroutineScope(Job())

    scope.launch(ceh) {
        coroutineScope {
            val deferred1 = async {
                // This coroutine completes successfully
                delay(100)
                "Result from Deferred 1"
            }

            /*val deferred4 = async {
                throw ArithmeticException()
            }*/

            val deferred2 = async {
                // This coroutine throws an exception
                throw RuntimeException("Exception in Deferred 2")
            }

            val deferred3 = async {
                // This coroutine completes successfully
                delay(200)
                "Result from Deferred 3"
            }

            // Waiting for results
            val result1 = deferred1.await()
            val result2 = try {
                deferred2.await()
            }catch (e:Exception){
                "Exception Occurred"
            }
            // val result2 = runCatching { deferred2.await() }.getOrElse { "Caught: ${it.message}" }
           // val result2 = deferred2.await()
            val result3 = deferred3.await()

            println("Result 1: $result1")
            println("Result 2: $result2")
            println("Result 3: $result3")
        }
    }.invokeOnCompletion {
        println("Completed with $it")
    }
    Thread.sleep(10000)
}

/**
 * If you want to cancel all the child coroutines when one of them fails use coroutine scope
 * further if you don't want to cancel the parent scope use try catch around coroutine scope
 *
 * If you don't want to cancel all the child coroutines when one of them fails use supervisor scope
 * further if you don't want to cancel the parent scope don't do anything supervisor scope will manage
 * */