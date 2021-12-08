package com.test.searchacro

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.searchacro.domain.ApiError
import com.test.searchacro.network.Acronyms
import com.test.searchacro.network.Result
import com.test.searchacro.repository.AcronymRepo
import com.test.searchacro.repository.AcronymRepository
import kotlinx.coroutines.*

class SearchActivityViewModel(val repository: AcronymRepo, val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)): ViewModel() {

    val searchText = ObservableField<String>()
    val searchData = MutableLiveData<List<SearchViewModel>>()

    private var searchJob: Job? = null

    init {
        searchText.addOnPropertyChangedCallback(
            object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    getData(searchText.get() ?: "")
                }
            })
    }

    fun getData(data: String) {
        searchJob?.cancel()
        searchJob = scope.launch {
            data?.let {
                delay(500)
                if (it.isEmpty()) {
                    searchData.value = emptyList()
                } else {
                    val result = repository.getSearch(data).await()
                    when(result) {
                        is Result.Success -> onSearchSuccess(result.value.firstOrNull())
                        is Result.Error -> onError(result.apiError)
                        is Result.Exception -> onException(result.exception.message ?: "")
                    }
                }
            }
        }
    }

    private fun onException(message: String) {
        searchData.value = listOf(SearchViewModel(data = "A network error occurred: $message"))
    }

    fun onSearchSuccess(value: Acronyms?) {
        searchData.value = value.toViewModel()
    }

    private fun onError(apiError: ApiError) {
        searchData.value = listOf(SearchViewModel(data = "An api error occurred: ${apiError.message}"))
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}

fun Acronyms?.toViewModel(): List<SearchViewModel> {
    return this?.lfs?.map { SearchViewModel(data = it.lf ?: "") } ?: emptyList()
}