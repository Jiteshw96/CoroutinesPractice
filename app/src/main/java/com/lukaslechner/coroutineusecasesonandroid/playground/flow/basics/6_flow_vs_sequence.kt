package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking  {
    //This will block the main thread as runBlock will run on same thread
    /** Launch is used inside runBlocking : runBlocking is blocking way that is it won't complete until all the coroutines
    are completed.
    It will print before launch, after launch (cause it creates the coroutine but doesn't wait for it to complete and proceed further)
    then it will print executing inside launch **/

    /** Control behaviour*/
    //1
    println("Before Launch")
    launch {
        //3
        println("executing inside launch")
        for (k in 1..3) {
            println("From main $k")
            delay(100)
        }
    }
    //2
    println("After Launch")
    simple().forEach { value -> println("sequence value $value") }
    println("End of program")
}