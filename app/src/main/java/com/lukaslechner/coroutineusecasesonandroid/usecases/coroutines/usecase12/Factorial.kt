package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12

import java.math.BigInteger

interface Factorial {

    suspend fun calculateFactorial(factorialOf: Int, numberOfCoroutines: Int):BigInteger

    suspend fun calculateFactorialOfSubRange(subRange: SubRange): BigInteger

    fun createSubRangeList(factorialOf: Int, numberOfSubRanges: Int) : List<SubRange>
}