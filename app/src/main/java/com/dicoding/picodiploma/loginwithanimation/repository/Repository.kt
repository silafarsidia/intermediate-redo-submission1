package com.dicoding.picodiploma.loginwithanimation.repository

import com.dicoding.picodiploma.loginwithanimation.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.api.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.api.RegisterResponse
import retrofit2.Call

class Repository {

    fun register(name: String, email: String, password: String): Call<RegisterResponse> {
        return ApiConfig.instances.register(name, email, password)
    }

    fun login(email: String, password: String): Call<LoginResponse> {
        return ApiConfig.instances.login(email, password)
    }

}