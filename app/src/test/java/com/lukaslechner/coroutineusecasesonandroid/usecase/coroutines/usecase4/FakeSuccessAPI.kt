package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase4

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import kotlinx.coroutines.delay
import java.lang.IllegalArgumentException

class FakeSuccessAPI(private val responseDelay : Long) : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        delay(responseDelay)
       return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        delay(responseDelay)
       return when(apiLevel) {
           27 -> mockVersionFeaturesOreo
           28 -> mockVersionFeaturesPie
           29 -> mockVersionFeaturesAndroid10
           else -> throw IllegalArgumentException("api level not found")
       }
    }
}