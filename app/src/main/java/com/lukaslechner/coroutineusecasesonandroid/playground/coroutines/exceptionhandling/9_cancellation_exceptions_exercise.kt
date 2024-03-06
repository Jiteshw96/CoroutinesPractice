package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling


import kotlinx.coroutines.*


/** - Cancellation exceptions won't cancel the whole scope and result 1 and result 3 will be printed
 * Note : In-case of any other exception it will cancel the scope
 */

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

            val deferred2 = async {
                // This coroutine throws an exception
                throw CancellationException("Exception in Deferred 2")
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
            val result3 = deferred3.await()

            println("Result 1: $result1")
            println("Result 2: $result2")
            println("Result 3: $result3")
        }
    }
    Thread.sleep(10000)
}