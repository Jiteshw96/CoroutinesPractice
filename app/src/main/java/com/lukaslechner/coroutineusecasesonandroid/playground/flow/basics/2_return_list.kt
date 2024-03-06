package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.printWithTimePassed
import java.math.BigInteger

fun main(){
    val startTime = System.currentTimeMillis()
    factorialOf(5).forEach {
       printWithTimePassed(it,startTime)
    }


}


private fun factorialOf(number:Int) : List<BigInteger> = buildList{
    var factorail = BigInteger.ONE
    for(i in 1..number){
        Thread.sleep(10)
        factorail = factorail.multiply(BigInteger.valueOf(i.toLong()))
        add(factorail)
    }
}