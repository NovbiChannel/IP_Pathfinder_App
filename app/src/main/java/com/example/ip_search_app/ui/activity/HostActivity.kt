package com.example.ip_search_app.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ip_search_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}