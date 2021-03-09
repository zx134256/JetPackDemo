package hy.test.jetpackdemo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import hy.test.jetpackdemo.entry.DataSource
import hy.test.jetpackdemo.entry.DefaultDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LiveDataViewModel(val dataSource : DataSource) :ViewModel() {

    val currentTime = dataSource.getCurrentTime()

    val currentWeather = liveData<String> {
        emit("loading...")
        dataSource.fetchWeather().value?.let { emit(it) }
        emitSource(dataSource.fetchWeather())
    }

    val cacheValue = dataSource.cachedData


    fun onRefresh(){
        viewModelScope.launch {
            dataSource.fetchNewData()
        }
    }

}

class LiveDataViewModelFactory : ViewModelProvider.Factory{

    private val dataSource =  DefaultDataSource(Dispatchers.IO)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LiveDataViewModel(dataSource) as T
    }

}

object LiveDataVMFactory : ViewModelProvider.Factory {

    private val dataSource = DefaultDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LiveDataViewModel(dataSource) as T
    }
}
