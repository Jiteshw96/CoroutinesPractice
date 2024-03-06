package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

suspend fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Exception caught ${throwable.toString()}")
    }

    val scope = CoroutineScope(Job())
    /**
     * Block #1
     * This block will print both the println statements as child will throw exception and parent will also be cancelled
     * */
  /* val job = scope.launch(exceptionHandler) {
        try {
            async {
                delay(2000)
                throw IllegalArgumentException("Something went wrong")
            }.await()
        }catch (exception:Exception){
            println("Try caught ${exception.toString()}")
        }
    }

    job.invokeOnCompletion {
        println("parent thread ${job.isCancelled}")
    }*/

    /**
     * Block #2
     * This block will print only try catch println statement as child will throw exception and parent will not be cancelled
     * */
    val job =  scope.launch(exceptionHandler) {
        supervisorScope {
            try {
                async {
                    delay(2000)
                    throw IllegalArgumentException("Something went wrong")
                }.await()
            }catch (exception:Exception){
                println("Try caught ${exception.toString()}")
            }
        }
    }

    job.invokeOnCompletion {
        println("parent thread ${job.isCancelled}")
    }

    Thread.sleep(10000)

}