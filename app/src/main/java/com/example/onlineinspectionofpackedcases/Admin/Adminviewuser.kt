package com.example.onlineinspectionofpackedcases.Admin

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globalstartupmatchmaker.model.User
import com.example.globalstartupmatchmaker.model.Userresponse
import com.example.onlineinspectionofpackedcases.R
import com.example.onlineinspectionofpackedcases.databinding.ActivityAdminviewuserBinding
import com.example.onlineinspectionofpackedcases.databinding.CarduseradminBinding
import com.example.onlineinspectionofpackedcases.model.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Adminviewuser : AppCompatActivity() {
    private val b by lazy {
        ActivityAdminviewuserBinding.inflate(layoutInflater)
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
            RetrofitClient.instance.adminuser()
                .enqueue(object : Callback<Userresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Userresponse>, response: Response<Userresponse>) {

                        b.listuser.let {
                            response.body()?.user?.let {
                                    it1 ->
                                it.adapter=   userAdminAdapter(this@Adminviewuser, it1)
                                it.layoutManager= LinearLayoutManager(this@Adminviewuser)
                                Toast.makeText(this@Adminviewuser, "success", Toast.LENGTH_SHORT).show()
                            }
                        }
                        p.dismiss()

                    }

                    override fun onFailure(call: Call<Userresponse>, t: Throwable) {
                        Toast.makeText(this@Adminviewuser, "${t.message}", Toast.LENGTH_SHORT).show()
                        p.dismiss()
                    }

                })
        }

    }

    class userAdminAdapter(var context: Context, var listdata: ArrayList<User>):
        RecyclerView.Adapter<userAdminAdapter.DataViewHolder>(){

        inner class DataViewHolder(val view: CarduseradminBinding) : RecyclerView.ViewHolder(view.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            return DataViewHolder(CarduseradminBinding.inflate(
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
                    if(type=="User") {
                        tvstatus.visibility = View.GONE
                        btndelete.visibility=View.GONE
                    }



                }

            }




        }


        override fun getItemCount() = listdata.size
    }
}