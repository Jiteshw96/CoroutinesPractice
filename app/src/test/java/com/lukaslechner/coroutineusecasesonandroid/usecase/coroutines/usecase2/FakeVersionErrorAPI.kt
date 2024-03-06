package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase2

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesOreo
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesPie
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.lang.IllegalArgumentException

class FakeVersionErrorAPI : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        throw HttpException(
            Response.error<List<AndroidVersion>>(500, ResponseBody.create(MediaType.parse("application/json"),""))
        )
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
       return  when(apiLevel){
           27 -> mockVersionFeaturesOreo
           28 -> mockVersionFeaturesPie
           29 -> mockVersionFeaturesAndroid10
           else -> throw IllegalArgumentException("api level not found")
       }
    }
}