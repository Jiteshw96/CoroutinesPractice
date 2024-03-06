package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling

import kotlinx.coroutines.*


/**
 *
 * - Supervisor scope will allow it's children to continue to execute when one of it's children fails
 * - If exception for failed children is not handled it will lead to cancel entire scope and result 2 and result 3 won't be printed
 * - We have to handle the exceptions for child failures
 * - deferred4 won't throw the exception as await is not called
 * - Exception is failed when async is executed and exception is thrown when we try to access result
 * Note : If there is any unhandled exception it will lead to cancellation of whole scope whether it is supervisor or coroutine scope.
 *  */
fun main() = runBlocking {

    val ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught using handler $throwable in CoroutineExceptionHandler")
    }
   val scope = CoroutineScope(Job())

   scope.launch(ceh) {
      supervisorScope {
           val deferred1 = async {
               // This coroutine completes successfully
               delay(100)
               "Result from Deferred 1"
           }

           val deferred4 = async {
               throw ArithmeticException()
           }

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

           //Handle the exception
           val result2 = try {
               deferred2.await()
           }catch (e:Exception){
               "Exception Occurred"
           }

           //Another way of try catch
          // val result2 = runCatching { deferred2.await() }.getOrElse { "Caught: ${it.message}" }

           //Exception is not handled
          // val result2 = deferred2.await()

           val result3 = deferred3.await()
           println("Result 1: $result1")
           println("Result 2: $result2")
           println("Result 3: $result3")
       }
   }
   Thread.sleep(10000)



    //Example 2
    scope.launch(ceh) {
        supervisorScope {
            try {
                awaitAll(
                    async {
                        // This coroutine completes successfully
                        delay(100)
                        "Result from Deferred 1"
                    },

                    async {
                        throw ArithmeticException()
                    },

                    async {
                        // This coroutine throws an exception
                        throw RuntimeException("Exception in Deferred 2")
                    },

                    async {
                        // This coroutine completes successfully
                        delay(200)
                        "Result from Deferred 3"
                    }
                )
            }catch (exception:Exception){
                println("something went wrong")
            }
        }
    }

    Thread.sleep(10000)
}
