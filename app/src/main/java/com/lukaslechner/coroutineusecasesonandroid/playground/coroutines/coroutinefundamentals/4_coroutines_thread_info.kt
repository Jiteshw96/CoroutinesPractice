package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinefundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts working")
    joinAll(
        async { threadInfoCoroutine(1,500)  },
        async { threadInfoCoroutine(2,300) }
    )
    println("main ends working")
}


suspend fun threadInfoCoroutine(number:Int,delay:Long) {
    println("$number starts working on ${Thread.currentThread().name}")
    delay(delay)
    println("$number ends working on ${Thread.currentThread().name}")

}