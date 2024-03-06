package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase14

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.EndPointShouldNotBeCalledException
import kotlinx.coroutines.delay

class FakeAPI : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
       delay(1)
       return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndPointShouldNotBeCalledException()
    }
}