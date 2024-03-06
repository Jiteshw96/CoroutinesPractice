package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase12

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.CalculationInSeveralCoroutinesViewModel
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.FactorialCalculator
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase12.UiState
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CalculationInSeveralCoroutinesViewModelTest {

    /**
     * This is a JUnit rule provided by the AndroidX Test Library.
     * It's specifically designed for testing LiveData and ViewModel classes in Android.
     * This rule ensures that LiveData operations are executed synchronously on
     * the same thread in which they are called during tests.
     *
     * */
    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    /**
     * Android environment uses main dispatcher but in test environment we have to replace it with test dispatcher
     * We use TestWatcher which is used to control test lifecycle
     * In starting we set the testDispatcher as main
     * In finished we reset it.
     *
     * This ensure controlled execution as we are using TestDispatcher which executes the coroutines synchronously for expected behaviour
     *
     * */
    @get:Rule
    val mainDispatcherRule = ReplaceMainDispatcherRule()

    val receivedStates = mutableListOf<UiState>()


    /***
     * Ways To Synchronously run the coroutines
     * 1) Pass UnConfined Dispatcher :
     *    -  This Dispatcher will execute the coroutines immediately on current thread
     * as our calculations are getting executed in a synchronous way.
     *    - If we don't pass the UnConfined Dispatcher Default.Dispatcher will be used and code will
     * be executed asynchronously on a background thread.
     *    - We can use UnconfinedTestDispatcher to synchronously execute coroutines in a test.
     * UnconfinedTestDispatcher is designed to execute coroutines immediately on the current thread,
     * making it suitable for testing scenarios where you want to ensure synchronous execution of coroutines.
     *
     *
     * 2)  Using StandardTestDispatcher
     *     - StandardTestDispatcher is a dispatcher provided by the kotlinx-coroutines-test library specifically
     * for testing suspending functions and coroutines.
     *
     *     - It provides functions to run the coroutines in a blocking way
     *
     *     - Coroutines launched with StandardTestDispatcher do not execute immediately unless they are explicitly
     * started using methods like TestCoroutineScheduler.runCurrent or TestCoroutineScheduler.advanceUntilIdle.
     *     - It uses TestCoroutineScheduler to execute tasks in a controlled manner, making it suitable for testing
     *scenarios where you need to control the timing and execution of coroutines.
     *
     * Summary :
     *          Unlike StandardTestDispatcher, Unconfined dispatcher does not provide explicit control over the
     *execution of coroutines. Coroutines launched with Unconfined dispatcher may execute immediately or
     *be suspended depending on the current thread's state.
     *
     * */







    /**
     * By passing the UnconfinedTestDispatcher() we can execute the coroutines immediately in below test
     * calculation of factorial will happen on Unconfined Dispatcher and our code is executing in a synchronous way
     *
     * - If we don't pass the dispatcher assert will happen before calculateFactorial function and test will fail.
     * - If we don't pass the dispatcher calculateFactorial will run on main thread as it is launched from that.
     * **/

    @Test
    fun `performCalculation() should return success with correct calculations`() = runTest {
        val dispatcher = UnconfinedTestDispatcher()
        val viewModel = CalculationInSeveralCoroutinesViewModel(defaultDispatcher = dispatcher, factorialCalculator = FactorialCalculator(dispatcher))

        Assert.assertTrue(receivedStates.isEmpty())

        viewModel.uiState().observeForever { uiState->
            if(uiState!=null){
                receivedStates.add(uiState)
            }
        }
        viewModel.performCalculation(5,3)

        Assert.assertTrue(receivedStates[0] is UiState.Loading)
        Assert.assertTrue(receivedStates[1] is UiState.Success)
        Assert.assertEquals((receivedStates[1] as UiState.Success).result,"120")
    }

    /***
     * With Standard Dispatcher needs call to runCurrent()
     *
     */

    @Test
    fun `performCalculation() should return success with correct calculations with Standard Dispatcher`() = runTest {

        val viewModel = CalculationInSeveralCoroutinesViewModel(defaultDispatcher = StandardTestDispatcher(testScheduler), factorialCalculator = FactorialCalculator(StandardTestDispatcher(testScheduler)))

        Assert.assertTrue(receivedStates.isEmpty())

        viewModel.uiState().observeForever { uiState->
            if(uiState!=null){
                receivedStates.add(uiState)
            }
        }
        viewModel.performCalculation(5,3)
        runCurrent()
        Assert.assertTrue(receivedStates[0] is UiState.Loading)
        Assert.assertTrue(receivedStates[1] is UiState.Success)
        Assert.assertEquals((receivedStates[1] as UiState.Success).result,"120")
    }

    @Test
    fun `performCalculation() should return success with correct calculations with DummyFactorial`() = runTest{
        val dispatcher = UnconfinedTestDispatcher()
        val viewModel = CalculationInSeveralCoroutinesViewModel(defaultDispatcher = dispatcher, factorialCalculator = DummyFactorialCalculator())

        Assert.assertTrue(receivedStates.isEmpty())

        viewModel.uiState().observeForever { uiState->
            if(uiState!=null){
                receivedStates.add(uiState)
            }
        }
        viewModel.performCalculation(5,3)

        Assert.assertTrue(receivedStates[0] is UiState.Loading)
        Assert.assertTrue(receivedStates[1] is UiState.Success)
        Assert.assertEquals((receivedStates[1] as UiState.Success).result,"120")
    }
}