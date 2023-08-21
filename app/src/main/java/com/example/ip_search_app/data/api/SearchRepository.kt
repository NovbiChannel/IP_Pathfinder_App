package com.example.ip_search_app.data.api

import com.example.ip_search_app.data.db.SearchDao
import com.example.ip_search_app.models.SearchIpModel
import javax.inject.Inject
import javax.inject.Named

class SearchRepository @Inject constructor(
    @Named("search")
    private val searchService: IpSearchService,
    @Named("userIp")
    private val userIpService: UserIpService,
    private val searchDao: SearchDao
) {
    suspend fun getUserIp(format: String) = userIpService.getUserIp(format)
    suspend fun getSearchIpInfo(ip: String) = searchService.getSearchIpInformation(ip)
    suspend fun getAllSearchDb() = searchDao.getAllSearch()
    suspend fun addToDb(searchIp: SearchIpModel) {
        if (!searchDao.checkIpFromDb(searchIp.ip)) {
            searchDao.insert(searchIp)
        }
    }
    suspend fun deleteFromDb(searchIp: SearchIpModel) = searchDao.delete(searchIp)
}