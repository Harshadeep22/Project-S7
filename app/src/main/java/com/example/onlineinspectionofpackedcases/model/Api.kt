package com.example.onlineinspectionofpackedcases.model


import com.example.globalstartupmatchmaker.model.Userresponse
import com.ymts0579.model.model.DefaultResponse
import com.ymts0579.model.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("users.php")
    fun register(
        @Field("name") name: String,
        @Field("num") num: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("pass") pass: String,
        @Field("type") type: String,
        @Field("status") status: String,
        @Field("condition") condition: String,
    ): Call<DefaultResponse>


    @FormUrlEncoded
    @POST("users.php")
    fun adminuser(
        @Field("status") status: String,
        @Field("condition") condition: String,
    ):Call<Userresponse>

    @FormUrlEncoded
    @POST("request.php")
    fun useraddrequest(
      @Field("uname")  uname:String,
      @Field("uemail")  uemail:String,
      @Field("unum")  unum:String,
      @Field("udescri")  udescri:String,
      @Field("uaddress")  uaddress:String,
      @Field("ustatus")  ustatus:String,
      @Field("wname")  wname:String,
      @Field("wemail")  wemail:String,
      @Field("wnum")  wnum:String,
      @Field("wstatus")  wstatus:String,
      @Field("wdescri")  wdescri:String,
      @Field("adstatus")  adstatus:String,
      @Field("addescri")  addescri:String,
      @Field("path")  path:String,
      @Field("quanity")  quanity:String,
      @Field("date")  date:String,
      @Field("condition") condition: String,
    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("request.php")
    fun adminviewrequest(
        @Field("condition") condition: String,
    ):Call<Requestresponse>

    @FormUrlEncoded
    @POST("request.php")
    fun updateadrequest(
        @Field("wname")  wname:String,
        @Field("wemail")  wemail:String,
        @Field("wnum")  wnum:String,
        @Field("adstatus")  adstatus:String,
        @Field("id") id: Int,
        @Field("condition") condition: String,
    ):Call<DefaultResponse>



    @FormUrlEncoded
    @POST("users.php")
    fun login(
        @Field("email") email: String, @Field("pass") pass: String,
        @Field("condition") condition: String
    ): Call<LoginResponse>


    @FormUrlEncoded
    @POST("users.php")
    fun updateusers(
        @Field("name") name: String,
        @Field("num") num: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("city") city: String,
        @Field("pass") pass: String,
        @Field("status") status: String,
        @Field("id") id: Int, @Field("condition") condition: String
    ): Call<DefaultResponse>


    @GET("getusers.php")
    fun adminuser(): Call<Userresponse>


    @GET("getworkers.php")
    fun getworkers(): Call<Userresponse>


    @FormUrlEncoded
    @POST("users.php")
    fun Deleteperson(
        @Field("id") id: Int,
        @Field("condition") condition: String,
    ): Call<DefaultResponse>






}