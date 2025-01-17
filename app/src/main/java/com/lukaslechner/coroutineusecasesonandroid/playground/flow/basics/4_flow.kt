package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.printWithTimePassed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigInteger

fun main() = runBlocking {5
    val startTime = System.currentTimeMillis()
    launch {
        calculateFactorialOf(5).collect{
            printWithTimePassed(it, startTime = startTime)
        }
    }
    println("Ready for more work")
}

private fun calculateFactorialOf(number : Int) : Flow<BigInteger> = flow  {
    var factorial = BigInteger.ONE
    for(i in 1..number){
     delay(100)
     factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
     emit(factorial)
    }
}.flowOn(Dispatchers.Default)