package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.launch

class ContinueCoroutineWhenUserLeavesScreenViewModel(
    private var repository: AndroidVersionRepository
) : BaseViewModel<UiState>() {

    // more information in this blogpost about "Coroutines & Patterns for work that shouldn't
    // be cancelled" =>
    // https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad

    fun loadData() {
        uiState.value = UiState.Loading.LoadFromDb
        viewModelScope.launch {
           try {
               val localVersions = repository.getLocalAndroidVersions()
               if(localVersions.isEmpty()){
                   uiState.value = UiState.Error(DataSource.Database,"Database empty!")
               }else{
                   uiState.value = UiState.Success(DataSource.Database,localVersions)
               }

           } catch (e: Exception) {
               uiState.value = UiState.Error(DataSource.Database,"Database Request Failed")
           }

            uiState.value = UiState.Loading.LoadFromNetwork
            try {
                val loadFromNetwork = repository.loadAndStoreRemoteAndroidVersions()
               // val loadFromNetwork = repository.loadAndStoreRemoteAndroidVersionsWithViewModelScope()
                uiState.value = UiState.Success(DataSource.Network,loadFromNetwork)
            } catch (e: Exception) {
                uiState.value = UiState.Error(DataSource.Network,"Network Request Failed")
            }

        }
    }

    fun clearDatabase() {
        repository.clearDatabase()
    }
}

sealed class DataSource(val name: String) {
    object Database : DataSource("Database")
    object Network : DataSource("Network")
}