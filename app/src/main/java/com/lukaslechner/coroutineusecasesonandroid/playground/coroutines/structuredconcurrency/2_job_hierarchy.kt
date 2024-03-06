package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(){
    //Coroutines forms the hierarchy

    val scopeJob = Job()
    val passedJob = Job()
    val scope = CoroutineScope(Dispatchers.Default+scopeJob)



    val coroutineJob = scope.launch(passedJob) {
        println("starting coroutine")
        delay(1000)
    }

    println("passedJob and coroutineJob are references to the same job object: ${passedJob === coroutineJob}")

    println("Is coroutineJob a child of scopeJob? =>${scopeJob.children.contains(coroutineJob)}")
}