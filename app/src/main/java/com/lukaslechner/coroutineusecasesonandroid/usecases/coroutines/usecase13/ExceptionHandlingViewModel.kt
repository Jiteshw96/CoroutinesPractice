package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.*
import timber.log.Timber

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {


    fun handleExceptionWithTryCatch() {
        // used to retry the api call we can start new coroutine in the scope
        /**
         * Handling Exception With Try Catch As there is no child coroutine exception will be caught in catch
         * */
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreoFeatures = api.getAndroidVersionFeatures(27)
                uiState.value = UiState.Success(listOf(oreoFeatures))
            } catch (e: Exception) {
                uiState.value = UiState.Error("Something Went Wrong")
            }
        }
    }

    fun handleExceptionInNestedCoroutineWithTryCatch() {
        /**
         * Install Coroutine Scope To Handle the exceptions from Child
         * Without CoroutineScope Async Will throw the Exception to parent and app will crash as launch doesn't have any exception handler
         * */
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                coroutineScope {
                    val versions = api.getRecentAndroidVersions()
                    val versionFeatures = versions.map { androidVersion ->
                        async {
                            api.getAndroidVersionFeatures(androidVersion.apiLevel)
                        }
                    }.awaitAll()
                    uiState.value = UiState.Success(versionFeatures)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error("Something Went Wrong")
            }
        }
    }


    fun handleWithCoroutineExceptionHandler() {
        //used for resource cleaning up as coroutine is already completed new coroutine
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            if (throwable is CancellationException) {
                uiState.value = UiState.Error("Coroutine Got Cancelled")
            } else {
                uiState.value = UiState.Error("Some Error Occurred")
            }
        }
        uiState.value = UiState.Loading
        viewModelScope.launch(exceptionHandler) {
            val versions = api.getRecentAndroidVersions()
            val versionFeatures = versions.map { androidVersion ->
                async {
                    api.getAndroidVersionFeatures(androidVersion.apiLevel)
                }
            }.awaitAll()
            uiState.value = UiState.Success(versionFeatures)
        }

    }

    fun showResultsEvenIfChildCoroutineFails() {
        /**
         * supervisorScope doesn't cancel the child coroutine but it doesn't manage the exceptions
         * To Manage the exceptions we need to have try catch for child coroutines
         * To manage the cancellation exception use try catch to outer scope as handler will ignore it
         * If cancellation exception is thrown whole coroutine should be cancelled
         * */
        uiState.value = UiState.Loading
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            if (throwable is CancellationException) {
                uiState.value = UiState.Error("Coroutine Got Cancelled")
            } else {
                uiState.value = UiState.Error("Some Error Occurred")
            }
        }
        viewModelScope.launch(exceptionHandler) {
            try {
                supervisorScope {
                    val androidVersions = api.getRecentAndroidVersions()
                    val featuresList = androidVersions.map { androidVersion ->
                        async {
                            api.getAndroidVersionFeatures(androidVersion.apiLevel)
                        }
                    }
                    val featureResult = featuresList.mapIndexedNotNull { index, result ->
                        try {
                           /* if(index == 1){
                                throw CancellationException()
                            }*/
                            result.await()
                        } catch (e: Exception) {
                            if(e is CancellationException){
                                throw e
                            }
                            Timber.d("Child Coroutine Failed with exception ${e.message}")
                            null
                        }
                    }
                    uiState.value = UiState.Success(featureResult)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error("Coroutine Is Cancelled")
            }

        }
    }
}