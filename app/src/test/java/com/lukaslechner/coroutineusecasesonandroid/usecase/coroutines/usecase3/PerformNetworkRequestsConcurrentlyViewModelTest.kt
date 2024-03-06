package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3.PerformNetworkRequestsConcurrentlyViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3.UiState
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class PerformNetworkRequestsConcurrentlyViewModelTest {


    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = ReplaceMainDispatcherRule()

    private val receivedUiStates = mutableListOf<UiState>()


    @Test
    fun `performNetworkRequestsSequentially should return data after 3 times of delay`() = runTest {
        val responseDelay = 1000L

        //Arrange
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(FakeSuccessAPI(responseDelay))
        viewModel.observe()
        Assert.assertTrue(receivedUiStates.isEmpty())

        //Act
        viewModel.performNetworkRequestsSequentially()
        advanceUntilIdle()


        //Assert
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(
                    listOf(
                        mockVersionFeaturesOreo,
                        mockVersionFeaturesPie,
                        mockVersionFeaturesAndroid10
                    )
                )
            ), receivedUiStates
        )


        Assert.assertEquals(
            3000,
            currentTime
        )

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `performNetworkRequestConcurrently should return the data after response delay`() =
        runTest {
            //Arrange
            val responseDelay = 1000L
            val viewModel =
                PerformNetworkRequestsConcurrentlyViewModel(FakeSuccessAPI(responseDelay))
            //Act
            viewModel.observe()
            Assert.assertTrue(receivedUiStates.isEmpty())
            viewModel.performNetworkRequestsConcurrently()
            advanceUntilIdle()
            //Assert
            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(
                        listOf(
                            mockVersionFeaturesOreo,
                            mockVersionFeaturesPie,
                            mockVersionFeaturesAndroid10
                        )
                    )
                ), receivedUiStates
            )

            Assert.assertEquals(1000, currentTime)
        }

    @Test
    fun `performNetworkRequestConcurrently should return other two features when one of them fails`() = runTest {
        //Arrange
        val responseDelay = 1000L
        val viewModel = PerformNetworkRequestsConcurrentlyViewModel(FakeErrorAPI(responseDelay))
        Assert.assertTrue(receivedUiStates.isEmpty())

        //Act
        viewModel.observe()
        viewModel.performNetworkRequestsConcurrently()
        advanceUntilIdle()
        //Assert
        Assert.assertEquals(listOf(
            UiState.Loading,
            UiState.Success(listOf(mockVersionFeaturesOreo,mockVersionFeaturesAndroid10))
        ),receivedUiStates)
    }

    fun PerformNetworkRequestsConcurrentlyViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }

}