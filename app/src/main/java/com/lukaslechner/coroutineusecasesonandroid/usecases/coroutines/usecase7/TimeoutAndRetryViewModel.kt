package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import kotlin.math.exp

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L
        // switch to branch "coroutine_course_full" to see solution

        // run api.getAndroidVersionFeatures(27) and api.getAndroidVersionFeatures(28) in parallel

        val oreoFeatures = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(27)
            }
        }
        val pieFeatures = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(28)
            }
        }

        viewModelScope.launch {
            try {
                val androidFeatures = listOf(oreoFeatures, pieFeatures).awaitAll()
                uiState.value = UiState.Success(androidFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network Request Failed")
            }
        }
    }

    suspend fun <T> retryWithTimeout(
        numberOfTries: Int,
        timeoutInMillis: Long,
        block: suspend () -> T
    ) = retry(numberOfTries) {
            withTimeout(timeoutInMillis) {
                block()
            }
        }

    suspend fun <T> retry(
        numberOfTries: Int,
        initialDelayInMillis: Long = 1000L,
        maxDelayInMillis: Long = 10000L,
        expoFactor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var expoDelay = initialDelayInMillis
        repeat(numberOfTries) {
            try {
                return block()
            } catch (e: Exception) {
                Timber.e(e)
            }
            delay(expoDelay)
            expoDelay = (expoDelay * expoFactor).toLong().coerceAtMost(maxDelayInMillis)
        }
        return block()
    }
}