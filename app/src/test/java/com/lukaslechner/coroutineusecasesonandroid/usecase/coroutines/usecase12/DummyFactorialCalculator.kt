package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase12

import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.Factorial
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.SubRange
import java.math.BigInteger

class DummyFactorialCalculator :Factorial {

    override suspend fun calculateFactorial(factorialOf: Int, numberOfCoroutines: Int): BigInteger {
       return BigInteger.valueOf(120);
    }

    override suspend fun calculateFactorialOfSubRange(subRange: SubRange): BigInteger {
        return BigInteger.valueOf(5)
    }

    override fun createSubRangeList(factorialOf: Int, numberOfSubRanges: Int): List<SubRange> {
       return listOf(SubRange(1,3))
    }
}