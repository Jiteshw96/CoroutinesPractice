package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinefundamentals

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    repeat(1_00_000){
        launch {
            delay(5000)
            println(".")
        }
    }
}