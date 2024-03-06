package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase6

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6.RetryNetworkRequestViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase6.UiState
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
@OptIn(ExperimentalCoroutinesApi::class)
class RetryNetworkRequestViewModelTest  {


    @get:Rule
    val instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule: TestRule = ReplaceMainDispatcherRule()

    val receivedUiStates = mutableListOf<UiState>()


    @Test
    fun `performNetworkRequest() should return success UiState when network request is successful`() = runTest{
        val responseDelay = 1000L
        val fakeSuccessAPI = FakeSuccessAPI(responseDelay)
        val viewModel = RetryNetworkRequestViewModel(fakeSuccessAPI).apply {
            observe()
        }

        Assert.assertTrue(receivedUiStates.isEmpty())

        viewModel.performNetworkRequest()

        advanceUntilIdle()

        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockAndroidVersions)
            ),
            receivedUiStates
        )
    }

    @Test
    fun `performNetworkRequest() should retry the request two times`() = runTest {
        val responseDelay = 1000L
        val fakeSuccessAPI = FakeSuccessAPIThreeAttempts(responseDelay)
        val viewModel = RetryNetworkRequestViewModel(fakeSuccessAPI).apply {
            observe()
        }

        Assert.assertTrue(receivedUiStates.isEmpty())

        viewModel.performNetworkRequest()

        advanceUntilIdle()

        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockAndroidVersions)
            ),
            receivedUiStates
        )

        Assert.assertEquals(
            3,
            fakeSuccessAPI.retryCount
        )

        Assert.assertEquals(
                6000,
            currentTime
        )

    }


    @Test
    fun `performNetworkRequest() should return error UiState after three unsuccessful requests`() = runTest{
        val responseDelay = 1000L
        val fakeErrorAPI = FakeErrorAPI(responseDelay)
        val viewModel = RetryNetworkRequestViewModel(fakeErrorAPI).apply {
            observe()
        }
        Assert.assertTrue(receivedUiStates.isEmpty())

        viewModel.performNetworkRequest()
        advanceUntilIdle()

        Assert.assertEquals(
            3,
            fakeErrorAPI.retryCount
        )

        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network request failed")
            ),
            receivedUiStates
        )
    }


    fun RetryNetworkRequestViewModel.observe(){
        uiState().observeForever { uiState->
            if(uiState!=null){
                receivedUiStates.add(uiState)
            }
        }
    }
}