package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.withIndex


suspend fun main(){
    val hashMap = HashMap<Int,Int>()

    //Filter repetitive numbers
    flowOf(1,3,4,5,6,7,7,8,9)
        .withIndex()
        .map{
            if(hashMap.containsKey(it.value)){
                hashMap[it.value] = it.value + 1
            }else{
                hashMap[it.value] = 0
            }
            hashMap
        }.map {
            it.filter {
                it.value > 0
            }
        }.collect{
            it.forEach {
                println(it.key)
            }
        }

    //2nd Solution
    val flow = flowOf(1,3,4,5,6,7,7,8,9)
    flow.toList().groupBy {
        it
    }.filter {
        it.value.count() > 1
    }.map {
        println(it)
    }
}