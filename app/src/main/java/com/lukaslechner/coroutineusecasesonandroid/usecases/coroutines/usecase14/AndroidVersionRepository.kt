package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AndroidVersionRepository(
    private var database: AndroidVersionDao,
    private val scope: CoroutineScope,
    private val api: MockApi = mockApi()
) {

    suspend fun getLocalAndroidVersions(): List<AndroidVersion> {
        return database.getAndroidVersions().mapToUiModelList()
    }

    //Continue to run operation when user leaves the screen
    suspend fun loadAndStoreRemoteAndroidVersions(): List<AndroidVersion> {
        return scope.async {
            val recentAndroidVersions = api.getRecentAndroidVersions()
            Timber.tag("Coroutines Database").d("Fetching from network completd")
            recentAndroidVersions.forEach {
                database.insert(it.mapToEntity())
            }
            Timber.tag("Coroutines Database").d("Storing to DB completed")
            recentAndroidVersions
        }.await()
    }

    //Retrofit Internally checks ensureActive and it will be cancelled
    suspend fun loadAndStoreRemoteAndroidVersionsWithViewModelScope() = withContext(Dispatchers.IO){
        val recentAndroidVersions = api.getRecentAndroidVersions()
        Timber.tag("Coroutines Database").d("Fetching from network completd")
        recentAndroidVersions.forEach {
            database.insert(it.mapToEntity())
        }
        Timber.tag("Coroutines Database").d("Storing to DB completed")
        recentAndroidVersions
    }

    fun clearDatabase() {
       scope.launch {
           database.clear()
       }
    }
}