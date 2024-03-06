package com.lukaslechner.coroutineusecasesonandroid.playground.coroutines.coroutinefundamentals

fun main(){
    println("main starts")
    routine(1,500)
    routine(2,300)
    println("main ends")
}

fun routine(number:Int, delay: Long){
    println("$number starts works")
    Thread.sleep(delay)
    println("$number ends works")
}