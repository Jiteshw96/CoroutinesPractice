package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase5

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5.NetworkRequestWithTimeoutViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5.UiState
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkRequestWithTimeoutViewModelTest {


    @get:Rule
    val dispatcherRule: TestRule = ReplaceMainDispatcherRule()


    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    val receivedStates = mutableListOf<UiState>()


    @Test
    fun `performNetworkRequest() should return success UiState on successful network request within timeout`() =
        runTest {
            val responseDelay = 1000L
            val timeout = 1001L
            val successAPI = FakeSuccessAPI(responseDelay)
            val viewModel = NetworkRequestWithTimeoutViewModel(successAPI)
            viewModel.observe()

            Assert.assertTrue(receivedStates.isEmpty())

            viewModel.performNetworkRequest(timeout)

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(mockAndroidVersions)
                ),
                receivedStates
            )
        }


    @Test
    fun `performNetworkRequest() should return error UiState with timeout error message if timeout gets exceeded`() =
        runTest {
            val responseDelay = 1000L
            val timeout = 999L
            val successAPI = FakeSuccessAPI(responseDelay)
            val viewModel = NetworkRequestWithTimeoutViewModel(successAPI)
            viewModel.observe()

            Assert.assertTrue(receivedStates.isEmpty())

            viewModel.performNetworkRequest(timeout)

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Request TimedOut")
                ),
                receivedStates
            )
        }

    @Test
    fun `performNetworkRequest() should return error UiState when network request fails`() =
        runTest {
            val responseDelay = 1000L
            val timeout = 1001L
            val fakeErrorAPI = FakeErrorAPI(responseDelay)
            val viewModel = NetworkRequestWithTimeoutViewModel(fakeErrorAPI)
            viewModel.observe()

            Assert.assertTrue(receivedStates.isEmpty())

            viewModel.performNetworkRequest(timeout)

            advanceUntilIdle()

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Something Went Wrong")
                ),
                receivedStates
            )


        }


    private fun NetworkRequestWithTimeoutViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedStates.add(uiState)
            }
        }
    }

}