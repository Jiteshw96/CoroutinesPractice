package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase6

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.EndPointShouldNotBeCalledException
import kotlinx.coroutines.delay
import java.io.IOException

class FakeSuccessAPIThreeAttempts(private val responseDelay: Long) : MockApi {

    var retryCount = 0

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        retryCount++
        delay(responseDelay)
        if (retryCount < 3) {
            throw IOException()
        }
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndPointShouldNotBeCalledException()
    }
}