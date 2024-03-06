package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CooperativeCancellationViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var calculationJob: Job?  = null
    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
       calculationJob =  viewModelScope.launch {
            try {
                var factorialResult = BigInteger.ZERO

                val computationTime = measureTimeMillis {
                    factorialResult = calculateFactorial(factorialOf)
                    Timber.d("Calculating Returned")
                }

                var stringResult = ""
                val stringConversionTime = measureTimeMillis {
                    Timber.d("Calculating string conversion")
                    stringResult = convertToString(factorialResult)
                }
                uiState.value = UiState.Success(stringResult,computationTime,stringConversionTime)
            } catch (e: Exception) {
                uiState.value = if (e is CancellationException) {
                    UiState.Error("Calculation was cancelled")
                } else {
                    UiState.Error("Error while calculating result")
                }
            }

       }
    }

    fun cancelCalculation() {
            calculationJob?.cancel()
    }

    fun uiState(): LiveData<UiState> = uiState

    private val uiState: MutableLiveData<UiState> = MutableLiveData()

    private suspend fun convertToString(number:BigInteger) = withContext(Dispatchers.Default){
            number.toString()
        }


    private suspend fun calculateFactorial(number:Int) = withContext(Dispatchers.Default){
            var factorial = BigInteger.ONE
            for(i in 1..number){
                //This coroutine doesn't know if viewmodel.launch coroutine is cancelled
                yield()
                factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            }
            Timber.d("Calculating Factorial")
            factorial
        }
}