package hy.test.jetpackdemo.viewModel

import android.util.Log
import androidx.lifecycle.*
import hy.test.jetpackdemo.Repository.NetWorkRepository
import hy.test.jetpackdemo.entry.DefaultDataSource
import hy.test.jetpackdemo.entry.HomeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NetWorkViewModel(val netWorkRepository: NetWorkRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    var homeInfo: LiveData<HomeInfo>? = liveData {
        val res = netWorkRepository.getHomeInfo()
        Log.d("zx", res.toString())
        emit(res)
    }


    init {
        viewModelScope.launch {
//            Log.d("zx","start network viewmodel")
//            val res = netWorkRepository.getHomeInfo()
//            homeInfo = MutableLiveData(res)
//            Log.d("zx","response" +res.status )
            homeInfo = liveData {
                val res = netWorkRepository.getHomeInfo()
                Log.d("zx", res.toString())
                emit(res)
            }


//            var  counter = 1
//            while (true) {
//                delay(5000)
//                counter++
//                homeInfo?.value?.sex = "nan$counter"
//                homeInfo?.value?.notifyChange()
//            }
        }
    }
}

class NetWorkViewModelFactory(private val netWorkRepository: NetWorkRepository) :
    ViewModelProvider.Factory {

    private val dataSource = DefaultDataSource(Dispatchers.IO)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NetWorkViewModel(netWorkRepository) as T
    }

}