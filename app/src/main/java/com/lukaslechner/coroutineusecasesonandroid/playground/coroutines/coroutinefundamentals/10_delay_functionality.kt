package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinefundamentals

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    println("main starts")
    async { delayDemonstration(1,5000) }.join()
    print("main ends")
}

suspend fun delayDemonstration(number:Int,delayed: Long){
    println("$number is working on ${Thread.currentThread().name}")
    //delay(delayed)
    Handler(Looper.getMainLooper()).postDelayed({
        println("$number is delayed on ${Thread.currentThread().name}")
    },500)
}