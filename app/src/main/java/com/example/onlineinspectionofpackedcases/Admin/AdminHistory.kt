package com.example.onlineinspectionofpackedcases.Admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onlineinspectionofpackedcases.R
import com.example.onlineinspectionofpackedcases.databinding.ActivityAdminHistoryBinding

class AdminHistory : AppCompatActivity() {
    private val b by lazy {
        ActivityAdminHistoryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(b.root)

    }
}