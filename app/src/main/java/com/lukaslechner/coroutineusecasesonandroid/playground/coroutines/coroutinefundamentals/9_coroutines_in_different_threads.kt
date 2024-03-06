package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinefundamentals

import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    println("main starts working")
    async { threadSwitchingCoroutine(1,500) }.join()
    println("main ends")
}

suspend fun threadSwitchingCoroutine(number:Int,delay: Long){
    print("$number starts working on ${Thread.currentThread().name}")
    delay(delay)
    withContext(Dispatchers.Default){
        println("$number ends working on ${Thread.currentThread().name}")
    }
}