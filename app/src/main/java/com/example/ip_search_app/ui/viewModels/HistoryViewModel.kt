package com.example.ip_search_app.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ip_search_app.data.api.SearchRepository
import com.example.ip_search_app.models.SearchIpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {
    val getDataStorage: MutableLiveData<List<SearchIpModel>> = MutableLiveData()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getAllDataStorage()
        }
    }
    private suspend fun getAllDataStorage() =
        viewModelScope.launch() {
            getDataStorage.postValue(repository.getAllSearchDb())
        }
    suspend fun deleteItemForDb(item: SearchIpModel) {
        repository.deleteFromDb(item)
    }
}