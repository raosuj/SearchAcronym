package com.test.searchacro.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {

    @GET("/software/acromine/dictionary.py?}")
    fun getAcronyms(@Query("sf") sf: String): Call<List<Acronyms>>
}

data class Acronyms(
    val lfs: List<Acronym> = emptyList(),
    val sf: String? = ""
)

data class Acronym(
    val lf: String? = ""
)




