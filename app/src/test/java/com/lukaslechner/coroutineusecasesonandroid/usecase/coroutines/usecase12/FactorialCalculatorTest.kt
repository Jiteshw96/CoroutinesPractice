package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase12

import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.FactorialCalculator
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.SubRange
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.math.BigInteger

@OptIn(ExperimentalCoroutinesApi::class)
class FactorialCalculatorTest {

    @Test
    fun `createSubRange() should create correct subrange`(){
        val testDispatcher = UnconfinedTestDispatcher()
        val factorialCalculatorTest = FactorialCalculator(testDispatcher)

        Assert.assertEquals(
            listOf(
                SubRange(1,16),
                SubRange(17,32),
                SubRange(33,50)
            ),
            factorialCalculatorTest.createSubRangeList(50,3)
        )

        Assert.assertEquals(
            listOf(
                SubRange(1,3),
                SubRange(4,6),
                SubRange(7,9)
            ),
            factorialCalculatorTest.createSubRangeList(9,3)
        )
    }

    @Test
    fun `calculateFactorialOfSubRange() should return correct factorial`() = runBlocking {
        val dispatcher = UnconfinedTestDispatcher()
        val factorialCalculator = FactorialCalculator(dispatcher)

        Assert.assertEquals(
            BigInteger.valueOf(6),
            factorialCalculator.calculateFactorialOfSubRange(SubRange(1,3))
        )
    }

    @Test
    fun `calculateFactorial() should return correct factorial for user input`() = runBlocking{
        val dispatcher = UnconfinedTestDispatcher()
        val factorialCalculator = FactorialCalculator(dispatcher)

        Assert.assertEquals(
            BigInteger.valueOf(120),
            factorialCalculator.calculateFactorial(5,3)
        )
    }
}