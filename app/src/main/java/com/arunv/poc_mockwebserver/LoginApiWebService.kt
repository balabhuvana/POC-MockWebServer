package com.arunv.poc_mockwebserver

import com.arunv.poc_mockwebserver.model.LoginRequestModel
import com.arunv.poc_mockwebserver.model.LoginResponseModelRoot
import com.arunv.poc_mockwebserver.model.RegisterRequestModel
import com.arunv.poc_mockwebserver.model.RegisterResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiWebService {

    @POST("Login")
    fun loginUser(@Body loginRequestModel: LoginRequestModel): Call<LoginResponseModelRoot>

    @POST("Register")
    fun registerUser(@Body registerRequestModel: RegisterRequestModel): Call<RegisterResponseModel>

}