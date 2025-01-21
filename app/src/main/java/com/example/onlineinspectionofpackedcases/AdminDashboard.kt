package com.example.onlineinspectionofpackedcases

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onlineinspectionofpackedcases.Admin.AdminHistory
import com.example.onlineinspectionofpackedcases.Admin.AdminViewRequests
import com.example.onlineinspectionofpackedcases.Admin.Adminviewuser
import com.example.onlineinspectionofpackedcases.Admin.Viewworking
import com.example.onlineinspectionofpackedcases.databinding.ActivityAdminDashboardBinding

class AdminDashboard : AppCompatActivity() {
    private val b by lazy {
        ActivityAdminDashboardBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(b.root)

        b.btnadminhistory.setOnClickListener { startActivity(Intent(this,AdminHistory::class.java)) }
        b.btnbookings.setOnClickListener { startActivity(Intent(this,AdminViewRequests::class.java)) }
        b.btnadminworkers.setOnClickListener { startActivity(Intent(this,Viewworking::class.java)) }
        b.btnadminuser.setOnClickListener { startActivity(Intent(this,Adminviewuser::class.java)) }
        b.btnadminlogout.setOnClickListener { logout() }


    }
}