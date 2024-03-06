package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.math.BigInteger

class FactorialCalculator(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
):Factorial {


    override suspend fun calculateFactorial(
        factorialOf: Int, numberOfCoroutines: Int
    ) = withContext(defaultDispatcher) {

        val subRanges = createSubRangeList(factorialOf, numberOfCoroutines)

        val listOfBigInteger = subRanges.map {
            async {
                calculateFactorialOfSubRange(it)
            }
        }.awaitAll()

        val result = listOfBigInteger.fold(BigInteger.ONE) { acc, element ->
            ensureActive()
            acc.multiply(element)
        }
        result
    }

    override suspend fun calculateFactorialOfSubRange(
        subRange: SubRange
    ): BigInteger {
       return withContext(defaultDispatcher){
            var factorial = BigInteger.ONE
            for (i in subRange.start..subRange.end) {
                ensureActive()
                factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            }
            factorial
        }
    }

    override fun createSubRangeList(
        factorialOf: Int, numberOfSubRanges: Int
    ) : List<SubRange> {
        val quotient = factorialOf.div(numberOfSubRanges)
        val rangeList = mutableListOf<SubRange>()

        var startIndex = 1
        repeat(numberOfSubRanges - 1) {
            rangeList.add(
                SubRange(
                    startIndex,
                    startIndex + (quotient - 1)
                )
            )
            startIndex += quotient
        }
        rangeList.add(SubRange(startIndex, factorialOf))
      return  rangeList
    }
}


data class SubRange(val start: Int, val end: Int)