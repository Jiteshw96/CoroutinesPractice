package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinefundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    println("Main Starts")
    joinAll(
        async { coroutineSuspends(1,500)  },
        async { coroutineSuspends(2,300) },
        async {
            repeat(5){
                println("I am using the thread ${Thread.currentThread().name} as other functions are suspended")
            }
        }
    )
    println("Main ends")

}

suspend fun coroutineSuspends(number:Int,delay:Long){
    println("$number starts working on ${Thread.currentThread().name}")
    delay(delay)
    println("$number ends working on ${Thread.currentThread().name}")

}