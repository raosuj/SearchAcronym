package com.test.searchacro

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.searchacro.network.Acronym
import com.test.searchacro.network.Acronyms
import com.test.searchacro.network.Result
import com.test.searchacro.repository.AcronymRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

object MockRepo: AcronymRepo {
    override suspend fun getSearch(acronym: String): Deferred<Result<List<Acronyms>>> {
        return GlobalScope.async { Result.Success(listOf(Acronyms(
            sf = "em",
            lfs = listOf(Acronym(lf = "emma walt"),
                Acronym(lf = "embark metal"))))) }
    }
}

class SearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun onSuccess_sets_liveData() {
        val data = Acronyms(
            sf = "em",
            lfs = listOf(Acronym(lf = "emma walt"),
                Acronym(lf = "embark metal")))
        val searchViewModel = SearchActivityViewModel(MockRepo, scope = TestCoroutineScope())
        searchViewModel.onSearchSuccess(data)

        assert(searchViewModel.searchData.value?.size == 2)
    }
}