package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import okhttp3.Dispatcher
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CalculationInBackgroundViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : BaseViewModel<UiState>() {

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        //write a function to calculate the factorial of a number
        //write a function to convert this number to string
        var job : Job? = null

        job =  viewModelScope.launch {
            try {

                var result : BigInteger
                val computationForCalculation = measureTimeMillis {
                    result = calculateFunction(factorialOf)
                }

                var stringValue :String
                val computationForStringConversion = measureTimeMillis {
                    stringValue = convertToString(result)
                }
                uiState.value = UiState.Success(stringValue,computationForCalculation,computationForStringConversion)

            } catch (exception: Exception) {
                UiState.Error("Error while calculating the result")
            }
        }

        viewModelScope.launch {
            delay(2000)
            Timber.d("Calculation cancelled")
            job.cancel()
        }
    }

    private suspend fun calculateFunction(number: Int) = withContext(defaultDispatcher) {
        //With out co-operative cancellation
        Timber.d("Calculation started")
        var factorial = BigInteger.ONE
        for (i in 1..number) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        Timber.d("Calculation completed value is $factorial")
        factorial
    }


    private suspend fun convertToString(number: BigInteger) = withContext(defaultDispatcher){
        number.toString()

    }


}