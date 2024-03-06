package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase14

import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14.AndroidVersionDao
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14.AndroidVersionEntity
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14.mapToEntityList

class FakeDatabase : AndroidVersionDao {

    var insertIntoDb = false

    override suspend fun getAndroidVersions(): List<AndroidVersionEntity> {
        return mockAndroidVersions.mapToEntityList()
    }

    override suspend fun insert(androidVersionEntity: AndroidVersionEntity) {
       insertIntoDb = true
    }

    override suspend fun clear() {

    }
}