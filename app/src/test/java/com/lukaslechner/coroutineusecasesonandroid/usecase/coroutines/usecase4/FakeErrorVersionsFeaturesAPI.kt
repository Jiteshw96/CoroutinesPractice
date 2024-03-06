package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase4

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import kotlinx.coroutines.delay
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.lang.IllegalArgumentException

class FakeErrorVersionsFeaturesAPI(private val responseDelay:Long) : MockApi {
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        delay(responseDelay)
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        delay(responseDelay)
       return when(apiLevel){
           27 -> mockVersionFeaturesOreo
           28 -> {
               throw HttpException(
                   Response.error<VersionFeatures>(500,
                       ResponseBody.create(MediaType.parse("application/json"),""))
               )
           }
           29-> mockVersionFeaturesAndroid10
           else-> throw IllegalArgumentException("Api level not found")
       }
    }
}