package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1.PerformSingleNetworkRequestViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1.UiState
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.rules.TestRule

class PerformSingleNetworkRequestWithViewModelTest {


    //Used for live data to make it work in synchronous way
    @get:Rule
    val testInstantTaskExecutorRule : TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val replaceMainDispatcherRule  = ReplaceMainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return success when network request is successful`() = runTest {
        //Arrange
        val mockAPI = FakeSuccessAPI()
        val viewModel = PerformSingleNetworkRequestViewModel(mockAPI)
        val listOfStates = mutableListOf<UiState>()
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                listOfStates.add(uiState)
            }
        }

        assertTrue(listOfStates.isEmpty())
        viewModel.performSingleNetworkRequest()

        assertEquals(
            listOf(UiState.Loading,UiState.Success(mockAndroidVersions)),
            listOfStates
        )
    }

    @Test
    fun `should return error when api fails`(){
        val mockAPI = FakeErrorAPI()
        val viewModel  = PerformSingleNetworkRequestViewModel(mockAPI)
        val listOfStates = mutableListOf<UiState>()
        viewModel.uiState().observeForever{uiState->
            if(uiState!=null){
                listOfStates.add(uiState)
            }
        }

        assertTrue(listOfStates.isEmpty())

        viewModel.performSingleNetworkRequest()
        assertEquals(
            listOf(UiState.Loading,UiState.Error("Something went wrong")),
            listOfStates
        )
    }
}