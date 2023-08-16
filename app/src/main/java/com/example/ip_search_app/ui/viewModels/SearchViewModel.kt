package com.example.ip_search_app.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ip_search_app.data.api.SearchRepository
import com.example.ip_search_app.models.SearchIpResponse
import com.example.ip_search_app.models.UserIpResponse
import com.example.ip_search_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
    val userIp: MutableLiveData<Resource<UserIpResponse>> = MutableLiveData()
    val ipInfo: MutableLiveData<Resource<SearchIpResponse>> = MutableLiveData()
    val latitude: MutableLiveData<Double> = MutableLiveData()
    val longitude: MutableLiveData<Double> = MutableLiveData()

    fun getUserIp(format: String) =
        viewModelScope.launch {
            userIp.postValue(Resource.Loading())
            val response = repository.getUserIp(format)
            if (response.isSuccessful) {
                response.body().let { res ->
                    userIp.postValue(Resource.Success(res))
                    getIpInformation(res?.ip ?: "null")
                }
            }
            else {
                userIp.postValue(Resource.Error(response.message()))
            }
        }
    fun getIpInformation(ip: String) =
        viewModelScope.launch {
            ipInfo.postValue(Resource.Loading())
            val response = repository.getSearchIpInfo(ip)
            if (response.isSuccessful) {
                response.body().let { res ->
                    ipInfo.postValue(Resource.Success(res))
                    val (lat, lon) = splitStringToFloats(res?.loc ?: "0,0")
                    latitude.postValue(lat)
                    longitude.postValue(lon)
                }
            }
            else {
                ipInfo.postValue(Resource.Error(response.message()))
            }
        }

    private fun splitStringToFloats(s: String): Pair<Double, Double> {
        val values = s.split(',')
        return Pair(values[0].toDouble(), values[1].toDouble())
    }
}