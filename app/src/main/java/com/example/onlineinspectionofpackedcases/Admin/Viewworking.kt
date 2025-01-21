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
import com.example.onlineinspectionofpackedcases.Admin.Adminviewuser.userAdminAdapter
import com.example.onlineinspectionofpackedcases.R
import com.example.onlineinspectionofpackedcases.databinding.ActivityViewworkingBinding
import com.example.onlineinspectionofpackedcases.databinding.CardaddworkersBinding
import com.example.onlineinspectionofpackedcases.databinding.CarduseradminBinding
import com.example.onlineinspectionofpackedcases.model.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Viewworking : AppCompatActivity() {
    private val b by lazy {
        ActivityViewworkingBinding.inflate(layoutInflater)
    }
    private val bind by lazy {
        CardaddworkersBinding.inflate(layoutInflater)
    }
    private lateinit var p: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)


        Readworker()

        b.btnaddwork.setOnClickListener {
            BottomSheetDialog(this
            ).apply {
                setContentView(bind.root)

                bind.btnsignup.setOnClickListener {
                    var name=bind.etname.text.toString().trim()
                    var num=bind.etnum.text.toString().trim()
                    var email1=bind.etemail1.text.toString().trim()
                    var address=bind.etaddress.text.toString().trim()
                    var city=bind.etcity.text.toString().trim()
                    var password1=bind.etpassword1.text.toString().trim()


                    if(name.isEmpty()){
                        bind.etname.error="Enter your Name"
                    }else if(num.isEmpty()){
                        bind.etnum.error="Enter your Number"
                    }else if(email1.isEmpty()){
                        bind.etemail1.error="Enter your Email"
                    }else if(address.isEmpty()){
                        bind.etaddress.error="Enter your Address"
                    }else if(city.isEmpty()){
                        bind.etcity.error="Enter your city"
                    }else if(password1.isEmpty()){
                        bind.etpassword1.error="Enter your password"
                    }else if(num.count()!=10){
                        bind.etnum.error="Enter your Number properly"
                    }else{
                        CoroutineScope(Dispatchers.IO).launch {
                            RetrofitClient.instance.register(name,num,email1,address,city,password1,"Worker","Available","register")
                                .enqueue(object: Callback<DefaultResponse> {
                                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                        Toast.makeText(this@Viewworking, ""+t.message, Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                        Toast.makeText(this@Viewworking, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                        bind.etname.text.clear()
                                        bind.etnum.text.clear()
                                        bind.etemail1.text.clear()
                                        bind.etaddress.text.clear()
                                        bind.etcity.text.clear()
                                        bind.etpassword1.text.clear()
                                        Readworker()
                                        dismiss()

                                    }
                                })
                        }
                    }
                }
                show()
            }
        }
    }

    private fun Readworker() {
        val builder = AlertDialog.Builder(this,R.style.TransparentDialog)
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.progressdialog, null))
        builder.setCancelable(false)
        p = builder.create()
        p.show()

        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getworkers()
                .enqueue(object : Callback<Userresponse> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Userresponse>, response: Response<Userresponse>) {

                        b.listworker.let {
                            response.body()?.user?.let {
                                    it1 ->
                                it.adapter=   workAdminAdapter(this@Viewworking, it1)
                                it.layoutManager= LinearLayoutManager(this@Viewworking)
                                Toast.makeText(this@Viewworking, "success", Toast.LENGTH_SHORT).show()
                            }
                        }
                        p.dismiss()

                    }

                    override fun onFailure(call: Call<Userresponse>, t: Throwable) {
                        Toast.makeText(this@Viewworking, "${t.message}", Toast.LENGTH_SHORT).show()
                        p.dismiss()
                    }

                })
        }



    }

    class workAdminAdapter(var context: Context, var listdata: ArrayList<User>):
        RecyclerView.Adapter<workAdminAdapter.DataViewHolder>(){

        inner class DataViewHolder(val view: CarduseradminBinding) : RecyclerView.ViewHolder(view.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            return DataViewHolder(
                CarduseradminBinding.inflate(
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

                    btndelete.setOnClickListener {

                    }



                }

            }




        }


        override fun getItemCount() = listdata.size
    }
}