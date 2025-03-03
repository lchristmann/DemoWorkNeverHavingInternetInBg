package com.lchristmann.demoworkneverhavinginternetinbg

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET

interface DemoApiService {

    @GET("objects/7")
    suspend fun getObject(): Response<JsonObject>

}