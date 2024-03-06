package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch

class RoomAndCoroutinesViewModel(
    private val api: MockApi,
    private val database: AndroidVersionDao
) : BaseViewModel<UiState>() {

    fun loadData() {
        uiState.value = UiState.Loading.LoadFromDb
        viewModelScope.launch {

            val localAndroidVersions = database.getAndroidVersions()
            if(localAndroidVersions.isEmpty()){
                uiState.value = UiState.Error(DataSource.DATABASE,"Database is empty")
            } else {
                uiState.value = UiState.Success(DataSource.DATABASE,localAndroidVersions.mapToUiModelList())
            }

            uiState.value = UiState.Loading.LoadFromNetwork
            try {
                val recentAndroidVersions = api.getRecentAndroidVersions()
                uiState.value = UiState.Success(DataSource.NETWORK,recentAndroidVersions)
                recentAndroidVersions.mapIndexed { index, androidVersion ->
                    //For testing Database
                    if(index != 1){
                        database.insert(androidVersion.mapToEntity())
                    }
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(DataSource.NETWORK,"Network Request Failed")
            }
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            database.clear()
        }
    }
}

enum class DataSource(val dataSourceName: String) {
    DATABASE("Database"),
    NETWORK("Network")
}