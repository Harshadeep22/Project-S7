package com.example.onlineinspectionofpackedcases.model



class Requestresponse(val error: Boolean, val message:String, var user:ArrayList<requestt>) {
}

data class requestt(
    var id :Int,
   var uname:String,
   var uemail:String,
   var unum:String,
   var udescri:String,
   var uaddress:String,
   var ustatus:String,
   var wname:String,
   var wemail:String,
   var wnum:String,
   var wstatus:String,
   var wdescri:String,
   var adstatus:String,
   var addescri:String,
   var path:String,
   var quanity:String,
   var date:String,

)