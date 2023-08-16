package com.example.ip_search_app.data.api

import com.example.ip_search_app.models.SearchIpResponse
import com.example.ip_search_app.models.UserIpResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IpSearchService {

    @GET("/{ip}/geo")
    suspend fun getSearchIpInformation(
        @Path("ip") ip: String
    ) : Response<SearchIpResponse>
}