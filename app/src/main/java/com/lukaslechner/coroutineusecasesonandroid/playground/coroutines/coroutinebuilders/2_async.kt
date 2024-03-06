package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinebuilders

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    val deferred1  = async {
       val result =  getResult(1)
        println("Returned result from 1st call")
        result
    }

    val deferred2 = async {
        val result =  getResult(2)
        println("Returned result from 1st call")
        result
    }

    val resultList = listOf(deferred1.await(),deferred2.await())
    println(resultList)
}

suspend fun getResult(number:Int) : String{
    delay(100)
    return "result $number"
}