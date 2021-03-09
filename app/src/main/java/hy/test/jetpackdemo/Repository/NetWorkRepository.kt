package hy.test.jetpackdemo.Repository

import hy.test.jetpackdemo.entry.HomeInfo
import io.reactivex.Observable
import retrofit2.Response

class NetWorkRepository {


    suspend fun getHomeInfo() : HomeInfo {
        return ApiClient.mApiServer.getHomeInfo()
    }
}