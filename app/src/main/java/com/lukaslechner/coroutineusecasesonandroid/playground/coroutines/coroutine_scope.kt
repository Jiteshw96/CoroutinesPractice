package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

 suspend fun main(){
        coroutineScope {
            launch { checking() }
            egg()
        }
    println("main function ended")
}

fun egg() = print("egg called")

suspend fun checking() = withContext(Dispatchers.IO){
    println("Checking fun called")
}