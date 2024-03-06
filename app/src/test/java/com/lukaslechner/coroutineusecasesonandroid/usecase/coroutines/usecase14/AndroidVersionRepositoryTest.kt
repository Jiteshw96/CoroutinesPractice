package com.lukaslechner.coroutineusecasesonandroid.usecase.coroutines.usecase14

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14.AndroidVersionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AndroidVersionRepositoryTest {


    @Test
    fun `getLocalAndroidVersions should return data from local database`() = runTest{
        val fakeDatabase = FakeDatabase()
        val repository = AndroidVersionRepository(
            database = fakeDatabase,
            this
        )
        assertEquals(mockAndroidVersions,repository.getLocalAndroidVersions())
    }

    @Test
    fun `loadAndStoreRemoteAndroidVersions should return data from network`() = runTest {
        val fakeDatabase = FakeDatabase()
        val fakeAPI = FakeAPI()

        val repository = AndroidVersionRepository(
            database = fakeDatabase,
            scope = this,
            api = fakeAPI
        )
        assertEquals(mockAndroidVersions,repository.loadAndStoreRemoteAndroidVersions())
    }


    @Test
    fun `loadAndStoreRemoteAndroidVersions should continue to load and store android versions when calling scope gets cancelled `() = runTest{
        val fakeDatabase = FakeDatabase()
        val fakeAPI = FakeAPI()
        val applicationScope = this
        val repository = AndroidVersionRepository(
            database = fakeDatabase,
            scope =  this,
            api = fakeAPI
        )

        //share the testScheduler to make sure that process is running on same test thread
        // Sharing the testScheduler with the applicationScope is important!
        val viewModelScope = TestScope(this.testScheduler)
        val job = viewModelScope.launch {
            repository.loadAndStoreRemoteAndroidVersions()
        }

        //execute the current task until delay(1) in the fake API
        applicationScope.runCurrent()

        // Check if nothing is inserted into the db before we cancel the scope
        assertEquals(false,fakeDatabase.insertIntoDb)

        // Cancel the scope and check if it was indeed cancelled
        viewModelScope.cancel()
        assertTrue(job.isCancelled)

        //continue coroutine execution after delay(1) in the fakeAPI
        applicationScope.advanceTimeBy(1)
        applicationScope.runCurrent()

        assertEquals(true,fakeDatabase.insertIntoDb)
    }

}