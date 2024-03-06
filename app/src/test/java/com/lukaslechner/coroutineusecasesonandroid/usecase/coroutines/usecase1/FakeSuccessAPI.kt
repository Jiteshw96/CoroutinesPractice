package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase1

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersionPie
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.EndPointShouldNotBeCalledException

class FakeSuccessAPI:MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
       return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndPointShouldNotBeCalledException()
    }
}