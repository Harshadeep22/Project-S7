package com.example.onlineinspectionofpackedcases.Admin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globalstartupmatchmaker.model.User
import com.example.globalstartupmatchmaker.model.Userresponse
import com.example.onlineinspectionofpackedcases.AdminDashboard
import com.example.onlineinspectionofpackedcases.R
import com.example.onlineinspectionofpackedcases.databinding.ActivityAdminassignworkersBinding
import com.example.onlineinspectionofpackedcases.databinding.CardadassignworkBinding
import com.example.onlineinspectionofpackedcases.model.RetrofitClient
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Adminassignworkers : AppCompatActivity() {
    private val b by lazy{
        ActivityAdminassignworkersBinding.inflate(layoutInflater)
    }
    private lateinit var p: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)


        var restatus=intent.getStringExtra("status").toString()
        var reid=intent.getIntExtra("reid",0)

        val builder = AlertDialog.Builder(this,R.style.TransparentDialog)
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.progressdialog, null))
        builder.setCancelable(false)
        p = builder.create()
        p.show()

        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.adminuser("Available","adminuser")
                .enqueue(object : Callback<Userresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Userresponse>, response: Response<Userresponse>) {
                        b.main.let {
                            response.body()?.user?.let {
                                    it1 ->
                                it.adapter=AssginAdminAdapter(this@Adminassignworkers,it1,restatus,reid.toInt())
                                it.layoutManager= LinearLayoutManager(this@Adminassignworkers)
                                Toast.makeText(this@Adminassignworkers, "success", Toast.LENGTH_SHORT).show()
                            }
                        }
                        p.dismiss()
                    }

                    override fun onFailure(call: Call<Userresponse>, t: Throwable) {
                        Toast.makeText(this@Adminassignworkers, "${t.message}", Toast.LENGTH_SHORT).show()
                        p.dismiss()
                    }

                })
        }

    }


    class AssginAdminAdapter(var context: Context, var listdata: ArrayList<User>,
                              var restatus:String, var reid:Int):
        RecyclerView.Adapter<AssginAdminAdapter.DataViewHolder>(){

        inner class DataViewHolder(val view: CardadassignworkBinding) : RecyclerView.ViewHolder(view.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            return DataViewHolder(
                CardadassignworkBinding.inflate(
                    LayoutInflater.from(context),parent,
                    false))
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
            with(holder.view){

                listdata[position].apply {

                    tvfname.text=name
                    tvfemail.text=email
                    tvfnum.text=num
                    tvfcity.text=city
                    tvstatus.text=status

                    holder.itemView.setOnClickListener {
                        val alertdialog= AlertDialog.Builder(context)
                        alertdialog.setTitle("Assign  to ${name}")
                        alertdialog.setIcon(R.drawable.logo)
                        alertdialog.setCancelable(false)

                        alertdialog.setPositiveButton("Yes"){ alertdialog, which->
                            updatedate(name,email,num,restatus,reid)
                            alertdialog.dismiss()
                        }

                        alertdialog.show()
                    }


                }

            }
        }

        private fun updatedate(
            name: String,
            email: String,
            num: String,
            restatus: String,

            reid: Int
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.instance.updateadrequest(name,email,num,restatus,reid,"updateadrequest")
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            Toast.makeText(context, ""+t.message, Toast.LENGTH_SHORT).show()
                        }
                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            Toast.makeText(context, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                            context.startActivity(Intent(context,AdminHistory::class.java))

                        }
                    })
            }
        }


        override fun getItemCount() = listdata.size
    }


    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this,AdminDashboard::class.java))
        finish()
    }
}