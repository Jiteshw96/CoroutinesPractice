package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun simpleFlow() = flow{
    (1..3).forEach{delay(100); emit(it)}
}

fun main() = runBlocking {
    launch {
        (1..3).forEach{
            println("from main $it")
            delay(100)
        }
    }

    //Flow is non blocking due to delay
    /*simpleFlow().collect{
        println("from flow $it")
    }*/

    //Sequence is blocking due to thread.sleep
    simple().forEach {
        println("from sequence $it")
    }

}