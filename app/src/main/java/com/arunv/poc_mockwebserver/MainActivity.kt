package com.arunv.poc_mockwebserver

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arunv.poc_mockwebserver.model.RegisterRequestModel
import com.arunv.poc_mockwebserver.model.RegisterResponseModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var loginApiWebService: LoginApiWebService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClickMe.setOnClickListener {

            val registrationRequestModel = RegisterRequestModel()

            registrationRequestModel.userName = etUserName.text.toString()
            registrationRequestModel.password = "abcdzxr@123"
            registrationRequestModel.phoneNumber = "8489966535"
            registrationRequestModel.firstName = "apple"
            registrationRequestModel.lastName = "apple"
            registrationRequestModel.countryCode = "342"

            getLoginApiInterface()?.registerUser(registrationRequestModel)
                ?.enqueue(object : Callback<RegisterResponseModel> {
                    override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                        Log.i("----> ", "onFailure - "+call.request().url)
                        Log.i("----> ", "onFailure - "+t.localizedMessage)
                    }

                    override fun onResponse(
                        call: Call<RegisterResponseModel>,
                        response: Response<RegisterResponseModel>
                    ) {
                        Log.i("----> ", "onResponse")
                        val registerResponseModel: RegisterResponseModel? = response.body()
                        Log.i("----> ", "RegisterResponseModel : " + registerResponseModel?.message)
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                })
        }

    }

    private fun getLoginApiInterface(): LoginApiWebService? {
        loginApiWebService = Retrofit.Builder()
            .baseUrl((application as PotterApp).getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(OkHttpProvider.getOkHttpClient())
            .build()
            .create(LoginApiWebService::class.java)
        return loginApiWebService
    }


}
