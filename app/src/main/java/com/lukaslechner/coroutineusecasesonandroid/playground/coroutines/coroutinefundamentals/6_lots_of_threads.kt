package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinefundamentals

import kotlin.concurrent.thread

fun main(){
 repeat(1_00_000){
     thread {
         Thread.sleep(5000)
         println(".")
     }
 }
}
