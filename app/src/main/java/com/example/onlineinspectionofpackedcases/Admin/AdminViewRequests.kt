package com.example.onlineinspectionofpackedcases.Admin

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globalstartupmatchmaker.model.User
import com.example.globalstartupmatchmaker.model.Userresponse
import com.example.onlineinspectionofpackedcases.Admin.Adminviewuser.userAdminAdapter
import com.example.onlineinspectionofpackedcases.MainActivity
import com.example.onlineinspectionofpackedcases.R
import com.example.onlineinspectionofpackedcases.databinding.ActivityAdminViewRequestsBinding
import com.example.onlineinspectionofpackedcases.databinding.CardadminrequestBinding
import com.example.onlineinspectionofpackedcases.databinding.CarduseradminBinding
import com.example.onlineinspectionofpackedcases.logout
import com.example.onlineinspectionofpackedcases.model.Requestresponse
import com.example.onlineinspectionofpackedcases.model.RetrofitClient
import com.example.onlineinspectionofpackedcases.model.requestt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminViewRequests : AppCompatActivity() {
    private val b by lazy {
        ActivityAdminViewRequestsBinding.inflate(layoutInflater)
    }
    private lateinit var p: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        val builder = AlertDialog.Builder(this,R.style.TransparentDialog)
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.progressdialog, null))
        builder.setCancelable(false)
        p = builder.create()
        p.show()


        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.adminviewrequest("adminviewrequest")
                .enqueue(object : Callback<Requestresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Requestresponse>, response: Response<Requestresponse>) {
                        b.listrequest.let {
                            response.body()?.user?.let {
                                    it1 ->
                                it.adapter=requestAdminAdapter(this@AdminViewRequests,it1)
                                it.layoutManager= LinearLayoutManager(this@AdminViewRequests)
                                Toast.makeText(this@AdminViewRequests, "success", Toast.LENGTH_SHORT).show()
                            }
                        }
                        p.dismiss()
                    }

                    override fun onFailure(call: Call<Requestresponse>, t: Throwable) {
                        Toast.makeText(this@AdminViewRequests, "${t.message}", Toast.LENGTH_SHORT).show()
                        p.dismiss()
                    }

                })
        }

    }


    class requestAdminAdapter(var context: Context, var listdata: ArrayList<requestt>):
        RecyclerView.Adapter<requestAdminAdapter.DataViewHolder>(){

        inner class DataViewHolder(val view: CardadminrequestBinding) : RecyclerView.ViewHolder(view.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            return DataViewHolder(
                CardadminrequestBinding.inflate(
                LayoutInflater.from(context),parent,
                false))
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
            with(holder.view){

                listdata[position].apply {
                    tvfname.text=uname
                    tvfnum.text=unum
                    tvfcity.text=uaddress
                    tvstatus.text=adstatus
                    tvDate.text=date
                    tvdescri.text="User Description : $udescri"



                    holder.itemView.setOnClickListener {
                        val alertdialog= AlertDialog.Builder(context)
                        alertdialog.setTitle("Accept/Reject")
                        alertdialog.setIcon(R.drawable.logo)
                        alertdialog.setCancelable(false)
                        alertdialog.setMessage("Do you Want to Accept or Reject?")
                        alertdialog.setPositiveButton("Yes"){ alertdialog, which->
                           readid("Accepted",id)
                            alertdialog.dismiss()
                        }
                        alertdialog.setNegativeButton("No"){alertdialog,which->
                            readid("Rejected",id)
                            alertdialog.dismiss()
                        }
                        alertdialog.show()
                    }


                }

            }
        }

        private fun readid(status: String, id: Int) {
            if(status=="Accepted"){
                context.startActivity(Intent(context,Adminassignworkers::class.java).apply {
                    putExtra("status",status)
                    putExtra("reid",id)
                })

            }else{

            }

        }

        override fun getItemCount() = listdata.size
    }
}