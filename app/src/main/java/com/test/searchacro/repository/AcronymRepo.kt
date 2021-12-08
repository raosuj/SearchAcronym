package com.test.searchacro.repository

import com.test.searchacro.network.Acronyms
import com.test.searchacro.network.ApiClient
import com.test.searchacro.network.awaitResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import com.test.searchacro.network.Result

interface AcronymRepo {
    suspend fun getSearch(acronym: String): Deferred<Result<List<Acronyms>>>
}

class AcronymRepository(private val apiClient: ApiClient) : AcronymRepo {

    override suspend fun getSearch(acronym: String): Deferred<Result<List<Acronyms>>> {
        return GlobalScope.async { apiClient.getAcronyms(acronym).awaitResult() }
    }
}