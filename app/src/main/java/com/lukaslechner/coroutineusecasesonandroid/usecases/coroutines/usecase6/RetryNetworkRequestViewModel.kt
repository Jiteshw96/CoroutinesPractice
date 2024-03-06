package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6

import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker.Result.retry
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.exp

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
          try {
              val androidVersions =  retry(2){
                    Timber.tag("Testing").d("Trying Request")
                    api.getRecentAndroidVersions()
                }
              uiState.value = UiState.Success(androidVersions)
          } catch (e: Exception) {
             uiState.value = UiState.Error("Network request failed")
          }
        }
    }
}

suspend fun <T> retry(
    times:Int,
    initialDelay:Long = 1000L,
    maxDelay:Long = 10000L,
    expoFactor:Double = 2.0,
    block: suspend  () -> T
): T {
    var expoDelay = initialDelay
    repeat(times){
        try {
           return block()
        } catch (e: Exception) {
            Timber.e(e)
        }
        delay(expoDelay)
        expoDelay = (expoDelay * expoFactor).toLong().coerceAtMost(maxDelay)
    }
   return  block()
}