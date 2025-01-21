package com.example.onlineinspectionofpackedcases

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onlineinspectionofpackedcases.User.AddRequest
import com.example.onlineinspectionofpackedcases.User.History
import com.example.onlineinspectionofpackedcases.databinding.ActivityUserDashboardBinding
import com.example.onlineinspectionofpackedcases.databinding.CardprofileBinding
import com.example.onlineinspectionofpackedcases.model.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDashboard : AppCompatActivity() {
    private val b by lazy{
        ActivityUserDashboardBinding.inflate(layoutInflater)
    }
    private val bind by lazy {
        CardprofileBinding.inflate(layoutInflater)
    }
    var num=""
    var pass=""
    var email=""
    var name=""
    var address=""
    var type=""
    var status=""
    var city=""
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {
            id=getInt("id",0).toInt()
            name=getString("name","").toString()
            num=getString("num","").toString()
            email=getString("email","").toString()
            address=getString("address","").toString()
            city=getString("city","").toString()
            pass=getString("pass","").toString()
            type=getString("type","").toString()
            status=getString("status","").toString()


        }

        bind.etname.setText(name)
        bind.etnum.setText(num)
        bind.etaddress.setText(address)
        bind.etcity.setText(city)
        bind.etpassword1.setText(pass)



        b.btnuserhistory.setOnClickListener{startActivity(Intent(this, History::class.java))}
        b.btnaddrequest.setOnClickListener { startActivity(Intent(this, AddRequest::class.java)) }
        b.btnuserlogout.setOnClickListener { logout() }

        b.btnuserprofile.setOnClickListener {
            BottomSheetDialog(this).apply {
                setContentView(bind.root)
                bind.btnsubmit.setOnClickListener {
                    var name1=bind.etname.text.toString().trim()
                    var num1=bind.etnum.text.toString().trim()

                    var address1=bind.etaddress.text.toString().trim()
                    var city1=bind.etcity.text.toString().trim()
                    var password1=bind.etpassword1.text.toString().trim()


                    if(name.isEmpty()){
                        bind.etname.error="Enter your Name"
                    }else if(num.isEmpty()){
                        bind.etnum.error="Enter your Number"
                    }else if(address.isEmpty()){
                        bind.etaddress.error="Enter your Address"
                    }else if(city.isEmpty()){
                        bind.etcity.error="Enter your city"
                    }else if(password1.isEmpty()){
                        bind.etpassword1.error="Enter your password"
                    }else if(num.count()!=10){
                        bind.etnum.error="Enter your Number properly"
                    }else {
                        CoroutineScope(Dispatchers.IO).launch {
                            RetrofitClient.instance.updateusers(name="$name1",
                                    num="$num1",
                                    email="$email",
                                    address="$address1",
                                    city="$city1",
                                    pass="$password1",
                                    status="",
                                    id=id,
                                    condition="")
                                .enqueue(object: Callback<DefaultResponse> {
                                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                        Toast.makeText(this@UserDashboard, ""+t.message, Toast.LENGTH_SHORT).show()
                                    }
                                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                        Toast.makeText(this@UserDashboard, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                        dismiss()
                                        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().apply {
                                            putString("num",num1)
                                            putString("pass",password1)
                                            putString("email",email)
                                            putString("name",name1)
                                            putString("address",address1)
                                            putString("type",type)
                                            putString("status",status)
                                            putString("city",city1)
                                            putInt("id",id)
                                            apply()
                                        }
                                    }
                                })
                        }

                    }
                }
                show()
            }

        }

    }


}