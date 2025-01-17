package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch


suspend fun main() : Unit = coroutineScope{

    launch {
        val stocks = stockFlow().map {
            throw Exception("Exception in map")
        }

        stocks
            .onCompletion {cause ->
                if(cause.isNull()){
                    println("Flow completed successfully")
                }else{
                    println("Exception in flow $cause")
                }

            }
            .collect{stock->
                println("Collected $stock")
            }
    }
}


private fun stockFlow()  = flow{
    emit("MicroSoft")
    emit("Apple")

    throw Exception("Network Request Faild")
}

private fun Throwable?.isNull(): Boolean{
    return this != null
}