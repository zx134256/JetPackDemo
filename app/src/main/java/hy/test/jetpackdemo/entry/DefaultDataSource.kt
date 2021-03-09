package hy.test.jetpackdemo.entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DefaultDataSource(val ioDispatcher: CoroutineDispatcher) :DataSource{

    private val _cachedData = MutableLiveData("This is old data")
    override val cachedData: LiveData<String>
        get() =_cachedData

    override fun getCurrentTime(): LiveData<Long> {
       return liveData<Long> {
           while (true){
               delay(1000)
               emit(System.currentTimeMillis())
           }
       }
    }

    private val weatherConditions = listOf("Sunny", "Cloudy", "Rainy", "Stormy", "Snowy")
    override fun fetchWeather(): LiveData<String> = liveData {
        var counter = 0
        while (true){
            delay(2000)
            counter++
            emit(weatherConditions[counter % weatherConditions.size])
        }
    }

    private var counter = 0
    // Using ioDispatcher because the function simulates a long and expensive operation.
    private suspend fun simulateNetworkDataFetch(): String = withContext(ioDispatcher) {
        delay(3000)
        counter++
        "New data from request #$counter"
    }


    override suspend fun fetchNewData() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            _cachedData.value = "Fetching new data..."
            _cachedData.value = simulateNetworkDataFetch()
        }
    }


}

interface DataSource{
    fun getCurrentTime() : LiveData<Long>
    fun fetchWeather(): LiveData<String>
    val cachedData: LiveData<String>
    suspend fun fetchNewData()
}