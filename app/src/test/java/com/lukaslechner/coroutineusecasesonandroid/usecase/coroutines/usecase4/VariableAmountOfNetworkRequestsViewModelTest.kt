package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase4

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4.UiState
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4.VariableAmountOfNetworkRequestsViewModel
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
class VariableAmountOfNetworkRequestsViewModelTest {


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

    val receivedUiStates = mutableListOf<UiState>()


    @Test
    fun `performNetworkRequestsSequentially should return success UiState on successful network request after 4000 milliseconds`() =
        runTest {
            //Arrange
            val responseDelay = 1000L
            val viewModel = VariableAmountOfNetworkRequestsViewModel(FakeSuccessAPI(responseDelay))
            Assert.assertTrue(receivedUiStates.isEmpty())

            //Act
            viewModel.observe()
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
                ),
                receivedUiStates
            )

            Assert.assertEquals(
                4000,
                currentTime
            )

        }

    @Test
    fun `performNetworkRequestsSequentially should return error UiState on failed recent-version-network request`() =
        runTest {
            //Arrange
            val responseDelay = 1000L
            val viewModel =
                VariableAmountOfNetworkRequestsViewModel(FakeErrorVersionsFeaturesAPI(responseDelay))
            Assert.assertTrue(receivedUiStates.isEmpty())

            //Act
            viewModel.observe()
            viewModel.performNetworkRequestsSequentially()
            advanceUntilIdle()
            //Assert

            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Something went wrong")
                ), receivedUiStates
            )
        }

    @Test
    fun `performNetworkRequestsSequentially should return error UiState on failed android-features-network request`() =
        runTest {
            //Arrange
            val responseDelay = 1000L
            val viewModel =
                VariableAmountOfNetworkRequestsViewModel(FakeFeatureErrorAPI(responseDelay))
            Assert.assertTrue(receivedUiStates.isEmpty())
            //Act
            viewModel.observe()
            viewModel.performNetworkRequestsSequentially()
            advanceUntilIdle()

            //Assert
            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Error("Something went wrong")
                ), receivedUiStates
            )
        }

    @Test
    fun `performNetworkRequestsConcurrently should return success UiState on successful network request after 2000 milliseconds`() =
        runTest {
            //Arrange
            val responseDelay = 1000L
            val viewModel = VariableAmountOfNetworkRequestsViewModel(FakeSuccessAPI(responseDelay))
            Assert.assertTrue(receivedUiStates.isEmpty())
            //Act
            viewModel.observe()
            viewModel.performNetworkRequestsConcurrently()
            advanceUntilIdle()
            //Assert
            Assert.assertEquals(
                listOf(
                    UiState.Loading,
                    UiState.Success(
                        listOf(
                            mockVersionFeaturesOreo, mockVersionFeaturesPie,
                            mockVersionFeaturesAndroid10
                        )
                    )
                ),
                receivedUiStates
            )

            Assert.assertEquals(2000,currentTime)
        }

    @Test
    fun `performNetworkRequestsConcurrently should return error UiState on unsuccessful recent-version-network request`() =
        runTest {
                //Arrange
                val responseDelay = 1000L
                val viewModel = VariableAmountOfNetworkRequestsViewModel(FakeFeatureErrorAPI(responseDelay))
                Assert.assertTrue(receivedUiStates.isEmpty())

                //Act
                viewModel.observe()
                viewModel.performNetworkRequestsConcurrently()
                advanceUntilIdle()

                //Assert
                Assert.assertEquals(
                    listOf(UiState.Loading,UiState.Error("something went wrong")),
                    receivedUiStates
                )
        }

    @Test
    fun `performNetworkRequestsConcurrently should return error UiState on unsuccessful android-features-network request`() =
        runTest {
                //Arrange
                val responseDelay = 1000L
                val viewModel = VariableAmountOfNetworkRequestsViewModel(FakeErrorVersionsFeaturesAPI(responseDelay))
                Assert.assertTrue(receivedUiStates.isEmpty())

                //Act
                viewModel.observe()
                viewModel.performNetworkRequestsConcurrently()
                advanceUntilIdle()

                //Assert
                Assert.assertEquals(
                    listOf(
                        UiState.Loading,
                        UiState.Error("Something went wrong")
                    ),
                    receivedUiStates
                )
        }


    fun VariableAmountOfNetworkRequestsViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }


}