package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase4

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import kotlinx.coroutines.delay
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.mockito.Mock
import retrofit2.HttpException
import retrofit2.Response
import java.lang.IllegalArgumentException

class FakeFeatureErrorAPI(private val responseDelay : Long) : MockApi {
    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        delay(responseDelay)
       throw HttpException(Response.error<List<AndroidVersion>>(500,ResponseBody.create(
           MediaType.parse("application/json"),"")))
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        delay(responseDelay)
       return  when(apiLevel){
           27 -> mockVersionFeaturesOreo
           28 -> mockVersionFeaturesPie
           29 -> mockVersionFeaturesAndroid10
           else -> throw IllegalArgumentException("api level not found")
       }
    }
}