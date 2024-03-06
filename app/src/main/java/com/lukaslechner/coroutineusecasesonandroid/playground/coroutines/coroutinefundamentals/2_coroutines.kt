package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinefundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    println("Main starts working")
    joinAll(
        async { coroutine(1,500) },
        async { coroutine(2,300) }
    )
    println("main finished working")

}

suspend fun coroutine(number:Int,delayInMillis:Long){
    println("$number starts working")
    delay(delayInMillis)
    println("$number finished works")
}