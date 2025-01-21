package com.example.onlineinspectionofpackedcases

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onlineinspectionofpackedcases.databinding.ActivityWorkerDashboardBinding
import com.example.onlineinspectionofpackedcases.databinding.CardprofileBinding
import com.example.onlineinspectionofpackedcases.databinding.CardworkerprofileBinding
import com.example.onlineinspectionofpackedcases.model.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkerDashboard : AppCompatActivity() {
    private val b by lazy {
        ActivityWorkerDashboardBinding.inflate(layoutInflater)
    }
    private val bind by lazy {
        CardworkerprofileBinding.inflate(layoutInflater)
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

        val k=arrayOf("choose your choice","Available","Not Available")

        ArrayAdapter(this@WorkerDashboard,
            android.R.layout.simple_list_item_checked, k).apply {
            bind.spinstatus.adapter=this
        }
        k.forEachIndexed { index, s ->
            if(s==status){
                bind.spinstatus.setSelection(index,true)
            }
        }

        b.imageView2.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this,b.imageView2)
            popupMenu.menuInflater.inflate(R.menu.menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_profile ->{
                        BottomSheetDialog(this).apply {
                            setContentView(bind.root)
                            bind.btnsubmit.setOnClickListener {
                                var name1=bind.etname.text.toString().trim()
                                var num1=bind.etnum.text.toString().trim()

                                var address1=bind.etaddress.text.toString().trim()
                                var city1=bind.etcity.text.toString().trim()
                                var password1=bind.etpassword1.text.toString().trim()
                                var status1=bind.spinstatus.selectedItem.toString()


                                if(name1.isEmpty()){
                                    bind.etname.error="Enter your Name"
                                }else if(num1.isEmpty()){
                                    bind.etnum.error="Enter your Number"
                                }else if(address1.isEmpty()){
                                    bind.etaddress.error="Enter your Address"
                                }else if(city1.isEmpty()){
                                    bind.etcity.error="Enter your city"
                                }else if(password1.isEmpty()){
                                    bind.etpassword1.error="Enter your password"
                                }else if(num1.count()!=10){
                                    bind.etnum.error="Enter your Number properly"
                                }else if(status1=="choose your choice"){
                                    Toast.makeText(this@WorkerDashboard, "choose proper choice", Toast.LENGTH_SHORT).show()
                                }else {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        RetrofitClient.instance.updateusers(name="$name1",
                                            num="$num1",
                                            email="$email",
                                            address="$address1",
                                            city="$city1",
                                            pass="$password1",
                                            status="$status1",
                                            id=id,
                                            condition="")
                                            .enqueue(object: Callback<DefaultResponse> {
                                                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                                    Toast.makeText(this@WorkerDashboard, ""+t.message, Toast.LENGTH_SHORT).show()
                                                }
                                                override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                                    Toast.makeText(this@WorkerDashboard, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                                    dismiss()
                                                    getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().apply {
                                                        putString("num",num1)
                                                        putString("pass",password1)
                                                        putString("email",email)
                                                        putString("name",name1)
                                                        putString("address",address1)
                                                        putString("type",type)
                                                        putString("status",status1)
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

                    R.id.action_Logout -> logout()

                }
                true
            })
            popupMenu.show()

        }

    }


    //uname
    //unum
    //uemail
    //uaddress
    //quaninty
    //udescri
    //status
    //wname
    //wnum
    //wemail
    //astatus
    //wdescri
    //cost
    //path
    //date
}