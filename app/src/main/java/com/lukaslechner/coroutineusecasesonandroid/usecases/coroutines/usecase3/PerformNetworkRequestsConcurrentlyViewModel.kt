package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    /**
     * Scope has context elements
     * Context Elements :
     * Dispatcher
     * Name
     * Exception Handler
     * JOb
     * */
    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreoFeature = mockApi.getAndroidVersionFeatures(27)
                val pieFeature = mockApi.getAndroidVersionFeatures(28)
                val android10Feature = mockApi.getAndroidVersionFeatures(29)
                val listOfAllFeatures = listOf(oreoFeature,pieFeature,android10Feature)
                uiState.value = UiState.Success(listOfAllFeatures)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Something Went Wrong")
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Timber.e("Exception has occurred")
        }

        viewModelScope.launch {
            supervisorScope {
                val oreoFeatures = async {
                    mockApi.getAndroidVersionFeatures(27)
                }

                val pieFeatures = async {
                    mockApi.getAndroidVersionFeatures(28)
                }

                val android10Features = async {
                    mockApi.getAndroidVersionFeatures(29)
                }

                val listOfAllFeatures =
                    listOf(oreoFeatures, pieFeatures, android10Features).mapNotNull {
                        try {
                            //Filter deferred objects which throw exceptions and return null from exception
                            it.await()
                        } catch (e: Exception) {
                            Timber.e("Exception is this", e.stackTrace)
                            null
                        }
                    }
                uiState.value = UiState.Success(listOfAllFeatures)
            }
        }
    }
}

