package com.example.onlineinspectionofpackedcases

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onlineinspectionofpackedcases.databinding.ActivityLoginBinding
import com.example.onlineinspectionofpackedcases.model.RetrofitClient
import com.ymts0579.model.model.DefaultResponse
import com.ymts0579.model.model.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private val b by lazy{
        ActivityLoginBinding.inflate(layoutInflater)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.tvregister.setOnClickListener {
            b.linearregister.visibility= View.VISIBLE
            b.linearlogin.visibility=View.GONE
        }


        b.btnsignin.setOnClickListener {
            var email=b.etemail.text.toString().trim()
            var pass=b.etpassword.text.toString().trim()

            if(email.isEmpty()){
                b.etemail.error="Enter your Email"
            }else if(pass.isEmpty()){
                b.etpassword1.error="Enter your Password"
            }else if(email.contains("admin")&&pass.contains("admin")){
                getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().putString("type","admin").apply()
                startActivity(Intent(this@Login,AdminDashboard::class.java))
                finish()
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.login(email,pass,"login")
                        .enqueue(object: Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>, response: Response<LoginResponse>
                            ) {
                                if(!response.body()?.error!!){
                                    val type=response.body()?.user
                                    if (type!=null) {
                                        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().apply {
                                            putString("num",type.num)
                                            putString("pass",type.pass)
                                            putString("email",type.email)
                                            putString("name",type.name)
                                            putString("address",type.address)
                                            putString("type",type.type)
                                            putString("status",type.status)
                                            putString("city",type.city)
                                            putInt("id",type.id)
                                            apply()
                                        }

                                        when(type.type){
                                            "User"->{
                                                startActivity(Intent(this@Login, UserDashboard::class.java))
                                                finish()
                                            }

                                        }

                                        Toast.makeText(this@Login, response.body()?.message, Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    Toast.makeText(this@Login, response.body()?.message, Toast.LENGTH_SHORT).show()
                                }

                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()


                            }

                        })
                }
            }
        }


        b.btnsignup.setOnClickListener {
            var name=b.etname.text.toString().trim()
            var num=b.etnum.text.toString().trim()
            var email1=b.etemail1.text.toString().trim()
            var address=b.etaddress.text.toString().trim()
            var city=b.etcity.text.toString().trim()
            var password1=b.etpassword1.text.toString().trim()


            if(name.isEmpty()){
                b.etname.error="Enter your Name"
            }else if(num.isEmpty()){
                b.etnum.error="Enter your Number"
            }else if(email1.isEmpty()){
                b.etemail1.error="Enter your Email"
            }else if(address.isEmpty()){
                b.etaddress.error="Enter your Address"
            }else if(city.isEmpty()){
                b.etcity.error="Enter your city"
            }else if(password1.isEmpty()){
                b.etpassword1.error="Enter your password"
            }else if(num.count()!=10){
                b.etnum.error="Enter your Number properly"
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.register(name,num,email1,address,city,password1,"User","","register")
                        .enqueue(object: Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                Toast.makeText(this@Login, ""+t.message, Toast.LENGTH_SHORT).show()
                            }
                            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                Toast.makeText(this@Login, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                b.etname.text.clear()
                                b.etnum.text.clear()
                                b.etemail1.text.clear()
                                b.etaddress.text.clear()
                                b.etcity.text.clear()
                                b.etpassword1.text.clear()
                                b.linearregister.visibility= View.GONE
                                b.linearlogin.visibility=View.VISIBLE
                                b.etemail.setText(email1)
                                b.etpassword.setText(password1)
                            }
                        })
                }
            }
        }

    }
}