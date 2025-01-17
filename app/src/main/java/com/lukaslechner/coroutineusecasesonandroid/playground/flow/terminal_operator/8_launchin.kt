package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

fun main() {
    val flow = flowOf(1,2,3)

    val scope = CoroutineScope(EmptyCoroutineContext)

    // This is same as launching two coroutine which run simultaneously 1- scope.launch() 2- scope.launch()

   /* flow
        .onEach { println("Received $it with launch() - 1") }
        .launchIn(scope)

    flow
        .onEach { println("Received $it with launch() - 2") }
        .launchIn(scope)*/


    // This wil run the two jobs in blocking way as they are under one parent coroutine
    scope.launch {
        flow.collect{
            println("Received $it with collect -1")
        }

        flow.collect{
            println("Received $it with collect -2")
        }
    }

    Thread.sleep(1000)
}