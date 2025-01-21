package com.example.onlineinspectionofpackedcases.User

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineinspectionofpackedcases.databinding.ActivityAddRequestBinding
import com.example.onlineinspectionofpackedcases.model.RetrofitClient
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class AddRequest : AppCompatActivity() {
    private val b by lazy {
        ActivityAddRequestBinding.inflate(layoutInflater)
    }
    var name=""
    var num=""
    var email=""
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH)
        val formattedDate = currentDate.format(formatter)

        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {
            name=getString("name","").toString()
            num=getString("num","").toString()
            email=getString("email","").toString()
        }

        b.etname.setText(name)
        b.etnum.setText(num)
        b.etemail.setText(email)

        b.btnsend.setOnClickListener {
           var  etname=b.etname.text.toString().trim()
           var  etnum=b.etnum.text.toString().trim()
           var  etemail=b.etemail.text.toString().trim()
           var  etquaninty=b.etquaninty.text.toString().trim()
           var  etdescri=b.etdescri.text.toString().trim()
            var etaddress=b.etaddress.text.toString().trim()


            if(etname.isEmpty()){
                b.etname.error="Enter your name"
            }else if(etnum.isEmpty()){
                b.etnum.error="Enter your Number"
            }else if(etemail.isEmpty()){
                b.etemail.error="Enter your Email"
            }else if(etquaninty.isEmpty()){
                b.etquaninty.error="Enter Quantity"
            }else if(etdescri.isEmpty()){
                b.etdescri.error="Enter Description"
            }else if(etaddress.isEmpty()){
                b.etaddress.error
            }
            else{

                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.useraddrequest(
                                uname=etname,
                                uemail=etemail,
                                unum=etnum,
                                udescri=etdescri,
                                uaddress=etaddress,
                                ustatus="Pending",
                                wname="",
                                wemail="",
                                wnum="",
                                wstatus="",
                                wdescri="",
                                adstatus="Pending",
                                addescri="",
                                path="",
                                quanity=etquaninty,
                                date=formattedDate,
                                condition="register"
                    )
                        .enqueue(object: Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                Toast.makeText(this@AddRequest, ""+t.message, Toast.LENGTH_SHORT).show()
                            }
                            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                Toast.makeText(this@AddRequest, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                b.etname.text.clear()
                                b.etnum.text.clear()
                                b.etemail.text.clear()
                                b.etquaninty.text.clear()
                                b.etdescri.text.clear()
                                b.etaddress.text.clear()
                                finish()
                                startActivity(Intent(this@AddRequest,History::class.java))
                            }
                        })
                }

            }

        }

    }
}