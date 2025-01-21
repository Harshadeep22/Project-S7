package com.ymts0579.model.model

import com.example.globalstartupmatchmaker.model.User

data class LoginResponse(val error: Boolean, val message:String,var user: User)