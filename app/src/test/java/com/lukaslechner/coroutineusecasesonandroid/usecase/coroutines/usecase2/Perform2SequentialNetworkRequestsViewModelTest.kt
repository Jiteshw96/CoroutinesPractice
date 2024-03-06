package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.Perform2SequentialNetworkRequestsViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.UiState
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class Perform2SequentialNetworkRequestsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule : TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val replaceMainDispatcherRule  = ReplaceMainDispatcherRule()

    private val receivedUIStates = mutableListOf<UiState>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return success when both request are successful`()  = runTest{

        //Arrange
        val viewModel = Perform2SequentialNetworkRequestsViewModel(FakeSuccessAPI())
        viewModel.observe()

        Assert.assertTrue(receivedUIStates.isEmpty())

        //Act
        viewModel.perform2SequentialNetworkRequest()

        //Assert
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockVersionFeaturesAndroid10)
            ),
            receivedUIStates
        )
    }

    @Test
    fun `should return failed when first request is failed`() = runTest {

        val viewModel  = Perform2SequentialNetworkRequestsViewModel(FakeVersionErrorAPI())
        viewModel.observe()

        Assert.assertTrue(receivedUIStates.isEmpty())

        viewModel.perform2SequentialNetworkRequest()

        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Something went wrong")
            ),
            receivedUIStates
        )
    }

    @Test
    fun `should return failed when second request is failed`() = runTest {
        //Arrange
        val viewModel = Perform2SequentialNetworkRequestsViewModel(FakeFeaturesAPIError())
        viewModel.observe()

        Assert.assertTrue(receivedUIStates.isEmpty())

        //Act
        viewModel.perform2SequentialNetworkRequest()

        //Assert
        Assert.assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Something went wrong")
            ),
            receivedUIStates
        )
    }


    fun Perform2SequentialNetworkRequestsViewModel.observe(){
        uiState().observeForever {uiState ->
            if(uiState !=null){
                receivedUIStates.add(uiState)
            }
        }
    }

}