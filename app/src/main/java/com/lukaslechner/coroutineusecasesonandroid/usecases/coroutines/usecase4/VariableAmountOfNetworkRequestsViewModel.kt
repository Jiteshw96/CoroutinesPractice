package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {

            try {
                val listOfAndroidVersion = mockApi.getRecentAndroidVersions()
                val listOfFeatures = listOfAndroidVersion.map { androidVersion ->
                    mockApi.getAndroidVersionFeatures(androidVersion.apiLevel)
                }
                uiState.value = UiState.Success(listOfFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Something went wrong")
            }


        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            uiState.value = UiState.Error("Something went wrong")
            Log.d("Exception caught", throwable.toString())
        }

        viewModelScope.launch(exceptionHandler) {
            val listOfAndroidVersions =  try {
                  mockApi.getRecentAndroidVersions()
            } catch (exception: Exception) {
                uiState.value = UiState.Error("something went wrong")
                null
            }
            val featureList = listOfAndroidVersions?.map {
                async {
                    mockApi.getAndroidVersionFeatures(it.apiLevel)
                }
            }?.awaitAll()

            featureList?.let {
                uiState.value = UiState.Success(featureList)
            }
        }
    }
}