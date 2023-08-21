
package com.example.ip_search_app.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ip_search")
data class SearchIpModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ip: String,
    val hostname: String,
    val city: String,
    val region: String,
    val country: String,
    val loc: String,
    val org: String,
    val postal: String,
    val timezone: String
) : Serializable
