package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase10

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10.CalculationInBackgroundViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10.UiState
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CalculationInBackgroundViewModelTest {


    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    val mainDispatcherRule:TestRule = ReplaceMainDispatcherRule()

    val receivedStates = mutableListOf<UiState>()

    @Test
    fun `performCalculation() should return correct factorial results`() = runTest{
            val viewModel = CalculationInBackgroundViewModel(
                StandardTestDispatcher(testScheduler)
            ).apply {
                uiState().observeForever { uiState->
                    if(uiState!=null){
                        receivedStates.add(uiState)
                    }
                }
            }

        Assert.assertTrue(receivedStates.isEmpty())
        viewModel.performCalculation(5)
        runCurrent()
        Assert.assertTrue(receivedStates[0] is UiState.Loading)
        Assert.assertTrue(receivedStates[1] is UiState.Success)
        Assert.assertEquals((receivedStates[1] as UiState.Success).result,"120")
    }

}