package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase9

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.utils.addCoroutineDebugInfo
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DebuggingCoroutinesViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            Timber.d(addCoroutineDebugInfo("Initial coroutine launched"))
            try {
                val recentAndroidVersions = api.getRecentAndroidVersions()
                Timber.d(addCoroutineDebugInfo("Recent Versions Of Android Returned"))
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (e: Exception) {
                Timber.d(addCoroutineDebugInfo("Recent Versions Of Android Failed"))
               uiState.value = UiState.Error("Something Went Wrong")
            }

            val calculation1 = async {
                performCalculation1()
            }

            val calculation2 = async {
                performCaculation2()
            }
            val calculation3 = async {
                performCaculation3()
            }
            val calculation4 = async {
                performCaculation4()
            }
            val calculation5 = async {
                performCaculation5()
            }
            val calculation6 = async {
                performCaculation6()
            }
            val calculation7 = async {
                performCaculation7()
            }
            val calculation8 = async {
                performCaculation8()
            }

            val calculation9 = async {
                performCaculation9()
            }
            val calculation10 = async {
                performCaculation10()
            }
            val calculation11 = async {
                performCaculation11()
            }

            //Creating More Coroutines On Default to check if it is dependent on number of cpu core

            Timber.d(addCoroutineDebugInfo("Result Of Coroutine1 is ${calculation1.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation2.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation3.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation4.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation5.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation6.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation7.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation8.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation9.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation10.await()}"))
            Timber.d(addCoroutineDebugInfo("Result Of Coroutine2 is ${calculation11.await()}"))
            Timber.d(addCoroutineDebugInfo("Coroutine Finsihed"))
        }

    }

    private suspend fun performCalculation1() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 1"))
        delay(3000)
        Timber.d(addCoroutineDebugInfo("Calculation 1 completed"))
        13
    }

    private suspend fun performCaculation2() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 2"))
        delay(4000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }

    private suspend fun performCaculation3() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 3"))
        delay(5000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }
    private suspend fun performCaculation4() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 4"))
        delay(8000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }
    private suspend fun performCaculation5() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 5"))
        delay(9000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }
    private suspend fun performCaculation6() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 6"))
        delay(7000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }
    private suspend fun performCaculation7() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 7"))
        delay(4000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }
    private suspend fun performCaculation8() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 8"))
        delay(3000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }
    private suspend fun performCaculation9() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 9"))
        delay(6000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }
    private suspend fun performCaculation10() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 10"))
        delay(2000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }

    private suspend fun performCaculation11() = withContext(Dispatchers.Default){
        Timber.d(addCoroutineDebugInfo("Starting Calculation 11"))
        delay(2000)
        Timber.d(addCoroutineDebugInfo("Calculation 2 Completed"))
        23
    }


        /**
         *
         * Output for above program :
         *
         * Octa core system created 7 threads on default using one core for each
         *
         * [main] Initial coroutine launched
         * .898  7494-7494    [main] Recent Versions Of Android Returned
         * .924  7494-7615    [DefaultDispatcher-worker-1] Starting Calculation 1
         * .929  7494-76152   [DefaultDispatcher-worker-1] Starting Calculation 2
         * .930  7494-76163   [DefaultDispatcher-worker-2] Starting Calculation 3
         * .930  7494-76185   [DefaultDispatcher-worker-3] Starting Calculation 5
         * .932  7494-76166   [DefaultDispatcher-worker-2] Starting Calculation 6
         * .937  7494-76154   [DefaultDispatcher-worker-1] Starting Calculation 4
         * .937  7494-76198   [DefaultDispatcher-worker-4] Starting Calculation 8
         * .937  7494-76187   [DefaultDispatcher-worker-3] Starting Calculation 7
         * .938  7494-76159   [DefaultDispatcher-worker-1] Starting Calculation 9
         * .939  7494-7618    [DefaultDispatcher-worker-3] Starting Calculation 11
         * .940  7494-7619    [DefaultDispatcher-worker-4] Starting Calculation 10
         * .944  7494-7616    [DefaultDispatcher-worker-2] Calculation 2 Completed
         * .948  7494-7622 [DefaultDispatcher-worker-7] Calculation 2 Completed
         *     [DefaultDispatcher-worker-7] Calculation 1 completed
         * */

}