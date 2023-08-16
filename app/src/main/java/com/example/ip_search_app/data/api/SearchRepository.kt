package com.example.ip_search_app.data.api

import javax.inject.Inject
import javax.inject.Named

class SearchRepository @Inject constructor(
    @Named("search")
    private val searchService: IpSearchService,
    @Named("userIp")
    private val userIpService: UserIpService
) {
    suspend fun getUserIp(format: String) = userIpService.getUserIp(format)
    suspend fun getSearchIpInfo(ip: String) = searchService.getSearchIpInformation(ip)
}