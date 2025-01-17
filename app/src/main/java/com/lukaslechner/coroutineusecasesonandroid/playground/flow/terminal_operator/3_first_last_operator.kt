package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking

fun main(){
    val flow = flow {
        println("emitting first value")
        emit(1)

        println("emitting second value")
        emit(2)
    }

    //First
    runBlocking {
        val  first = flow.first()
        println("Collected $first")
    }

    /*//Second
    runBlocking {
        val last = flow.last()
        println("Collected $last")
    }*/

    //Third
   /*runBlocking {
       val last = flow.first { it > 1 }
       println("Collected $last")
   }*/
}

