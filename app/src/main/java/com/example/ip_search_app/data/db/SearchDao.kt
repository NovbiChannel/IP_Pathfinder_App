package com.example.ip_search_app.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ip_search_app.models.SearchIpModel

@Dao
interface SearchDao {
    @Query("SELECT * FROM ip_search")
    suspend fun getAllSearch(): List<SearchIpModel>

    @Query("SELECT COUNT(*) FROM ip_search WHERE ip = :ip")
    suspend fun checkIpFromDb(ip: String): Boolean
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchIp: SearchIpModel)
    @Delete
    suspend fun delete(searchIp: SearchIpModel)
}