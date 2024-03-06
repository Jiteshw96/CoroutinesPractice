package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.value = UiState.Loading
        getAndroidVersionWithTimeout(timeout)
       // getAndroidVersionWithTimeoutOrNull(timeout)
    }


    fun getAndroidVersionWithTimeout(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentVersions = withTimeout(timeout) {
                    api.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(recentVersions)
            }catch (timeoutException: TimeoutCancellationException){
              uiState.value = UiState.Error("Request TimedOut")
            } catch (e: Exception) {
                uiState.value = UiState.Error("Something Went Wrong")
            }
        }
    }

    fun getAndroidVersionWithTimeoutOrNull(timeout:Long){
        viewModelScope.launch {
            try {
                val recentAndroidVersions = withTimeoutOrNull(timeout){
                    api.getRecentAndroidVersions()
                }

                recentAndroidVersions?.let {
                    uiState.value = UiState.Success(recentAndroidVersions)
                }?: run {
                    uiState.value = UiState.Error("Something went wrong")
                }
            } catch (timeoutException: TimeoutCancellationException) {
                uiState.value = UiState.Error("Requesttimeout")
            }
        }
    }

}