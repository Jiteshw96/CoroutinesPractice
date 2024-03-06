package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(10) { index ->
            try {
                if(isActive){
                    println("starting delay in job 1")
                    delay(100)
                    println("working in job 1 ${Thread.currentThread().name}")
                }
            } catch (e: CancellationException) {
                println("Job 1 cancelled")
                throw CancellationException()
            }
        }
    }
    delay(250)
    job.cancel()

    val job2 = GlobalScope.launch {
        repeat(10){ index ->
            try {
                if(isActive){
                    delay(100)
                    println("working in job 2 ${Thread.currentThread().name}")
                }
            } catch (e: Exception) {
                println("Job 2 cancelled")
                throw CancellationException()
            }
        }
    }
    delay(250)
    job2.cancel()
    println("main ends")

}
