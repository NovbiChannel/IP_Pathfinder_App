package com.example.ip_search_app.data.api

import com.example.ip_search_app.models.UserIpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserIpService {
    @GET("/")
    suspend fun getUserIp(
        @Query("format") format: String
    ) : Response<UserIpResponse>

}