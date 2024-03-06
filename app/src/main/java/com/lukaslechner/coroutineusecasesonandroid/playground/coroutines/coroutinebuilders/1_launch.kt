package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    val job = launch(start= CoroutineStart.LAZY) {
        networkRequest()
    }
    delay(200)
    println("start job")
    job.start()
    println("finish run blocking")
}

suspend fun networkRequest(){
    println("Network Started")
    delay(100)
    println("Network request finished")
}