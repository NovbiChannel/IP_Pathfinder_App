package com.example.ip_search_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ip_search_app.models.SearchIpModel

@Database(entities = [SearchIpModel::class], version = 1, exportSchema = true)
abstract class SearchDatabase: RoomDatabase() {
    abstract fun getSearchIpDao(): SearchDao
}